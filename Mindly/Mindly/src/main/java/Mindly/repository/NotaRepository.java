package Mindly.repository;

import Mindly.model.Nota;
import Mindly.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotaRepository extends JpaRepository<Nota, Integer> {
    List<Nota> findByClienteOrderByDataCreazioneDesc(Cliente cliente);
}
