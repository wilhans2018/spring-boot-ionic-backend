package com.wilhans.cursomc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wilhans.cursomc.domain.Pedido;;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Integer> {	

}
