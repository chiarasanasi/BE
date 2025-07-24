package Mindly.service;

import Mindly.dto.ClienteDto;
import Mindly.dto.PsicologoDto;
import Mindly.dto.RichiestaDto;
import Mindly.model.Cliente;
import Mindly.model.Psicologo;
import Mindly.model.Utente;
import Mindly.repository.ClienteRepository;
import Mindly.repository.PsicologoRepository;
import Mindly.repository.RichiestaAppuntamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PsicologoService {

    @Autowired
    private PsicologoRepository psicologoRepository;

    @Autowired
    RichiestaAppuntamentoRepository repository;

    @Autowired
    private ClienteRepository clienteRepository;

    public PsicologoDto getPsicologoById(int id) {
        Optional<Psicologo> psicologoOpt = psicologoRepository.findById(id);
        if (psicologoOpt.isPresent()) {
            return new PsicologoDto(psicologoOpt.get());
        } else {
            throw new RuntimeException("Psicologo non trovato con id: " + id);
        }
    }

    public List<RichiestaDto> getRichiestePsicologo(Integer psicologoId) {
        return repository.findByPsicologoId(psicologoId).stream()
                .map(RichiestaDto::new)
                .toList();
    }



    public List<ClienteDto> getClientiDelPsicologo(Utente utente) {
        Psicologo psicologo = psicologoRepository.findByUtente(utente)
                .orElseThrow(() -> new RuntimeException("Psicologo non trovato"));

        List<Cliente> clienti = clienteRepository.findByPsicologo(psicologo);

        return clienti.stream()
                .map(ClienteDto::new)
                .toList();
    }

}
