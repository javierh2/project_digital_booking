package com.roomhotel.roomhotel.config;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.roomhotel.roomhotel.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

// JwtFilter intercepta cada solicitud entrante para verificar si incluye un token JWT válido en el encabezado Authorization.
// Si el token es válido, extrae el email del usuario,carga sus detalles de seguridad y establece la autenticación
// en el contexto de seguridad de Spring para que el resto de la aplicación pueda identificar al usuario autenticado.
@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    // UserRepository se inyecta para cargar los detalles del usuario a partir del email extraído del token JWT.
    private final UserRepository userRepository;

    // jwtSecret se inyecta desde application.properties para verificar la firma del token JWT.
    @Value("${jwt.secret}")
    private String jwtSecret;

    // doFilterInternal es el método principal que se ejecuta para cada solicitud entrante.
    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException{
        final String authHeader = request.getHeader("Authorization"); // obtiene el encabezado Authorization de la solicitud


        if (authHeader == null || !authHeader.startsWith("Bearer ")){ // si el encabezado es nulo o no comienza con "Bearer ", continúa sin autenticación
            filterChain.doFilter(request,response);
            return;
        }

        // extrae el token JWT del encabezado (removiendo el prefijo "Bearer ")
        final String token = authHeader.substring(7);
        final String email;
        // intenta verificar el token JWT usando la clave secreta. Si la verificación falla, continúa sin autenticación.
        try{
            email = JWT.require(Algorithm.HMAC256(jwtSecret))
                    .build()
                    .verify(token)
                    .getSubject();
        }catch (JWTVerificationException e){
            filterChain.doFilter(request,response);
            return;
        }

        // si se obtiene un email válido del token y no hay una autenticación ya establecida en el contexto de seguridad,
        // carga los detalles del usuario desde la base de datos usando el email, crea un token de autenticación y
        // lo establece en el contexto de seguridad para que el usuario sea reconocido como autenticado en el resto de la aplicación
        if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userRepository.findByEmail(email)
                    .orElse(null);
            if(userDetails != null){
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities()
                        );
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        filterChain.doFilter(request,response); // continúa con la cadena de filtros para que la solicitud pueda ser procesada por el controlador correspondiente
    }
}
