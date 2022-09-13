package com.teste.desafio;

import com.teste.desafio.response.ResponseCpfClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@FeignClient(name = "cpfClient", url = "https://user-info.herokuapp.com/")
public interface CpfClient {
    @GetMapping("/users/{cpf}")
    ResponseCpfClient validaVotoPorCpf(@PathVariable("cpf") String cpf);

}
