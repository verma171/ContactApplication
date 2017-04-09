package com.proawareness.contactapplication;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.android.volley.NoConnectionError;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.proawareness.contactapplication.models.ContactModel;
import com.proawareness.contactapplication.models.Result;
import com.proawareness.contactapplication.networkutils.NetworkManager;
import com.proawareness.contactapplication.viewadapters.ContactAdapter;

import java.util.List;

public class MainActivity extends AppCompatActivity implements Listener,ErrorListener,SwipeRefreshLayout.OnRefreshListener {

	RecyclerView mRecycyclerView;
	LinearLayoutManager mLayoutManager;
	ContactAdapter mContactAdapter;
	SwipeRefreshLayout mSwipeRefreshLayout;
	ContactLoader mContactLoader;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initUI();
	}

	/**
	 * function sends post contact request to server.
	 */
	private void sendContactRequest() {

		if(!isNetworkAvailable())
		{
			Toast.makeText(this,"No Internet Connection! Check your connection and try again!!",Toast.LENGTH_LONG).show();
			showProgress(false);
			return;
		}
		showProgress(true);
		NetworkManager.getInstance(this).sendContactRequest(this,this);
	}

	/**
	 * check for network connection
	 * @return
	 */
	private boolean isNetworkAvailable() {
		ConnectivityManager cm =
				(ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = cm.getActiveNetworkInfo();
		return netInfo != null && netInfo.isConnectedOrConnecting();
	}

	/**
	 * Initializing Ui Elementss
	 */
	private void initUI() {
		mRecycyclerView = (RecyclerView) findViewById(R.id.recyclerView);
		mLayoutManager =  new LinearLayoutManager(this);
		mRecycyclerView.setLayoutManager(mLayoutManager);
		mSwipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.swipterefreshLayout);
		mContactAdapter = new ContactAdapter(this, null);
		mRecycyclerView.setAdapter(mContactAdapter);


		/**
		 * swipe view to add pull to reshresh functionality.
		 */
		mSwipeRefreshLayout.setOnRefreshListener(this);

		/**
		 * Showing Swipe Refresh animation on activity create
		 * As animation won't start on onCreate, post runnable is used
		 */
		mSwipeRefreshLayout.post(new Runnable() {
			@Override
			public void run() {
				mSwipeRefreshLayout.setRefreshing(true);

				sendContactRequest();
			}
		});
	}

	@Override
	public void onErrorResponse(VolleyError error) {

		//show Error message
		if(error instanceof NoConnectionError)
		{
			Toast.makeText(this,R.string.networkerror,Toast.LENGTH_SHORT).show();
		}
		else {
			Toast.makeText(this, R.string.dataerror, Toast.LENGTH_SHORT).show();
		}
		showProgress(false);
	}

	@Override
	public void onResponse(Object response) {
		if(response != null)
		{
			ContactModel model = (ContactModel) response;
             mContactLoader = new ContactLoader(this);
			 mContactLoader.execute(model.getResult());
		}
		else
		{
			//show Error message
			Toast.makeText(this,R.string.dataerror,Toast.LENGTH_SHORT).show();
		}
		showProgress(false);
	}


	private void showProgress(boolean isLoading)
	{
		if(isLoading)
			mSwipeRefreshLayout.setRefreshing(true);
		else
			mSwipeRefreshLayout.setRefreshing(false);
	}

	@Override
	public void onRefresh() {
      sendContactRequest();
	}

	/**
	 * Async taks to fetch the Sharedpreference data off the UI Thread
	 */
	public class ContactLoader extends AsyncTask<List<Result>,Integer,List<Result>>
	{
		Context context;
		public ContactLoader(Context context)
		{
		   this.context = context;
		}

		@Override
		protected List<Result> doInBackground(List<Result>... params) {
			return StorageHelper.getInstance(context).getFilteredList(params[0]);
		}

		@Override
		protected void onPostExecute(List<Result> results) {
			setAdapter(results);
		}
	}

	public void setAdapter(List filteredList)
	{
		if(mContactAdapter == null) {

			mContactAdapter = new ContactAdapter(this, filteredList);
			mRecycyclerView.setAdapter(mContactAdapter);
		}else
		{
			mContactAdapter.RefreshList(filteredList);
		}
	}

	@Override
	protected void onDestroy() {
		mContactLoader.cancel(true);
		super.onDestroy();

	}
}
