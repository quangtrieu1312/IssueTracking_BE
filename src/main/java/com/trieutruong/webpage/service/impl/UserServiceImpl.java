package com.trieutruong.webpage.service.impl;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;

import java.io.IOException;

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

import com.trieutruong.webpage.constant.UserRole;
import com.trieutruong.webpage.domain.User;
import com.trieutruong.webpage.exception.BadInputException;
import com.trieutruong.webpage.repository.UserRepository;
import com.trieutruong.webpage.request.ActivateRequest;
import com.trieutruong.webpage.request.EmailRequest;
import com.trieutruong.webpage.request.LoginRequest;
import com.trieutruong.webpage.request.SignUpRequest;
import com.trieutruong.webpage.service.EmailService;
import com.trieutruong.webpage.service.UserService;
import com.trieutruong.webpage.util.JwtTokenProvider;
import com.trieutruong.webpage.util.RandomUtil;
import com.trieutruong.webpage.util.StringChecker;

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
	public User loadUserById(String userId) {
		User user = userRepository.findByUser_id(userId);
		if (user == null)
			redirectedUrl("/login");
		return user;
	}

	@Override
	public void signUp(SignUpRequest req) throws IOException {
		if (userRepository.findByUsername(req.getUsername()) != null)
			return;
		if (!req.getPassword().equals(req.getPassword2()))
			return;
		if (!isValidUsername(req.getUsername()) || !isValidPassword(req.getPassword()))
			return;
		String userId = RandomUtil.generateId();
		while (userRepository.findByUser_id(userId) != null)
			userId = RandomUtil.generateId();
		User user = new User(userId, req.getEmail(), req.getUsername(), encoder.encode(req.getPassword()),
				UserRole.DEFAULT);
		userRepository.save(user);
		String activateToken = tokenProvider.generateToken(user);
		EmailRequest emailReq = new EmailRequest("no-reply@the.crip", user.getEmail(), "Activate account",
				"<html>"
				+"<h1>Welcome to the crib!</h1>"
				+"To activate your account, click "
				+"<a href=\"http://192.168.1.103:8080/activate?activateToken="+activateToken+"\">here</a>"
				+ "</html>");
		emailService.sendEmail(emailReq);

	}

	private boolean isValidUsername(String username) {
		return (username.length() >= 5 && !StringChecker.hasSpecialChar(username));
	}

	private boolean isValidPassword(String password) {
		return (password.length() >= 10 && StringChecker.hasLower(password) && StringChecker.hasUpper(password)
				&& StringChecker.hasNumber(password) && StringChecker.hasSpecialChar(password));
	}

	@Override
	public void activate(String activateToken) throws BadInputException {
		String jwt = activateToken;

		if (StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)) {
			String userId = tokenProvider.getUserIdFromJWT(jwt);
			userRepository.enable(userId);
		}

	}

	@Override
	public void sendActivateToken(ActivateRequest req) throws IOException {
		User user = userRepository.findByUsername(req.getUsername());
		if (!user.getEmail().equals(req.getEmail()))
			return;
		String activateToken = tokenProvider.generateToken(user);
		EmailRequest emailReq = new EmailRequest("no-reply@the.crip", user.getEmail(), "Activate account",
				"<html>"
				+"<h1>Welcome to the crib!</h1>"
				+"To activate your account, click "
				+"<a href=\"http://192.168.1.103:8080/activate?activateToken="+activateToken+"\">here</a>"
				+"</html>");
		emailService.sendEmail(emailReq);
	}

}
