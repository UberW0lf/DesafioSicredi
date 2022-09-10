package com.teste.desafio.repository;


import com.teste.desafio.entity.Pauta;
import com.teste.desafio.entity.VotoAssociado;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VotoAssociadoRepository extends JpaRepository<VotoAssociado, String> {
    Optional<VotoAssociado> getAllByCpfAndPauta(String cpf, Pauta pauta);
    Optional<Long> countAllByVotoAndPauta(String voto, Pauta pauta);
}