package com.teste.desafio.controller;

import com.teste.desafio.Service.AssembleiaService;
import com.teste.desafio.entity.Pauta;
import com.teste.desafio.entity.Sessao;
import com.teste.desafio.request.RequestCadastrarPauta;
import com.teste.desafio.request.RequestCadastrarSessao;
import com.teste.desafio.request.RequestCadastrarVoto;
import com.teste.desafio.response.ResponseResultadoVoto;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;


@Controller
@RequiredArgsConstructor
public class AssembleiaController {
    private final AssembleiaService assembleiaService;

    @PostMapping(path = "/cadastrar-pauta")
    @Operation(summary = "Criar nova pauta")
    public ResponseEntity<Pauta> cadastrarPauta(@RequestBody RequestCadastrarPauta requestCadastrarPauta){
        return new ResponseEntity<>(assembleiaService
                                            .cadastrarPauta(requestCadastrarPauta.getPauta()), HttpStatus.CREATED);
    }

    @PostMapping(path = "/cadastrar-sessao")
    @Operation(summary = "Criar nova sessao")
    public ResponseEntity<Sessao> cadastrarSessao(@RequestBody RequestCadastrarSessao requestCadastrarSessao){
        return new ResponseEntity<>(assembleiaService
                                            .cadastrarSessao(requestCadastrarSessao.getNomePauta(),
                                                             requestCadastrarSessao.getLimiteSessao()),
                                    HttpStatus.CREATED);
    }

    @PostMapping(path = "/votar-pauta")
    @Operation(summary = "Associado realizar votação")
    public ResponseEntity<String> votarPauta(@RequestBody RequestCadastrarVoto requestCadastrarVoto){
        return new ResponseEntity<>(assembleiaService
                                            .votoPauta(requestCadastrarVoto.getCpf(),
                                                       requestCadastrarVoto.getNomePauta(),
                                                       requestCadastrarVoto.getVoto()), HttpStatus.ACCEPTED);
    }


    @GetMapping(path = "/resultado-pauta")
    @Operation(summary = "Resultado pauta")
    public ResponseEntity<ResponseResultadoVoto> resultadoPauta(@RequestBody RequestCadastrarPauta requestCadastrarPauta){
        return new ResponseEntity<>(assembleiaService.resultadoVoto(requestCadastrarPauta.getPauta()), HttpStatus.OK);
    }
}
