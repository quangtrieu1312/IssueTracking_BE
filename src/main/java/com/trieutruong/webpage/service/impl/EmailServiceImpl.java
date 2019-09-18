package com.trieutruong.webpage.service.impl;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import com.sendgrid.Content;
import com.sendgrid.Email;
import com.sendgrid.Mail;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.trieutruong.webpage.request.EmailRequest;
import com.trieutruong.webpage.service.EmailService;

@Service
@PropertySource(value = "classpath:application.properties")
public class EmailServiceImpl implements EmailService {

	@Value("${spring.sendgrid.api-key}")
	String key;
	
	@Override
	public void sendEmail(EmailRequest req) throws IOException {
		Email from = new Email(req.getFrom());
		Email to = new Email(req.getTo());
		Content content = new Content("text/html", req.getContent());
		Mail mail = new Mail(from, req.getSubject(), to, content);
		
		SendGrid sg = new SendGrid(key);
		Request request = new Request();
		try {
			request.setMethod(Method.POST);
			request.setEndpoint("mail/send");
			request.setBody(mail.build());
			Response response = sg.api(request);
			System.out.println(response.getStatusCode());
			System.out.println(response.getBody());
			System.out.println(response.getHeaders());
		} catch (IOException ex) {
			throw ex;
		}
	}

}
