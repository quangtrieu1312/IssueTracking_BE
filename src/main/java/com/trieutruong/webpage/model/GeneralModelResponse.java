package com.trieutruong.webpage.model;

public class GeneralModelResponse {
	private String status; // true = process OK, false = not
	private String msg;

	public GeneralModelResponse() {
	}

	public GeneralModelResponse(String status, String msg) {
		this.status = status;
		this.msg = msg;
	}

	public GeneralModelResponse successResponse(String msg) {
		this.setStatus(Boolean.TRUE.toString());
		this.setMsg(msg);
		return this;
	}

	public GeneralModelResponse failureResponse(String msg) {
		return new GeneralModelResponse(Boolean.FALSE.toString(), msg);
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}
