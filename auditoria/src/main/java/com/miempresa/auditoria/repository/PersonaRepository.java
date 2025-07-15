package com.miempresa.auditoria.repository;

import com.miempresa.auditoria.model.Persona;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonaRepository extends JpaRepository<Persona, Long> {
}