package com.poc.servicemesh.registro_service.controller;

import com.poc.servicemesh.registro_service.dto.RegistroRequest;
import com.poc.servicemesh.registro_service.dto.RegistroResponse;
import com.poc.servicemesh.registro_service.service.RegistroService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/registros")
@RequiredArgsConstructor
public class RegistroController {

    private final RegistroService service;

    @PostMapping
    public ResponseEntity<RegistroResponse> criar(@RequestBody RegistroRequest request) {
        RegistroResponse response = service.criar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RegistroResponse> buscarPorId(@PathVariable Long id) {
        RegistroResponse response = service.buscarPorId(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<RegistroResponse>> listarTodos() {
        List<RegistroResponse> response = service.listarTodos();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/rotina/{rotinaId}")
    public ResponseEntity<List<RegistroResponse>> listarPorRotina(@PathVariable Long rotinaId) {
        List<RegistroResponse> response = service.listarPorRotina(rotinaId);
        return ResponseEntity.ok(response);
    }
}