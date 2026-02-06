package com.poc.servicemesh.registro_service.service;

import com.poc.servicemesh.registro_service.client.RotinaClient;
import com.poc.servicemesh.registro_service.domain.Registro;
import com.poc.servicemesh.registro_service.dto.RegistroRequest;
import com.poc.servicemesh.registro_service.dto.RegistroResponse;
import com.poc.servicemesh.registro_service.dto.RotinaDTO;
import com.poc.servicemesh.registro_service.repository.RegistroRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class RegistroService {

    private final RegistroRepository repository;
    private final RotinaClient rotinaClient;

    public RegistroResponse criar(RegistroRequest request) {
        log.info("Criando registro para rotina ID: {}", request.getRotinaId());

        // Valida se rotina existe (comunicação entre microsserviços)
        RotinaDTO rotina = rotinaClient.buscarPorId(request.getRotinaId());
        log.info("Rotina encontrada: {}", rotina.getNome());

        Registro registro = new Registro();
        registro.setRotinaId(request.getRotinaId());
        registro.setDataExecucao(request.getDataExecucao());
        registro.setObservacao(request.getObservacao());

        Registro salvo = repository.save(registro);
        log.info("Registro criado com ID: {}", salvo.getId());

        return toResponse(salvo, rotina);
    }

    public RegistroResponse buscarPorId(Long id) {
        log.info("Buscando registro com ID: {}", id);

        Registro registro = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Registro não encontrado"));

        RotinaDTO rotina = rotinaClient.buscarPorId(registro.getRotinaId());

        return toResponse(registro, rotina);
    }

    public List<RegistroResponse> listarTodos() {
        log.info("Listando todos os registros");

        return repository.findAll().stream()
                .map(registro -> {
                    RotinaDTO rotina = rotinaClient.buscarPorId(registro.getRotinaId());
                    return toResponse(registro, rotina);
                })
                .collect(Collectors.toList());
    }

    public List<RegistroResponse> listarPorRotina(Long rotinaId) {
        log.info("Listando registros da rotina ID: {}", rotinaId);

        RotinaDTO rotina = rotinaClient.buscarPorId(rotinaId);

        return repository.findByRotinaId(rotinaId).stream()
                .map(registro -> toResponse(registro, rotina))
                .collect(Collectors.toList());
    }

    private RegistroResponse toResponse(Registro registro, RotinaDTO rotina) {
        return new RegistroResponse(
                registro.getId(),
                registro.getRotinaId(),
                rotina.getNome(),
                registro.getDataExecucao(),
                registro.getObservacao(),
                registro.getCriadoEm()
        );
    }
}