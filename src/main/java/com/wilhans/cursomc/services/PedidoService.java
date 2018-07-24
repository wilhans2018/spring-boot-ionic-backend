package com.wilhans.cursomc.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wilhans.cursomc.domain.Pedido;
import com.wilhans.cursomc.repositories.PedidoRepository;
import com.wilhans.cursomc.services.exceptions.ObjNotFoundException;

@Service
public class PedidoService {
	
	@Autowired
	private PedidoRepository repo;
	
	public Pedido buscar(Integer id) {
		Pedido obj  = repo.findOne(id);
		if (obj == null) {
			throw new ObjNotFoundException("Objeto não encontrado! Id: " + id
					+ ", tipo: " + Pedido.class.getName());
		}
		return obj;
	}

}
