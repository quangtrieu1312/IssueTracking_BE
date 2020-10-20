package com.trieutruong.project.service.impl;

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
import com.trieutruong.project.request.EmailRequest;
import com.trieutruong.project.service.EmailService;

@Service
@PropertySource(value = "classpath:application.properties")
public class EmailServiceImpl implements EmailService {

	@Value("${sendgrid.api-key}")
	String key;
	
	@Override
	public void send(EmailRequest req) throws IOException {
		Email from = new Email(req.getFrom());
		Email to = new Email(req.getTo());
		Content content = new Content("text/html", req.getContent());
		Mail mail = new Mail(from, req.getSubject(), to, content);
		mail.setReplyTo(from);
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

	@Override
	public void send(String fromEmail, String toEmail, String emailSubject, String emailContent) throws IOException {
		Email from = new Email(fromEmail);
		Email to = new Email(toEmail);
		Content content = new Content("text/html", emailContent);
		Mail mail = new Mail(from, emailSubject, to, content);
		mail.setReplyTo(from);
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
