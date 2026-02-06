package com.poc.servicemesh.registro_service.client;

import com.poc.servicemesh.registro_service.dto.RotinaDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "rotina-service", url = "${rotina.service.url}")
public interface RotinaClient {

    @GetMapping("/api/rotinas/{id}")
    RotinaDTO buscarPorId(@PathVariable("id") Long id);
}
