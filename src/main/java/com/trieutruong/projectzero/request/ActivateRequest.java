package com.trieutruong.projectzero.request;

import javax.validation.constraints.NotBlank;

public class ActivateRequest {
	@NotBlank
	private String email;
	
	@NotBlank
	private String username;

	public ActivateRequest() {

	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}
