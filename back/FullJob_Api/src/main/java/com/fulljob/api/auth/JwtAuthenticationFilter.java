package com.fulljob.api.auth;

import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fulljob.api.services.impl.UsuarioDetallesServiceImpl;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Este filtro se ejecuta en cada solicitud que llega a la aplicación.
 *
 * Lo que hemos hecho aquí es revisar si la solicitud lleva un token JWT válido en el encabezado.
 * 
 * Paso a paso:
 * 1. Leemos el encabezado "Authorization" de la solicitud.
 * 2. Verificamos que empiece por "Bearer ", que es como normalmente se envían los tokens.
 * 3. Si tiene el formato correcto, extraemos el token JWT.
 * 4. Usamos ese token para obtener el nombre del usuario.
 * 5. Si el usuario no está autenticado aún y el token es válido, cargamos sus datos desde la base de datos.
 * 6. Creamos una autenticación para ese usuario y la guardamos en el contexto de seguridad de Spring.
 *
 * De esta forma, el usuario queda identificado (logueado) en la aplicación mientras dure la solicitud.
 *
 * Si el token no está bien o ha expirado, simplemente no autenticamos a nadie.
 */
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    // Inyectamos el servicio que carga el usuario desde la base de datos.
    @Autowired
    private UsuarioDetallesServiceImpl usuarioDetallesService;

    // Inyectamos la herramienta que crea y valida los tokens JWT.
    @Autowired
    private JwtUtils jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // Mostramos la ruta que entra
        logger.info("Petición: {} {}", request.getMethod(), request.getRequestURI());

        // Sacamos lo que viene en el encabezado "Authorization"
        final String requestTokenHeader = request.getHeader("Authorization");
        String username = null;
        String jwtToken = null;

        // Revisamos que el encabezado empiece con "Bearer "
        if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
            // Quitamos "Bearer " para quedarnos solo con el token
            jwtToken = requestTokenHeader.substring(7);
            try {
                // Extraemos el nombre de usuario del token
                username = jwtUtil.extractUsername(jwtToken);
                logger.debug("Token recibido correctamente. Usuario: {}", username);
            } catch (ExpiredJwtException e) {
                logger.warn("El token ha expirado: {}", e.getMessage());
            } catch (Exception e) {
                logger.error("Error al extraer el token JWT", e);
            }
        } else {
            logger.warn("Token inválido, no empieza con 'Bearer ' o está ausente");
        }

        // Si tenemos un nombre de usuario y no hay un usuario autenticado aún
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            // Cargamos los datos del usuario
            UserDetails userDetails = usuarioDetallesService.loadUserByUsername(username);
            // Si el token es válido
            if (jwtUtil.validateToken(jwtToken, userDetails)) {
                // Creamos una autenticación con esos datos
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                // Ponemos el usuario en el contexto de seguridad (lo dejamos "logueado")
                SecurityContextHolder.getContext().setAuthentication(authToken);
                logger.info("Usuario autenticado: {}", username);
            } else {
                logger.warn("Token no válido para el usuario: {}", username);
            }
        }

        // Si nadie fue autenticado, lo avisamos
        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            logger.warn("No hay usuario autenticado. Si la ruta lo requiere, se devolverá 401.");
        }

        // Seguimos con el proceso de la solicitud
        filterChain.doFilter(request, response);
    }
}
