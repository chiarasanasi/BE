package Mindly.service;

import Mindly.dto.RichiestaDto;
import Mindly.enumaration.StatoRichiesta;
import Mindly.model.Cliente;
import Mindly.model.Psicologo;
import Mindly.model.RichiestaAppuntamento;
import Mindly.repository.ClienteRepository;
import Mindly.repository.PsicologoRepository;
import Mindly.repository.RichiestaAppuntamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
public class RichiestaAppuntamentoService {

    @Autowired
    private RichiestaAppuntamentoRepository repository;

    @Autowired
    private ClienteRepository clienteRepo;

    @Autowired
    private PsicologoRepository psicologoRepo;

    public RichiestaAppuntamento creaRichiesta(RichiestaDto dto, Integer clienteId) {
        Cliente cliente = clienteRepo.findById(clienteId).orElseThrow();
        Psicologo psicologo = cliente.getPsicologo();

        RichiestaAppuntamento richiesta = new RichiestaAppuntamento();
        richiesta.setCliente(cliente);
        richiesta.setPsicologo(psicologo);
        richiesta.setData(LocalDate.parse(dto.getData()));
        richiesta.setOra(LocalTime.parse(dto.getOra()));
        richiesta.setMessaggio(dto.getMessaggio());
        richiesta.setStato(StatoRichiesta.IN_ATTESA);

        RichiestaAppuntamento salvata = repository.save(richiesta);


        System.out.println("Richiesta appuntamento inviata da " + cliente.getUtente().getUsername() + " per il " + dto.getData() + " alle " + dto.getOra());

        return salvata;
    }


    public List<RichiestaAppuntamento> getRichiesteCliente(Integer clienteId) {
        return repository.findByClienteId(clienteId);
    }

    public List<RichiestaDto> getRichiestePsicologo(Integer psicologoId) {
        return repository.findByPsicologoId(psicologoId).stream()
                .map(RichiestaDto::new)
                .toList();
    }

    public RichiestaAppuntamento aggiornaStato(Integer id, StatoRichiesta nuovoStato) {
        RichiestaAppuntamento richiesta = repository.findById(id).orElseThrow();
        richiesta.setStato(nuovoStato);
        return repository.save(richiesta);
    }


    public List<RichiestaAppuntamento> getAppuntamentiAccettati(Integer clienteId) {
        return repository.findByClienteId(clienteId).stream()
                .filter(r -> r.getStato() == StatoRichiesta.ACCETTATA)
                .toList();
    }

    public List<RichiestaAppuntamento> getAppuntamentiAccettatiPsicologo(int psicologoId) {
        return repository.findByPsicologoIdAndStato(psicologoId, StatoRichiesta.ACCETTATA);
    }
}

