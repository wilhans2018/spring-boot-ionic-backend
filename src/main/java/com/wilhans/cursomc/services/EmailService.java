package com.wilhans.cursomc.services;

import org.springframework.mail.SimpleMailMessage;

import com.wilhans.cursomc.domain.Pedido;

public interface EmailService {
	
	void sendOrderConfirmationEmail(Pedido obj);
	
	void sendEmail(SimpleMailMessage msg);

}
