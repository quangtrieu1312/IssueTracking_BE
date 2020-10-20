package com.trieutruong.project.service.impl;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.trieutruong.project.constant.UserRole;
import com.trieutruong.project.domain.User;
import com.trieutruong.project.exception.BadInputException;
import com.trieutruong.project.repository.UserRepository;
import com.trieutruong.project.request.EmailRequest;
import com.trieutruong.project.request.LoginRequest;
import com.trieutruong.project.request.SignUpRequest;
import com.trieutruong.project.service.EmailService;
import com.trieutruong.project.service.UserService;
import com.trieutruong.project.util.JwtTokenProvider;
import com.trieutruong.project.util.RandomUtil;
import com.trieutruong.project.util.StringChecker;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository userRepository;

	@Autowired
	EmailService emailService;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	JwtTokenProvider tokenProvider;

	@Autowired
	AuthenticationManager authenticationManager;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username);
		if (user == null || !user.isEnabled()) {
			throw new UsernameNotFoundException(username);
		}
		return user;
	}

	@Override
	public void login(LoginRequest req, HttpServletRequest request, HttpServletResponse response) {
		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(req.getUsername(), req.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = tokenProvider.generateToken((User) authentication.getPrincipal());
		Cookie cookie = new Cookie("AUTH_JWT", jwt);
		cookie.setPath("/");
		response.addCookie(cookie);
	}

	@Override
	public User findByUserId(String userId) throws BadInputException {
		User user = userRepository.findByUserId(userId);
		if (user == null) {
			throw new BadInputException("No user found");
		}
		return user;
	}

	@Override
	public List<User> findByUserIds(List<String> userIds) throws BadInputException {
		List<User> users = userRepository.findByUserIds(userIds);
		if (users == null) {
			throw new BadInputException("No user found");
		}
		return users;
	}

	@Override
	public void signUp(SignUpRequest req) throws IOException, BadInputException {
		if (userRepository.findByUsername(req.getUsername()) != null)
			throw new BadInputException("Username already exists");
		if (!req.getPassword().equals(req.getPassword2()))
			throw new BadInputException("Password doesn't match");
		if (!isValidUsername(req.getUsername()))
			throw new BadInputException("Username must have at least 5 characters and no special chars");
		if (!isValidPassword(req.getPassword()))
				throw new BadInputException("Password must have at least: 10 character, 1 lowercase, 1 uppercase, 1 number, and 1 special char");
		String userId = RandomUtil.generateId();
		while (userRepository.findByUserId(userId) != null)
			userId = RandomUtil.generateId();
		User user = new User(userId, req.getEmail(), req.getUsername(), encoder.encode(req.getPassword()),
				UserRole.DEFAULT);
		userRepository.save(user);
		String activateToken = tokenProvider.generateToken(user);
		EmailRequest emailReq = new EmailRequest("no-reply@trieutruong.com", user.getEmail(), "Activate account",
				"<html>" + "<h1>Welcome to my website!</h1>" + "To activate your account, click "
						+ "<a href=\"https://mysterious-reaches-08183.herokuapp.com/token/" + activateToken
						+ "\">here</a>" + "</html>");
		emailService.send(emailReq);

	}

	private boolean isValidUsername(String username) {
		return (username.length() >= 5 && !StringChecker.hasSpecialChar(username));
	}

	private boolean isValidPassword(String password) {
		return (password.length() >= 10 && StringChecker.hasLower(password) && StringChecker.hasUpper(password)
				&& StringChecker.hasNumber(password) && StringChecker.hasSpecialChar(password));
	}

	@Override
	public void activateByToken(String activateToken) throws BadInputException {
		String jwt = activateToken;

		if (StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)) {
			String userId = tokenProvider.getUserIdFromJWT(jwt);
			userRepository.enable(userId);
		}

	}

	@Override
	public void sendActivateToken(String username, String email) throws IOException {
		User user = userRepository.findByUsername(username);
		if (!user.getEmail().equals(email))
			return; // throws Exception
		String activateToken = tokenProvider.generateToken(user);
		EmailRequest emailReq = new EmailRequest("no-reply@trieutruong.com", user.getEmail(), "Activate account",
				"<html>" + "<h1>Welcome to my website!</h1>" + "To activate your account, click "
						+ "<a href=\"https://mysterious-reaches-08183.herokuapp.com/token/" + activateToken
						+ "\">here</a>" + "</html>");
		emailService.send(emailReq);
	}

	@Override
	public User findByJWT(String jwt) throws BadInputException {
		if (StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)) {
			String userId = tokenProvider.getUserIdFromJWT(jwt);
			User user = this.findByUserId(userId);
			return user;
		}
		throw new BadInputException("Cannot find user");
	}

	@Override
	public User findByHttpRequest(HttpServletRequest httpRequest) throws BadInputException {
		String jwt = tokenProvider.getJwtFromRequest(httpRequest);
		return findByJWT(jwt);
	}

	@Override
	public List<User> findByUsernames(List<String> usernames) throws BadInputException {
		List<User> users = userRepository.findByUsernames(usernames);
		if (users == null || users.isEmpty()) {
			throw new BadInputException("No users found");
		}
		return users;
	}

}
