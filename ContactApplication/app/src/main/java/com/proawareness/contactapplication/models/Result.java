package com.proawareness.contactapplication.models;



/**
 * Created by aniruddh.rathore on 4/9/17.
 */

public class Result {

	private String name;
	private String uid;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	@Override
	public boolean equals(Object obj) {
		if((obj instanceof Result) && (((Result)obj).getUid()).equals(this.getUid()))
			return true;
		else
			return false;
	}
}
