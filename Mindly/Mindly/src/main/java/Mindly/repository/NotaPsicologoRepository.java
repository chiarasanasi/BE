package Mindly.repository;

import Mindly.exception.NotFoundException;
import Mindly.model.Cliente;
import Mindly.model.NotaPsicologo;
import Mindly.model.Psicologo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface NotaPsicologoRepository extends JpaRepository<NotaPsicologo, Integer> {
    List<NotaPsicologo> findByPsicologoAndClienteOrderByDataCreazioneDesc(Psicologo psicologo, Cliente cliente);



    NotaPsicologo findById(int id) throws NotFoundException;
}
