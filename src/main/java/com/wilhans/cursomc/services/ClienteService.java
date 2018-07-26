package com.wilhans.cursomc.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.wilhans.cursomc.domain.Cliente;
import com.wilhans.cursomc.dto.ClienteDTO;
import com.wilhans.cursomc.repositories.ClienteRepository;
import com.wilhans.cursomc.services.exceptions.DataIntegreityException;
import com.wilhans.cursomc.services.exceptions.ObjNotFoundException;

@Service
public class ClienteService {
	
	@Autowired
	private ClienteRepository repo;
	
	public Cliente find(Integer id) {
		Cliente obj  = repo.findOne(id);
		if (obj == null) {
			throw new ObjNotFoundException("Objeto não encontrado! Id: " + id
					+ ", tipo: " + Cliente.class.getName());
		}
		return obj;
	}
	
	public List<Cliente> findAll() {
		return repo.findAll();

	}

	public Cliente insert(Cliente obj) {
		obj.setId(null);
		return repo.save(obj);
	}

	public Cliente update(Cliente obj) {
		
		Cliente newObj = find(obj.getId());
		updateData(newObj, obj);
		return repo.save(newObj);
	}

	private void updateData(Cliente newObj, Cliente obj) {
		newObj.setNome(obj.getNome());
		newObj.setEmail(obj.getEmail());
		
	}

	public void delete(Integer id) {
		find(id);
		try {
			repo.delete(id);

		} catch (DataIntegrityViolationException e) {
			throw new DataIntegreityException("Não é possível excluir porque há entidades relacionadas!!!");
		}

	}

	public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = new PageRequest(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repo.findAll(pageRequest);

	}
	
	public Cliente fromDTO(ClienteDTO objDto) {
		return new Cliente(objDto.getId(), objDto.getNome(), objDto.getEmail(), null, null);
	}

}
