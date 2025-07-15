package com.miempresa.auditoria.service;

import com.miempresa.auditoria.model.Persona;
import com.miempresa.auditoria.model.Religion;
import com.miempresa.auditoria.repository.PersonaRepository;
import com.miempresa.auditoria.repository.ReligionRepository;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;

@Service
public class PersonaService {

    private final PersonaRepository personaRepository;
    private final ReligionRepository religionRepository;

    // Pepper estático solo como ejemplo, en producción usar variables de entorno
    private static final String PEPPER = "S3cr3tP3pp3r";

    public PersonaService(PersonaRepository personaRepository, ReligionRepository religionRepository) {
        this.personaRepository = personaRepository;
        this.religionRepository = religionRepository;
    }

    public java.util.List<Persona> listar() {
        return personaRepository.findAll();
    }

    public Persona obtenerPorId(Long id) {
        return personaRepository.findById(id).orElse(null);
    }

    public void eliminar(Long id) {
        personaRepository.deleteById(id);
    }

    public Persona guardar(Persona persona) {
        if (persona.getReligion() != null) {
            Religion religionEntrada = persona.getReligion();

            Religion religionGuardada = null;

            if (religionEntrada.getId() != null) {
                religionGuardada = religionRepository.findById(religionEntrada.getId())
                        .orElseThrow(() -> new RuntimeException("Religión con ID " + religionEntrada.getId() + " no encontrada"));
            } else if (religionEntrada.getNombre() != null && !religionEntrada.getNombre().isBlank()) {
                religionGuardada = religionRepository.findByNombre(religionEntrada.getNombre())
                        .orElseGet(() -> {
                            Religion nuevaReligion = new Religion();
                            nuevaReligion.setNombre(religionEntrada.getNombre());
                            return religionRepository.save(nuevaReligion);
                        });
            } else {
                throw new RuntimeException("Religión inválida o incompleta");
            }

            persona.setReligion(religionGuardada);
        } else {
            throw new RuntimeException("La religión no puede ser nula");
        }

        // Ofuscar el RUT
        String rutOfuscado = ofuscarRut(persona.getRut());
        persona.setRutOfuscado(rutOfuscado);
        persona.setRut(null); // Limpiar campo transient

        return personaRepository.save(persona);
    }

    // --- Lógica para ofuscar RUT (base64 codificado con "salt + pepper") ---
    private String ofuscarRut(String rut) {
        String salt = generarSalt();
        return Base64.getEncoder().encodeToString((salt + rut + PEPPER).getBytes());
    }

    private String generarSalt() {
        byte[] salt = new byte[4];
        new SecureRandom().nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }
}