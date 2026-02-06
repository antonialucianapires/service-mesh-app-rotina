package com.poc.servicemesh.registro_service.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RotinaDTO {
    private Long id;
    private String nome;
    private String descricao;
    private LocalDateTime criadoEm;
}