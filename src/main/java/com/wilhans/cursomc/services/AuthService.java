package com.wilhans.cursomc.services;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.wilhans.cursomc.domain.Cliente;
import com.wilhans.cursomc.repositories.ClienteRepository;
import com.wilhans.cursomc.services.exceptions.ObjNotFoundException;

@Service
public class AuthService {

	private Random rand = new Random();

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	private EmailService emailService;

	public void sendNewPassword(String email) {

		Cliente cliente = clienteRepository.findByEmail(email);
		if (cliente == null) {
			throw new ObjNotFoundException("Email não encontrado!!!");
		}

		String newPass = newPassword();
		cliente.setSenha(bCryptPasswordEncoder.encode(newPass));

		clienteRepository.save(cliente);
		emailService.sendNewPasswordEmail(cliente, newPass);

	}

	private String newPassword() {
		char[] vet = new char[10];

		for (int i = 0; i < 10; i++) {
			vet[i] = ramdomChar();
		}

		return new String(vet);
	}

	private char ramdomChar() {
		int opt = rand.nextInt(3);
		if (opt == 0) {// gera um digito
			return (char) (rand.nextInt(10) + 48);
		} else if (opt == 1) {// gera letra maiuscula
			return (char) (rand.nextInt(26) + 65);

		} else {// gera letra minuscula
			return (char) (rand.nextInt(26) + 97);

		}

	}
}
