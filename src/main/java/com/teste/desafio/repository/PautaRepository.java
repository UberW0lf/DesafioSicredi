package com.teste.desafio.repository;


import com.teste.desafio.entity.Pauta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PautaRepository extends JpaRepository<Pauta, Long> {
    Optional<Pauta> getPautaByNome(String nome);
}