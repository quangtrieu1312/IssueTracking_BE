package com.trieutruong.projectzero.request;

import javax.validation.constraints.NotBlank;

public class SignUpRequest {
	@NotBlank
	private String email;
	@NotBlank
	private String username;
	@NotBlank
	private String password;
	@NotBlank
	private String password2;

	public SignUpRequest() {

	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPassword2() {
		return password2;
	}

	public void setPassword2(String password2) {
		this.password2 = password2;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
