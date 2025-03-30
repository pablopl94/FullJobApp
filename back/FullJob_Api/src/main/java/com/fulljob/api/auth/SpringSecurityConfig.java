package com.fulljob.api.auth;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
public class SpringSecurityConfig {
	/**
	 * Esta clase configura la seguridad de toda la aplicación.
	 *
	 * Lo que hemos hecho aquí es definir cómo se comporta Spring Security al proteger las rutas.
	 *
	 * Paso a paso:
	 * 1. Hemos configurado qué rutas puede usar cualquier usuario (sin estar logueado), y cuáles solo pueden usar ciertos roles:
	 *    - Por ejemplo, para iniciar sesión o registrarse, no hace falta estar autenticado.
	 *    - Las rutas de vacantes, empresas, solicitudes, etc., están protegidas y solo accesibles si tienes el rol adecuado.
	 *
	 * 2. Hemos añadido un filtro (JwtAuthenticationFilter) que revisa el token JWT en cada solicitud.
	 *    - Si el token es válido, se identifica al usuario automáticamente.
	 *    - Si no lo es, usamos JwtAuthenticationEntryPoint para devolver un error 401 (no autorizado).
	 *
	 * 3. Hemos desactivado la seguridad CSRF porque no usamos sesiones, sino tokens JWT.
	 *
	 * 4. También hemos configurado CORS para permitir que el frontend que está en localhost:4200
	 *    pueda hacer peticiones a esta API sin problemas.
	 *
	 * 5. Por último, se define que la app es "stateless", es decir, que no guarda sesión entre peticiones.
	 */

	
	// Inyectamos el filtro JWT que revisa el token en cada solicitud
	@Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;
	
	// Inyectamos el manejador de errores de autenticación para devolver un 401 cuando no se autoriza
	@Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    // Creamos un bean para encriptar contraseñas usando BCrypt 
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Configuramos el AuthenticationManager que gestiona la autenticación
    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration config)
            throws Exception {
        return config.getAuthenticationManager();
    }

    // Configuración principal de seguridad
    // Este bloque define las reglas de acceso y la integración de JWT
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // Deshabilitamos CSRF porque usamos JWT y la app es stateless
            .cors(Customizer.withDefaults()) // Usamos configuración CORS por defecto
            .authorizeHttpRequests(authorize -> {
            	
            	// =================== AUTHENTICATION ===================
            	authorize.requestMatchers(HttpMethod.POST, "/auth/login", "/auth/alta/cliente").permitAll();
            	authorize.requestMatchers(HttpMethod.POST, "/auth/alta/empresa").hasAuthority("ROLE_ADMON");
            	authorize.requestMatchers(HttpMethod.GET, "/auth/obtenerusuario").authenticated();

            	// =================== CATEGORIAS ======================
            	authorize.requestMatchers(HttpMethod.GET, "/categorias").permitAll();
            	authorize.requestMatchers(HttpMethod.POST, "/categorias").hasAuthority("ROLE_ADMON");
            	authorize.requestMatchers(HttpMethod.PUT, "/categorias/{id}").hasAuthority("ROLE_ADMON");
            	authorize.requestMatchers(HttpMethod.DELETE, "/categorias/{id}").hasAuthority("ROLE_ADMON");

            	// =================== VACANTES ========================
            	authorize.requestMatchers(HttpMethod.GET, "/vacantes", "/vacantes/{id}").permitAll();
            	authorize.requestMatchers(HttpMethod.GET, "/vacantes/filtrar").permitAll();
            	authorize.requestMatchers(HttpMethod.GET, "/vacantes/misvacantes").hasAuthority("ROLE_EMPRESA");
            	authorize.requestMatchers(HttpMethod.POST, "/vacantes/publicar").hasAuthority("ROLE_EMPRESA");
            	authorize.requestMatchers(HttpMethod.PUT, "/vacantes/editar/{id}").hasAuthority("ROLE_EMPRESA");
            	authorize.requestMatchers(HttpMethod.DELETE, "/vacantes/cancelar/{id}").hasAuthority("ROLE_EMPRESA");

            	// =================== EMPRESAS ========================
            	authorize.requestMatchers(HttpMethod.GET, "/empresas", "/empresas/{id}", "/empresas/buscar/{nombre}").hasAuthority("ROLE_ADMON");
            	authorize.requestMatchers(HttpMethod.GET, "/empresas/miperfil").hasAuthority("ROLE_EMPRESA");
            	authorize.requestMatchers(HttpMethod.POST, "/empresas/register").hasAuthority("ROLE_ADMON");
            	authorize.requestMatchers(HttpMethod.PUT, "/empresas/{id}", "/desactivar/{id}", "/activar/{id}").hasAuthority("ROLE_ADMON");
            	authorize.requestMatchers(HttpMethod.PUT, "/empresas/update").hasAuthority("ROLE_EMPRESA");
            	authorize.requestMatchers(HttpMethod.DELETE, "/empresas/{id}").hasAuthority("ROLE_ADMON");
            	authorize.requestMatchers(HttpMethod.PUT, "/empresa/eliminar/{id}").hasAuthority("ROLE_ADMON");

            	// =================== SOLICITUDES =====================
            	authorize.requestMatchers(HttpMethod.POST, "/solicitudes").hasAuthority("ROLE_CLIENTE");
            	authorize.requestMatchers(HttpMethod.GET, "/solicitudes/mis-solicitudes").hasAuthority("ROLE_CLIENTE");
            	authorize.requestMatchers(HttpMethod.DELETE, "/solicitudes/{id}").hasAuthority("ROLE_CLIENTE");
            	authorize.requestMatchers(HttpMethod.GET, "/solicitudes/vacante/{idVacante}").hasAuthority("ROLE_EMPRESA");
            	authorize.requestMatchers(HttpMethod.PUT, "/solicitudes/asignar/{id}").hasAuthority("ROLE_EMPRESA");

            	// =================== CLIENTE =========================
            	authorize.requestMatchers(HttpMethod.PUT, "/cliente/desactivar/{id}").hasAuthority("ROLE_ADMON");
            	authorize.requestMatchers(HttpMethod.POST, "/cliente/admin").hasAuthority("ROLE_ADMON");
            	authorize.requestMatchers(HttpMethod.PUT, "/cliente/admin/{id}").hasAuthority("ROLE_ADMON");
            	authorize.requestMatchers(HttpMethod.DELETE, "/cliente/admin/{id}").hasAuthority("ROLE_ADMON");

            	// =================== DEFAULT =========================
            	authorize.anyRequest().authenticated();


            })
            .exceptionHandling(exception -> exception
                    .authenticationEntryPoint(jwtAuthenticationEntryPoint)) // Usa este manejador si falla la autenticación
            .sessionManagement(session -> session
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)); // La app no guarda sesión

        // Agregamos el filtro JWT antes del filtro de autenticación de Spring
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    // Configuración de CORS para permitir peticiones desde el frontend
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:4200")); // Permitir el frontend local
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE")); // Métodos permitidos
        configuration.setAllowedHeaders(List.of("Authorization", "Content-Type")); // Headers permitidos
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
