package com.poc.servicemesh.registro_service.repository;

import com.poc.servicemesh.registro_service.domain.Registro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RegistroRepository extends JpaRepository<Registro, Long> {
    List<Registro> findByRotinaId(Long rotinaId);
}