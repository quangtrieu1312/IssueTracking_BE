package com.trieutruong.webpage.service;

import java.io.IOException;

import com.trieutruong.webpage.request.EmailRequest;

public interface EmailService {
	void sendEmail(EmailRequest req) throws IOException;
}
