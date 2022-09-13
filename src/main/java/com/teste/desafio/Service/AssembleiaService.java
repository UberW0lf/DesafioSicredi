package com.teste.desafio.Service;

import com.teste.desafio.CpfClient;
import com.teste.desafio.entity.Pauta;
import com.teste.desafio.entity.Sessao;
import com.teste.desafio.entity.VotoAssociado;
import com.teste.desafio.repository.PautaRepository;
import com.teste.desafio.repository.SessaoRepository;
import com.teste.desafio.repository.VotoAssociadoRepository;
import com.teste.desafio.response.ResponseCpfClient;
import com.teste.desafio.response.ResponseResultadoVoto;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AssembleiaService {
    @Autowired
    private final PautaRepository pautaRepository;

    @Autowired
    private final SessaoRepository sessaoRepository;

    @Autowired
    private final VotoAssociadoRepository votoAssociadoRepository;

    @Autowired
    private final CpfClient cpfClient;


    public static final Long DEFAULT_LIMITE = 1L;
    public Pauta cadastrarPauta(String nome){
        if (pautaRepository.getPautaByNome(nome).isPresent()){
            throw new RuntimeException("Pauta ja cadastrada");
        }
        return pautaRepository.save(new Pauta()
                                            .toBuilder()
                                            .nome(nome)
                                            .build());

    }

    @Transactional
    public Sessao cadastrarSessao(String nomePauta, Long minutosLimiteSessao){
        Pauta pauta = pautaRepository.getPautaByNome(nomePauta)
                .orElseThrow(() -> new RuntimeException("Pauta não existe"));
        if (Objects.nonNull(pauta.getIdSessao())){
            throw new RuntimeException("Sessao ja cadastrada");
        }
        Sessao sessao = sessaoRepository.save(new Sessao()
                                                    .toBuilder()
                                                    .duracaoSessao(Objects.isNull(minutosLimiteSessao) ? DEFAULT_LIMITE : minutosLimiteSessao)
                                                    .abertura(LocalDateTime.now()).build()
        );
        pauta.setIdSessao(sessao.getId());
        pautaRepository.save(pauta);
        return sessao;
    }

    public String votoPauta(String cpf, String nomePauta, String voto){
        ResponseCpfClient ableToVote;
        try{
            ableToVote = cpfClient.validaVotoPorCpf(cpf);
        }catch (FeignException fe){
            if(fe.status() == 404){
                throw new RuntimeException("Formato de cpf inválido " + fe.getMessage());
            }
            throw new RuntimeException(fe.getMessage());
        }
        if(ableToVote.getStatus().equals("UNABLE_TO_VOTE")) {
            throw new RuntimeException("Associado com cpf inválido");
        }
        Pauta pauta = pautaRepository.getPautaByNome(nomePauta)
                .orElseThrow(() -> new RuntimeException("Pauta não existe"));
        Sessao sessao = sessaoRepository.getSessaoById(pauta.getId())
                .orElseThrow(() -> new RuntimeException("Sessao não existe"));
        if (Objects.isNull(pauta.getId()) || !sessao.sessaoAberta()){
            throw new RuntimeException("Sessao não cadastrada ou Sessão já finalizada");
        }
        if (votoAssociadoRepository.getAllByCpfAndPauta(cpf,pauta).isPresent()){
            throw new RuntimeException("Associado já votou nesta pauta");
        }
        votoAssociadoRepository.save(new VotoAssociado().toBuilder().voto(voto.toUpperCase(Locale.ROOT)).cpf(cpf).pauta(pauta).build());

        return "Voto realizado com sucesso";

    }

    public ResponseResultadoVoto resultadoVoto(String nomePauta){
        Pauta pauta = pautaRepository.getPautaByNome(nomePauta)
                .orElseThrow(() -> new RuntimeException("Pauta não existe"));

        ResponseResultadoVoto responseResultadoVoto = new ResponseResultadoVoto();
        responseResultadoVoto.setQuantidadeVotosSim(String.valueOf(votoAssociadoRepository.countAllByVotoAndPauta("SIM", pauta)
                                                            .orElseThrow(() -> new RuntimeException("Pauta não existe"))));
        responseResultadoVoto.setQuantidadeVotosNao(String.valueOf(votoAssociadoRepository.countAllByVotoAndPauta("NAO", pauta)
                                                            .orElseThrow(() -> new RuntimeException("Pauta não existe"))));
        return responseResultadoVoto;

    }

}
