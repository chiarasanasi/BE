package Mindly.security;

import Mindly.exception.NotFoundException;
import Mindly.model.Utente;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private final JwtTool jwtTool;

    public JwtFilter(JwtTool jwtTool) {
        this.jwtTool = jwtTool;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = authHeader.replace("Bearer ", "");
        if (!jwtTool.isTokenValid(token)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            Map<String, String> error = new HashMap<>();
            error.put("error", "Token non valido o scaduto");
            new ObjectMapper().writeValue(response.getWriter(), error);
            return;
        }

        Claims claims = jwtTool.getClaims(token);
        String username = claims.getSubject();
        List<String> ruoli = claims.get("ruolo", List.class);

        List<GrantedAuthority> authorities = ruoli.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                .collect(Collectors.toList());

        Utente utente;
        try {
            utente = jwtTool.getUserFromToken(token);
            System.out.println("Utente autenticato con ruoli: " + utente.getRuoli());
        } catch (NotFoundException e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            Map<String, String> error = new HashMap<>();
            error.put("error", "Utente non trovato");
            new ObjectMapper().writeValue(response.getWriter(), error);
            return;
        }

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                utente,
                null,
                utente.getAuthorities()
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        filterChain.doFilter(request, response);
    }
}
