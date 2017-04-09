package com.proawareness.contactapplication.networkutils;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.proawareness.contactapplication.models.ContactModel;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by aniruddh.rathore on 4/9/17.
 */

public class ContactRequest extends Request<ContactModel> {

	protected static final String PROTOCOL_CHARSET = "utf-8";
	private static final String KEY_TOKEN = "token";
	private static final String TOEKN_VALUE = "c149c4fac72d3a3678eefab5b0d3a85a";
	Response.Listener mListener;
	public ContactRequest(int method, String url, Response.ErrorListener errorListener,Response.Listener responseListEner) {
		super(method, url, errorListener);
		mListener = responseListEner;
	}

	@Override
	protected Response<ContactModel> parseNetworkResponse(NetworkResponse response) {

		/**
		 * Parser the network resposne and convert it to model.
		 */
		ContactModel adWallResposne = null;
		try {
			String jsonString = new String(response.data,
					HttpHeaderParser.parseCharset(response.headers, PROTOCOL_CHARSET));
			adWallResposne = new Gson().fromJson(jsonString,ContactModel.class);
			return Response.success(adWallResposne,
					HttpHeaderParser.parseCacheHeaders(response));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	protected void deliverResponse(ContactModel response) {
		if(mListener != null)
		{
			mListener.onResponse(response);
		}
	}

	@Override
	protected Map<String, String> getParams() throws AuthFailureError {
		Map<String,String> params = new HashMap<String, String>();
		params.put(KEY_TOKEN,TOEKN_VALUE);

		return params;
	}
}
