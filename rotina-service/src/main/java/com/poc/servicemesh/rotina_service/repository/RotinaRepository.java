package com.poc.servicemesh.rotina_service.repository;

import com.poc.servicemesh.rotina_service.domain.Rotina;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RotinaRepository extends JpaRepository<Rotina, Long> {
}