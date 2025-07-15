package com.miempresa.auditoria.controller;

import com.miempresa.auditoria.model.Usuario;
import com.miempresa.auditoria.security.JwtUtils;
import com.miempresa.auditoria.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")  // Permite peticiones desde cualquier origen (útil para desarrollo)
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UsuarioService usuarioService;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

    public AuthController(AuthenticationManager authenticationManager,
                          UsuarioService usuarioService,
                          PasswordEncoder passwordEncoder,
                          JwtUtils jwtUtils) {
        this.authenticationManager = authenticationManager;
        this.usuarioService = usuarioService;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtils = jwtUtils;
    }


    @PostMapping("/register")
    public ResponseEntity<?> registrar(@Valid @RequestBody Usuario usuario) {
        System.out.println("Registrando usuario: " + usuario.getEmail());
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        Usuario guardado = usuarioService.guardar(usuario);
        System.out.println("Usuario guardado con ID: " + guardado.getId());
        return ResponseEntity.ok(guardado);
    }


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Usuario usuario) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        usuario.getEmail(), usuario.getPassword()));

        String token = jwtUtils.generateToken(authentication);


        return ResponseEntity.ok(new JwtResponse(token));
    }


    private static class JwtResponse {
        private final String token;

        public JwtResponse(String token) {
            this.token = token;
        }

        public String getToken() {
            return token;
        }
    }
}