package com.wilhans.cursomc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wilhans.cursomc.domain.Pagamento;;

@Repository
public interface PagamentoRepository extends JpaRepository<Pagamento, Integer> {	

}
