package com.wilhans.cursomc.config;

import java.text.ParseException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.wilhans.cursomc.services.EmailService;
import com.wilhans.cursomc.services.SmtpMailService;

@Configuration
@Profile("prod")
public class ProdConfig {

	@Bean
	public boolean instantiateDataBase() throws ParseException {
		return true;
	}

	@Bean
	public EmailService emailService() {
		return new SmtpMailService();
	}

}
