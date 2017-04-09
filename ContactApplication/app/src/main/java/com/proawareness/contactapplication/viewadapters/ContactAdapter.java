package com.proawareness.contactapplication.viewadapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.proawareness.contactapplication.R;
import com.proawareness.contactapplication.StorageHelper;
import com.proawareness.contactapplication.models.Result;
import com.proawareness.contactapplication.viewadapters.ContactAdapter.ContactViewHolder;

import java.util.List;

/**
 * Created by aniruddh.rathore on 4/9/17.
 */

public class ContactAdapter extends RecyclerView.Adapter<ContactViewHolder> implements
																			OnClickListener{


	List<Result> mArrayList = null;
	Context mContext;

	public ContactAdapter(Context context, List arrayList) {
		mContext = context;
		mArrayList = arrayList;
	}

	@Override
	public ContactViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View itemview = LayoutInflater.from(mContext).inflate(R.layout.list_item, parent, false);
		return new ContactViewHolder(itemview);
	}

	@Override
	public void onBindViewHolder(ContactViewHolder holder, int position) {
		holder.nameTextView.setText(mArrayList.get(position).getName());
		holder.delete.setTag(position);
		holder.delete.setOnClickListener(this);
	}


	@Override
	public int getItemCount() {
		if (mArrayList != null)
			return mArrayList.size();
		else
			return 0;
	}


	/**
	 * handle delete icon click
	 * @param v
	 */
	@Override
	public void onClick(View v) {
       int position = (int)v.getTag();
	   Result result = mArrayList.get(position);
		StorageHelper.getInstance(mContext).saveContact(result);

		// remove item from list and notify
		mArrayList.remove(position);
		notifyDataSetChanged();
	}

	public class ContactViewHolder extends RecyclerView.ViewHolder {
		TextView nameTextView;
		ImageView delete;

		public ContactViewHolder(View itemView) {
			super(itemView);
			nameTextView = (TextView) itemView.findViewById(R.id.nameView);
			delete = (ImageView)itemView.findViewById(R.id.delete);
		}
	}

	/**
	 * refersh the list with new data
	 * @param list
	 */
	public void RefreshList(List list)
	{
		mArrayList = list;
		notifyDataSetChanged();
	}
}
