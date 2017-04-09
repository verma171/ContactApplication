package com.proawareness.contactapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.proawareness.contactapplication.models.Result;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aniruddh.rathore on 4/9/17.
 */

/**
 * Helper class to save and retive contact from sharedpreference.
 */
public class StorageHelper {

	private static final String CONTACT_KEY = "CONTACT_KEY";
	private static StorageHelper helper;
	Context mContext;
	SharedPreferences mappSharedPrefs;
	private StorageHelper(Context context)
	{
	   mContext = context;
		mappSharedPrefs = PreferenceManager
				.getDefaultSharedPreferences(mContext);
	}


	public static StorageHelper getInstance(Context context){
		if(helper == null){
			helper = new StorageHelper(context);
		}
		return helper;
	}

	/**
	 * Save object to sharedpreference list
	 * @param contact object to save in list
	 */

	public void saveContact(Result contact)
	{
        List list = getContactList();
		if(list == null)
		{
           list = new ArrayList();
			list.add(contact);
			saveContactList(list);
		}
		else
		{
			list.add(contact);
			saveContactList(list);
		}
	}

	/**
	 * get all contact from sharedpreference
	 * @return
	 */
	public List<Result> getContactList()
	{
		Gson gson = new Gson();
		String json = mappSharedPrefs.getString(CONTACT_KEY, "");
		List contactList = gson.fromJson(json, new TypeToken<ArrayList<Result>>(){}.getType());
		return contactList;
	}

	/**
	 * Save list of contact in sharedpreference
	 * @param list
	 */
	private void saveContactList(List list)
	{
		Gson gson = new Gson();
		String json = gson.toJson(list);
		Editor prefsEditor = mappSharedPrefs.edit();
		prefsEditor.putString(CONTACT_KEY, json);
		prefsEditor.commit();
	}

	// remove all the results from the list which are alreafy exist.
	public List getFilteredList(List<Result> list)
	{
		if(getContactList() != null)
		 list.removeAll(getContactList());

		 return  list;
	}
}
