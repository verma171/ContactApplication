package com.proawareness.contactapplication.networkutils;

import android.content.Context;

import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.Volley;

/**
 * Created by aniruddh.rathore on 4/9/17.
 */

public final class NetworkManager {


	private RequestQueue requestQueue;
	private static NetworkManager mNetworkManager ;
	private NetworkManager(Context context){
		if(requestQueue == null){
		}         requestQueue = Volley.newRequestQueue(context.getApplicationContext());
	}
	/**
	 *
	 * @param context
	 * @return
	 */
	public static NetworkManager getInstance(Context context){
		if(mNetworkManager == null){
			mNetworkManager = new NetworkManager(context);
		}
		return mNetworkManager;
	}

	public static final String WEB_REQUEST_URL = "http://139.162.152.157/contactlist.php";

	public void sendContactRequest(Listener respsoneListener, ErrorListener errorListener)
	{
        ContactRequest contactRequest = new ContactRequest(Method.PATCH,WEB_REQUEST_URL,errorListener,respsoneListener);
		requestQueue.add(contactRequest);
	}
}
