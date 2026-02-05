package com.poc.servicemesh.rotina_service.controller;

import com.poc.servicemesh.rotina_service.dto.RotinaRequest;
import com.poc.servicemesh.rotina_service.dto.RotinaResponse;
import com.poc.servicemesh.rotina_service.service.RotinaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rotinas")
@RequiredArgsConstructor
public class RotinaController {

    private final RotinaService service;

    @PostMapping
    public ResponseEntity<RotinaResponse> criar(@RequestBody RotinaRequest request) {
        RotinaResponse response = service.criar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RotinaResponse> buscarPorId(@PathVariable Long id) {
        RotinaResponse response = service.buscarPorId(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<RotinaResponse>> listarTodas() {
        List<RotinaResponse> response = service.listarTodas();
        return ResponseEntity.ok(response);
    }
}