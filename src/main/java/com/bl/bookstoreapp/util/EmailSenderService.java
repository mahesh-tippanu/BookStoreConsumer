package com.bl.bookstoreapp.util;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import com.bl.bookstoreapp.configuration.RabbitMQConfig;

@Component
public class EmailSenderService {
	@Autowired
	private JavaMailSender mailSender;

	public void sendEmail(String toEmail,String token) {
		try {
			
			MimeMessage message = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message);
//			String body = "<b>Hello user</b>,<br><i>your order is sucessfull </i>";
			helper.setSubject("Book store application");
			helper.setTo(toEmail);
			boolean html = true;
			helper.setText(token, html);
//		SimpleMailMessage message = new SimpleMailMessage();
//		message.setTo(toEmail);
//		message.setSubject("Book ordered");
			mailSender.send(message);
		} catch (MessagingException e) {
			System.out.println("MessagingException");
		}
	}
	private static final Logger logger = LoggerFactory.getLogger(EmailSenderService.class);

	@RabbitListener(queues = RabbitMQConfig.queueName)
	public void consumeMessageFromQueue(String email, String token) {
//		System.out.println("Message received from queue");
		logger.info("Message received from queue");
		sendEmail(email,token);
	}
}
