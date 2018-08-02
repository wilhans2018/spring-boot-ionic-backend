package com.wilhans.cursomc.services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.wilhans.cursomc.domain.Cliente;
import com.wilhans.cursomc.domain.ItemPedido;
import com.wilhans.cursomc.domain.PagamentoComBoleto;
import com.wilhans.cursomc.domain.Pedido;
import com.wilhans.cursomc.domain.enums.EstadoPagamento;
import com.wilhans.cursomc.repositories.ClienteRepository;
import com.wilhans.cursomc.repositories.ItemPedidoRepository;
import com.wilhans.cursomc.repositories.PagamentoRepository;
import com.wilhans.cursomc.repositories.PedidoRepository;
import com.wilhans.cursomc.security.UserSS;
import com.wilhans.cursomc.services.exceptions.AuthorizantionException;
import com.wilhans.cursomc.services.exceptions.ObjNotFoundException;

@Service
public class PedidoService {

	@Autowired
	private PedidoRepository repo;

	@Autowired
	private BoletoService boletoService;

	@Autowired
	private PagamentoRepository pagamentoRepository;

	
	@Autowired
	private ProdutoService produtoservice;

	@Autowired
	private ItemPedidoRepository itemPedidoRepository;
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
	private ClienteRepository clienteRepository;
	

	
	@Autowired
	private ClienteService clienteService;

	public Pedido find(Integer id) {
		Pedido obj = repo.findOne(id);
		if (obj == null) {
			throw new ObjNotFoundException("Objeto não encontrado! Id: " + id + ", tipo: " + Pedido.class.getName());
		}
		return obj;
	}
	
	public Page<Pedido> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		
		UserSS user = UserService.authenticated();
		
		if (user == null) {
			throw new AuthorizantionException("Acesso negado");
		}
		
		PageRequest pageRequest = new PageRequest(page, linesPerPage, Direction.valueOf(direction), orderBy);
		Cliente cliente = clienteRepository.findOne(user.getId());
		
		return repo.findByCliente(cliente, pageRequest);
		
	}

	public Pedido insert(Pedido obj) {
		obj.setId(null);
		obj.setInstante(new Date());
		obj.setCliente(clienteService.find(obj.getCliente().getId()));
		obj.getPagamento().setEstado(EstadoPagamento.PENDENTE);
		obj.getPagamento().setPedido(obj);
		
		if (obj.getPagamento() instanceof PagamentoComBoleto) {
			PagamentoComBoleto pagto = (PagamentoComBoleto) obj.getPagamento();
			boletoService.preencherPagamentoComBoleto(pagto, obj.getInstante());
		}
		
		obj = repo.save(obj);

		pagamentoRepository.save(obj.getPagamento());
		for (ItemPedido ip : obj.getItens()) {
			ip.setDesconto(0.0);
			ip.setProduto(produtoservice.find(ip.getProduto().getId()));
			ip.setPreco(ip.getProduto().getPreco());
			ip.setPedido(obj);
		}

		itemPedidoRepository.save(obj.getItens());
		emailService.sendOrderConfirmationHtmlEmail(obj);

		return obj;
	}

}
