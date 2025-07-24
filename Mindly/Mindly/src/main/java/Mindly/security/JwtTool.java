package Mindly.security;

import Mindly.exception.NotFoundException;
import Mindly.model.Utente;
import Mindly.service.UtenteService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.stream.Collectors;

@Component
public class JwtTool {

    @Autowired
    @Lazy
    private UtenteService utenteService;

    @Value("${jwt.duration}")
    private Long durata;

    @Value("${jwt.secret}")
    private String chiaveSegreta;


    public String createToken(Utente utente) {
        return Jwts.builder()
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + durata))
                .subject(utente.getId()+"")
                .claim("username", utente.getUsername())
                .claim("ruolo", utente.getRuoli())
                .signWith(Keys.hmacShaKeyFor(chiaveSegreta.getBytes()))
                .compact();
    }


    public void validateToken(String token) {
        Jwts.parser()
                .verifyWith(Keys.hmacShaKeyFor(chiaveSegreta.getBytes()))
                .build()
                .parseClaimsJws(token);
    }


    public Utente getUserFromToken(String token) throws NotFoundException {
        Claims claims = Jwts.parser()
                .verifyWith(Keys.hmacShaKeyFor(chiaveSegreta.getBytes()))
                .build()
                .parseClaimsJws(token)
                .getBody();

        int id = Integer.parseInt(claims.getSubject());
        return utenteService.getUtenteById(id);
    }

    public Claims getClaims(String token) {
        return Jwts.parser()
                .verifyWith(Keys.hmacShaKeyFor(chiaveSegreta.getBytes()))
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean isTokenValid(String token) {
        try {
            getUserFromToken(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String extractUsername(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(Keys.hmacShaKeyFor(chiaveSegreta.getBytes()))
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims.get("username", String.class);
    }

}
