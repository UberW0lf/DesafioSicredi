package com.teste.desafio.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder(toBuilder = true)
public class Sessao implements Domain {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime abertura;
    private Long duracaoSessao;

    public Boolean sessaoAberta() {
        LocalDateTime sessaoAux = this.abertura;
        return LocalDateTime.now().isBefore(sessaoAux.plusMinutes(this.duracaoSessao));
    }

}
