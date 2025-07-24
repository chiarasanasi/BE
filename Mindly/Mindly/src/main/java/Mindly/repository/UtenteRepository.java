package Mindly.repository;

import Mindly.model.Utente;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UtenteRepository extends JpaRepository<Utente, Integer> {
    Optional<Utente> findByUsername(String username);
    Optional<Utente> findByUsernameAndEmail(String username, String email);
    Optional<Utente> findByEmail(String email);
    Optional<Utente> findById(int id);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}

