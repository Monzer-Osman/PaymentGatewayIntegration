package com.PaymentGateway.EmailClient;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import lombok.extern.slf4j.Slf4j;
import lombok.AllArgsConstructor;
@Controller
@RequestMapping("/mails")
@Slf4j
@AllArgsConstructor
public class EmailClientController{
	private EmailClientService emailClientService;

	@PostMapping("/sendMail")
	public String sendEmailMessage(MailDto mailDto){
		emailClientService.sendEmail(mailDto.to, mailDto.subject, mailDto.body);
		return "Email Sent Successfully ...";
	}

}