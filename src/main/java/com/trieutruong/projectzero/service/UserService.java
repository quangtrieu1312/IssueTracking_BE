package com.trieutruong.projectzero.service;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.trieutruong.projectzero.domain.User;
import com.trieutruong.projectzero.exception.BadInputException;
import com.trieutruong.projectzero.request.LoginRequest;
import com.trieutruong.projectzero.request.SignUpRequest;

public interface UserService extends UserDetailsService {
	void login(LoginRequest req, HttpServletRequest request, HttpServletResponse response);

	User findByUserId(String userId) throws BadInputException;

	void signUp(SignUpRequest req) throws IOException, BadInputException;

	void activateByToken(String activateToken) throws BadInputException;

	void sendActivateToken(String username, String email) throws IOException;
	
	User findByJWT(String jwt) throws BadInputException;
	
	User findByHttpRequest(HttpServletRequest httpRequest) throws BadInputException;

	List<User> findByUserIds(List<String> userIds) throws BadInputException;

	List<User> findByUsernames(List<String> list) throws BadInputException;
}
