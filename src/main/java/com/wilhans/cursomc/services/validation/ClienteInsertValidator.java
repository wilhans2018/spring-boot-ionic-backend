package com.wilhans.cursomc.services.validation;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.wilhans.cursomc.domain.Cliente;
import com.wilhans.cursomc.domain.enums.TipoCliente;
import com.wilhans.cursomc.dto.ClienteNewDTO;
import com.wilhans.cursomc.repositories.ClienteRepository;
import com.wilhans.cursomc.resources.exceptions.FieldMessage;
import com.wilhans.cursomc.services.exceptions.utils.BR;

public class ClienteInsertValidator implements ConstraintValidator<ClienteInsert, ClienteNewDTO> {
	
	@Autowired
	private ClienteRepository repo;
	
	@Override
	public void initialize(ClienteInsert ann) {
	}

	@Override
	public boolean isValid(ClienteNewDTO objDto, ConstraintValidatorContext context) {
		
		List<FieldMessage> list = new ArrayList<>();
		
		if (objDto.getTipo().equals((TipoCliente.PESSOAFISICA.getCodigo())) && !BR.isValidCPF(objDto.getCpfOuCnpj())) {
			list.add(new FieldMessage("cpfOuCnpj", "CPF inváido"));			
		}
		
		if (objDto.getTipo().equals((TipoCliente.PESSOAJURIDICA.getCodigo())) && !BR.isValidCNPJ(objDto.getCpfOuCnpj())) {
			list.add(new FieldMessage("cpfOuCnpj", "CNPJ inváido"));			
		}
		
		Cliente aux = repo.findByEmail(objDto.getEmail());
		if (aux != null) {
			list.add(new FieldMessage("email", "Email já existente"));	
		}
		
// inclua os testes aqui, inserindo erros na lista
		for (FieldMessage e : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFildName())
					.addConstraintViolation();
		}
		return list.isEmpty();
	}
}