package com.poc.servicemesh.rotina_service.service;

import com.poc.servicemesh.rotina_service.domain.Rotina;
import com.poc.servicemesh.rotina_service.dto.RotinaRequest;
import com.poc.servicemesh.rotina_service.dto.RotinaResponse;
import com.poc.servicemesh.rotina_service.repository.RotinaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class RotinaService {

    private final RotinaRepository repository;

    public RotinaResponse criar(RotinaRequest request) {
        log.info("Criando nova rotina: {}", request.getNome());

        Rotina rotina = new Rotina();
        rotina.setNome(request.getNome());
        rotina.setDescricao(request.getDescricao());

        Rotina salva = repository.save(rotina);
        log.info("Rotina criada com ID: {}", salva.getId());

        return toResponse(salva);
    }

    public RotinaResponse buscarPorId(Long id) {
        log.info("Buscando rotina com ID: {}", id);

        Rotina rotina = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Rotina não encontrada"));

        return toResponse(rotina);
    }

    public List<RotinaResponse> listarTodas() {
        log.info("Listando todas as rotinas");

        return repository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    private RotinaResponse toResponse(Rotina rotina) {
        return new RotinaResponse(
                rotina.getId(),
                rotina.getNome(),
                rotina.getDescricao(),
                rotina.getCriadoEm()
        );
    }
}