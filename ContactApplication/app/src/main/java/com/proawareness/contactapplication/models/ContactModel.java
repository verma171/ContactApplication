package com.proawareness.contactapplication.models;

/**
 * Created by aniruddh.rathore on 4/9/17.
 */

import java.util.List;

public class ContactModel {

	private Object message;
	private String status;
	private List<Result> result = null;

	public Object getMessage() {
		return message;
	}

	public void setMessage(Object message) {
		this.message = message;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<Result> getResult() {
		return result;
	}

	public void setResult(List<Result> result) {
		this.result = result;
	}



}
