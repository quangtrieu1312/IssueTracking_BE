package com.trieutruong.webpage.model;

import com.trieutruong.webpage.domain.User;

public class UserInfo {
	private String username;
	private String email;

	public UserInfo(String username, String email) {
		this.username = username;
		this.email = email;
	}
	
	public UserInfo(User user) {
		this.username = user.getUsername();
		this.email = user.getEmail();
	}

	public UserInfo() {
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
}
