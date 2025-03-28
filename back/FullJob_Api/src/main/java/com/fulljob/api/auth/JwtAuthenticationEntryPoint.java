package com.fulljob.api.auth;



import java.io.IOException;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
	
	/**
	 * Esta clase se encarga de manejar los errores de acceso cuando alguien intenta entrar sin estar autenticado.
	 *
	 * Cuando un usuario intenta acceder a una parte protegida de la aplicación sin enviar un token válido
	 * (o sin enviar ningún token), Spring Security llama automáticamente a este componente.
	 *
	 * Lo que hace es devolver un error 401, que significa "No autorizado", junto con un mensaje que dice
	 * "Usuario no autorizado".
	 *
	 * Este tipo de clase es útil cuando trabajamos con JWT, ya que controla qué pasa cuando alguien no está
	 * autenticado correctamente.
	 */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
            throws IOException, ServletException {
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Usuario no autorizado");
    }
}
