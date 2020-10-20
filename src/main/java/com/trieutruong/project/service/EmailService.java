package com.trieutruong.project.service;

import java.io.IOException;

import com.trieutruong.project.request.EmailRequest;

public interface EmailService {
	void send(EmailRequest req) throws IOException;
	
	void send(String fromEmail, String toEmail, String emailSubject, String emailContent) throws IOException;
}
