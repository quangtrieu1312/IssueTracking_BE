package com.trieutruong.projectzero.response;

import java.util.ArrayList;
import java.util.List;

import com.trieutruong.projectzero.domain.User;
import com.trieutruong.projectzero.model.GeneralModelResponse;
import com.trieutruong.projectzero.model.UserInfo;

public class UserResponse extends GeneralModelResponse {

	private List<UserInfo> usersInfo;

	public UserResponse(String msg, User user) {
		super.setStatus(Boolean.TRUE.toString());
		super.setMsg(msg);
		this.usersInfo = new ArrayList<UserInfo>();
		UserInfo userInfo = new UserInfo();
		userInfo.setEmail(user.getEmail());
		userInfo.setUsername(user.getUsername());
		usersInfo.add(userInfo);
	}

	public UserResponse(String msg, List<User> users) {
		super.setStatus(Boolean.TRUE.toString());
		super.setMsg(msg);
		this.usersInfo = new ArrayList<UserInfo>();
		for (User user : users) {
			UserInfo userInfo = new UserInfo();
			userInfo.setEmail(user.getEmail());
			userInfo.setUsername(user.getUsername());
			usersInfo.add(userInfo);
		}
	}

	public List<UserInfo> getUsersInfo() {
		return this.usersInfo;
	}

	public void setUsersInfo(List<UserInfo> usersInfo) {
		this.usersInfo = usersInfo;
	}

}
