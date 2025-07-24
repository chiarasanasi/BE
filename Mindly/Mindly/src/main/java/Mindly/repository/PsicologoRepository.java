package Mindly.repository;

import Mindly.model.Cliente;
import Mindly.model.Psicologo;
import Mindly.model.Utente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PsicologoRepository extends JpaRepository<Psicologo, Integer> {
    Optional<Psicologo> findByUtente(Utente utente);

    Optional<Psicologo> findByUtenteUsername(String username);

}
