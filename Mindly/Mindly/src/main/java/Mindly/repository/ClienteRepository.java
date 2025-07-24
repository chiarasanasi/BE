package Mindly.repository;

import Mindly.model.Cliente;
import Mindly.model.Psicologo;
import Mindly.model.Utente;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
    Optional<Cliente> findByUtente(Utente utente);

    Optional<Cliente> findByUtenteUsername(String username);

    List<Cliente> findByPsicologo(Psicologo psicologo);

    Optional<Cliente> findByUtente_Username(String username);


}
