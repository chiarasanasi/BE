package Mindly.repository;

import Mindly.enumaration.StatoRichiesta;
import Mindly.model.RichiestaAppuntamento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RichiestaAppuntamentoRepository extends JpaRepository<RichiestaAppuntamento, Integer> {
    List<RichiestaAppuntamento> findByPsicologoId(Integer psicologoId);
    List<RichiestaAppuntamento> findByClienteId(Integer clienteId);
    List<RichiestaAppuntamento> findByStato(String stato);
    List<RichiestaAppuntamento> findByPsicologoIdAndStato(Integer psicologoId, StatoRichiesta stato);

}
