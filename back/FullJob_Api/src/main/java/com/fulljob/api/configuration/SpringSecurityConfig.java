package com.fulljob.api.configuration;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .cors(Customizer.withDefaults())

                .authorizeHttpRequests(authorize -> authorize
                        // Aqui manejaremos las rutas filtrando por .hasAuthority("ROLE_USER",
                        // "ROLE_ADMIN"), .permitAll() o simplemente .authenticated()
                        // AUTHORIZATION
                        .requestMatchers(HttpMethod.POST, "/auth/login", "/auth/register").permitAll()

                        .requestMatchers(HttpMethod.GET, "/auth/me").authenticated()

                        // CATEGORIA
                        .requestMatchers(HttpMethod.GET,
                                "/categorias",
                                "/categorias/{id}",
                                "/categorias/buscar/{nombre}")
                        .permitAll()

                        .requestMatchers(HttpMethod.POST, "/categorias").hasAuthority("ROLE_ADMON")
                        .requestMatchers(HttpMethod.PUT, "/categorias/{id}").hasAuthority("ROLE_ADMON")
                        .requestMatchers(HttpMethod.DELETE, "/categorias/{id}").hasAuthority("ROLE_ADMON")

                        // VACANTE
                        .requestMatchers(HttpMethod.GET,
                                "/vacantes",
                                "/vacantes/{id}",
                                "/vacantes/buscar/{nombre}",
                                "/vacantes/categoria/{idCategoria}")
                        .permitAll()

                        .requestMatchers(HttpMethod.POST, "/vacantes").hasAuthority("ROLE_EMPRESA")
                        .requestMatchers(HttpMethod.PUT, "/vacantes/{id}").hasAuthority("ROLE_EMPRESA")
                        .requestMatchers(HttpMethod.DELETE, "/vacantes/{id}").hasAuthority("ROLE_EMPRESA")

                        // EMPRESA
                        // role_admon
                        .requestMatchers(HttpMethod.GET, 
                                "/empresas",
                                "/empresas/{id}",
                                "/empresas/buscar/{nombre}"
                                )
                                .hasAuthority("ROLE_ADMON")
                        .requestMatchers(HttpMethod.POST, "/empresas/register").hasAuthority("ROLE_ADMON")
                        .requestMatchers(HttpMethod.PUT, 
                                "/empresas/{id}",
                                "/desactivar/{id}",
                                "/activar/{id}"
                                )
                                .hasAuthority("ROLE_ADMON")
                        .requestMatchers(HttpMethod.DELETE, "/empresas/{id}").hasAuthority("ROLE_ADMON")

                        // SOLICITUD
                        // role_user
                        .requestMatchers(HttpMethod.GET, "/solicitudes/mis-solicitudes").hasAuthority("ROLE_CLIENTE")

                        .requestMatchers(HttpMethod.POST, "/solicitudes").hasAuthority("ROLE_CLIENTE")
                        .requestMatchers(HttpMethod.DELETE, "/solicitudes/{id}").hasAuthority("ROLE_CLIENTE")

                        // role_empresa
                        .requestMatchers(HttpMethod.GET, "/solicitudes/vacante/{idVacante}")
                        .hasAuthority("ROLE_EMPRESA")
                        .requestMatchers(HttpMethod.PUT,
                                "/solicitudes/adjudicar/{id}",
                                "/solicitudes/desadjudicar/{id}")
                        .hasAuthority("ROLE_EMPRESA")

                        // USUARIO

                        .anyRequest().authenticated())

                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:4200"));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE"));
        configuration.setAllowedHeaders(List.of("Authorization", "Content-Type"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

}