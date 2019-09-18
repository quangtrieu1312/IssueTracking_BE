package com.trieutruong.webpage.model;

public class ExceptionModel extends GeneralModelResponse{
	
	private String error;

	public ExceptionModel(String error, String msg) {
		super.setStatus(Boolean.FALSE.toString());
		this.error = error;
		super.setMsg(msg);
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

}
