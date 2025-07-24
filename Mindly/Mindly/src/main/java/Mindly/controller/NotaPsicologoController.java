package Mindly.controller;

import Mindly.dto.NotaPsicologoDto;
import Mindly.exception.NotFoundException;
import Mindly.model.Cliente;
import Mindly.model.NotaPsicologo;
import Mindly.model.Psicologo;
import Mindly.model.Utente;
import Mindly.repository.ClienteRepository;
import Mindly.repository.NotaPsicologoRepository;
import Mindly.repository.PsicologoRepository;
import Mindly.service.NotaPsicologoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/note-psicologo")
@PreAuthorize("hasRole('PSICOLOGO')")
public class NotaPsicologoController {

    @Autowired
    private NotaPsicologoRepository notaPsicologoRepository;

    @Autowired
    NotaPsicologoService notaPsicologoService;

    @Autowired
    private PsicologoRepository psicologoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @PostMapping
    public NotaPsicologo creaNotaPsicologo(@RequestBody NotaPsicologoDto dto, @AuthenticationPrincipal Utente utente) throws NotFoundException {
        Psicologo psicologo = psicologoRepository.findByUtente(utente)
                .orElseThrow(() -> new NotFoundException("Psicologo non trovato"));

        Cliente cliente = clienteRepository.findById(dto.getClienteId())
                .orElseThrow(() -> new NotFoundException("Cliente non trovato"));

        return notaPsicologoService.creaNotaPsicologo(psicologo, dto.getContenuto(), cliente);
    }

    @GetMapping("/{usernameCliente}")
    public List<NotaPsicologo> getNotePsicologoPerCliente(
            @AuthenticationPrincipal Utente utente,
            @PathVariable String usernameCliente) throws NotFoundException {

        Psicologo psicologo = psicologoRepository.findByUtente(utente)
                .orElseThrow(() -> new NotFoundException("Psicologo non trovato"));

        return notaPsicologoService.getNotePsicologoPerCliente(psicologo, usernameCliente);
    }

    @PutMapping("/{id}")
    public NotaPsicologo aggiornaNotaPsicologo(
            @PathVariable int id,
            @RequestBody NotaPsicologoDto dto) throws NotFoundException {
        return notaPsicologoService.aggiornaNotaPsicologo(id, dto.getContenuto());
    }


    @DeleteMapping("/{id}")
    public void eliminaNotaPsicologo(@PathVariable int id) throws NotFoundException {
        notaPsicologoService.eliminaNotaPsicologo(id);
    }

}
