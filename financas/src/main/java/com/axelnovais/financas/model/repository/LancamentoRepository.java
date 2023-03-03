package com.axelnovais.financas.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.axelnovais.financas.model.entity.Lancamento;

public interface LancamentoRepository extends JpaRepository<Lancamento, Long>{

}
