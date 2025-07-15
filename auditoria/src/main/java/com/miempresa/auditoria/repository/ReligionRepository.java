package com.miempresa.auditoria.repository;

import com.miempresa.auditoria.model.Religion;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ReligionRepository extends JpaRepository<Religion, Long> {
    Optional<Religion> findByNombre(String nombre);
}