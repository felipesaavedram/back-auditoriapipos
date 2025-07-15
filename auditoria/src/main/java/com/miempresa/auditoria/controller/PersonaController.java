package com.miempresa.auditoria.controller;

import com.miempresa.auditoria.model.Persona;
import com.miempresa.auditoria.service.PersonaService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/personas")
@CrossOrigin(origins = "*")
public class PersonaController {

    private final PersonaService personaService;

    public PersonaController(PersonaService personaService) {
        this.personaService = personaService;
    }

    @GetMapping
    public ResponseEntity<List<Persona>> listar() {
        return ResponseEntity.ok(personaService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Persona> obtener(@PathVariable Long id) {
        Persona persona = personaService.obtenerPorId(id);
        if (persona == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(persona);
    }

    @PostMapping
    public ResponseEntity<Persona> crear(@Valid @RequestBody Persona persona) {
        return ResponseEntity.ok(personaService.guardar(persona));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        personaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}