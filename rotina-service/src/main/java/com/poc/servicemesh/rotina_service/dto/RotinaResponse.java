package com.poc.servicemesh.rotina_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class RotinaResponse {
    private Long id;
    private String nome;
    private String descricao;
    private LocalDateTime criadoEm;
}