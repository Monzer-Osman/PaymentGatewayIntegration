package com.PaymentGateway.EmailClient;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
import lombok.AllArgsConstructor;

@Service
@Slf4j
@AllArgsConstructor
public class EmailClientService{
	private JavaMailSender mailSender;

	public void sendEmail(String email,
						  String subject,
						  String body) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom("Team@M.I.S-Company");
		message.setTo(email);
		message.setSubject(subject);
		message.setText(body);
		mailSender.send(message);
		log.info("Mail Sent Successfully ...");
	}
}