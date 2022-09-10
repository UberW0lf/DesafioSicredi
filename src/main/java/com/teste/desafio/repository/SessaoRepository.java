package com.teste.desafio.repository;

import com.teste.desafio.entity.Sessao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SessaoRepository extends JpaRepository<Sessao, Long> {
    Optional<Sessao> getSessaoById(Long id);
}