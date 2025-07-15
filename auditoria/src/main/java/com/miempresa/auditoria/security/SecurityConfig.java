package com.miempresa.auditoria.security;

import com.miempresa.auditoria.service.UsuarioService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    private final UsuarioService usuarioService;
    private final JwtUtils jwtUtils;

    public SecurityConfig(UsuarioService usuarioService, JwtUtils jwtUtils) {
        this.usuarioService = usuarioService;
        this.jwtUtils = jwtUtils;
    }

    @Bean
    public JwtAuthenticationFilter authenticationJwtTokenFilter() {
        return new JwtAuthenticationFilter(jwtUtils, usuarioService);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // Deshabilita la protección CSRF para facilitar pruebas (solo temporal)
                .csrf(csrf -> csrf.disable())

                // Configura la sesión como sin estado, porque usas JWT
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                // Cambié esta parte para permitir todas las solicitudes sin autenticación
                .authorizeHttpRequests(auth -> auth
                                .anyRequest().permitAll()  // Aquí permito todas las rutas sin autenticación
                        // Si quieres luego volver a proteger "/api/auth/**", reemplaza esta línea por:
                        // .requestMatchers("/api/auth/**").permitAll()
                        // .anyRequest().authenticated()
                )

                // El filtro JWT queda, pero no bloquea nada porque todas las rutas están permitidas
                .addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}