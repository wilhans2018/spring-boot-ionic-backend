package com.wilhans.cursomc.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.wilhans.cursomc.domain.Categoria;
import com.wilhans.cursomc.dto.CategoriaDTO;
import com.wilhans.cursomc.repositories.CategoriaRepository;
import com.wilhans.cursomc.services.exceptions.DataIntegreityException;
import com.wilhans.cursomc.services.exceptions.ObjNotFoundException;

@Service
public class CategoriaService {

	@Autowired
	private CategoriaRepository repo;

	public Categoria find(Integer id) {
		Categoria obj = repo.findOne(id);
		if (obj == null) {
			throw new ObjNotFoundException("Objeto não encontrado! Id: " + id + ", tipo: " + Categoria.class.getName());
		}
		return obj;
	}

	public List<Categoria> findAll() {
		return repo.findAll();

	}

	public Categoria insert(Categoria obj) {
		obj.setId(null);
		return repo.save(obj);
	}

	public Categoria update(Categoria obj) {
		find(obj.getId());
		return repo.save(obj);
	}

	public void delete(Integer id) {
		find(id);
		try {
			repo.delete(id);

		} catch (DataIntegrityViolationException e) {
			throw new DataIntegreityException("Não é possível excluir uma categoria que possui produtos!!!");
		}

	}

	public Page<Categoria> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = new PageRequest(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repo.findAll(pageRequest);

	}
	
	public Categoria fromDTO(CategoriaDTO objDto) {
		return new Categoria(objDto.getId(), objDto.getNome());
	}

}
