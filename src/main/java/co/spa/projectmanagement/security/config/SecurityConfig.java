package co.spa.projectmanagement.security.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@Slf4j
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        log.info("Inicializando configuración de seguridad...");

        return http
                .csrf(csrf -> {
                    log.info("Deshabilitando protección CSRF");
                    csrf.disable();
                })
                .authorizeHttpRequests(auth -> {
                    log.info("Configurando permisos de rutas:");
                    log.info(" -> Permitidas sin autenticación: /api/auth/**");
                    log.info(" -> Requieren autenticación: todas las demás");
                    auth.requestMatchers("/api/auth/**").permitAll()
                    .requestMatchers("/api/integrators/**").authenticated() // Protege integradores
                    .anyRequest().authenticated();
                })
                .sessionManagement(sess -> {
                    log.info("Configurando política de sesión: STATELESS");
                    sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
                })
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .cors(cors -> {
                    log.info("Configurando CORS...");
                    cors.configurationSource(request -> {
                        var config = new org.springframework.web.cors.CorsConfiguration();
                        config.addAllowedOrigin("http://localhost:5173"); // Cambia a la URL del backend
                        config.addAllowedMethod("*"); // Permitir todos los métodos (GET, POST, PUT, DELETE)
                        config.addAllowedHeader("*"); // Permitir todos los encabezados
                        config.setAllowCredentials(true); // Permitir enviar credenciales (como JWT)
                        return config;
                    });
                })
                .build();
    }
}