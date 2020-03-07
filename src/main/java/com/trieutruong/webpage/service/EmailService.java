package com.trieutruong.webpage.service;

import java.io.IOException;

import com.trieutruong.webpage.request.EmailRequest;

public interface EmailService {
	void send(EmailRequest req) throws IOException;
	
	void send(String fromEmail, String toEmail, String emailSubject, String emailContent) throws IOException;
}
