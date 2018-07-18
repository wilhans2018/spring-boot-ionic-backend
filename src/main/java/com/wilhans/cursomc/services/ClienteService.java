package com.wilhans.cursomc.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wilhans.cursomc.domain.Cliente;
import com.wilhans.cursomc.repositories.ClienteRepository;
import com.wilhans.cursomc.services.exceptions.ObjNotFoundException;

@Service
public class ClienteService {
	
	@Autowired
	private ClienteRepository repo;
	
	public Cliente buscar(Integer id) {
		Cliente obj  = repo.findOne(id);
		if (obj == null) {
			throw new ObjNotFoundException("Objeto não encontrado! Id: " + id
					+ ", tipo: " + Cliente.class.getName());
		}
		return obj;
	}

}
