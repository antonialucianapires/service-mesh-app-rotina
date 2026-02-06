package com.poc.servicemesh.registro_service.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class RegistroRequest {
    private Long rotinaId;
    private LocalDate dataExecucao;
    private String observacao;
}