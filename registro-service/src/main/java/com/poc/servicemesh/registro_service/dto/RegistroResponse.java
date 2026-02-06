package com.poc.servicemesh.registro_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class RegistroResponse {
    private Long id;
    private Long rotinaId;
    private String rotinaNome;  // virá do rotina-service
    private LocalDate dataExecucao;
    private String observacao;
    private LocalDateTime criadoEm;
}