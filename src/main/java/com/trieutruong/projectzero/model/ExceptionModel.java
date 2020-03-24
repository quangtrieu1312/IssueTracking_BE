package com.trieutruong.projectzero.model;

public class ExceptionModel extends GeneralModelResponse{
	
	private String error;

	public ExceptionModel(String msg,String error) {
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
