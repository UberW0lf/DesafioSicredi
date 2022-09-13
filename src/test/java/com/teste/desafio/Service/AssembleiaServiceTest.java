package com.teste.desafio.Service;

import com.teste.desafio.entity.Pauta;
import com.teste.desafio.repository.PautaRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.util.Optionals;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class AssembleiaServiceTest {

    @InjectMocks
    AssembleiaService assembleiaService;

    @Mock
    PautaRepository pautaRepository;

    @Test
    void cadastrarPauta() {
        String pautaNome = "Pauta teste";
        Pauta pauta = new Pauta().toBuilder().nome(pautaNome).build();
        Pauta pautaNull = null;

        when(pautaRepository.getPautaByNome(pautaNome)).thenReturn(Optional.ofNullable(pautaNull));
        when(pautaRepository.save(any())).thenReturn(pauta);

        Pauta pautaResultado = assembleiaService.cadastrarPauta(pautaNome);
        Assertions.assertEquals(pautaResultado.getNome(), pauta.getNome());

        when(pautaRepository.getPautaByNome(pautaNome)).thenReturn(Optional.ofNullable(pauta));

        Assertions.assertThrows(RuntimeException.class, () -> assembleiaService.cadastrarPauta(pautaNome));
    }

    @Test
    void cadastrarSessao() {
    }

    @Test
    void votoPauta() {
    }

    @Test
    void resultadoVoto() {
    }
}