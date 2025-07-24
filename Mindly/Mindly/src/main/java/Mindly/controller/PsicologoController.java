package Mindly.controller;

import Mindly.dto.ClienteDto;
import Mindly.dto.PsicologoDto;
import Mindly.model.Cliente;
import Mindly.model.Psicologo;
import Mindly.model.Utente;
import Mindly.repository.ClienteRepository;
import Mindly.repository.PsicologoRepository;
import Mindly.service.PsicologoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/psicologo")
public class PsicologoController {

    @Autowired
    private PsicologoService psicologoService;

    @Autowired
    private PsicologoRepository psicologoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @GetMapping("/{id}")
    public PsicologoDto getPsicologoById(@PathVariable int id) {
        return psicologoService.getPsicologoById(id);
    }

    @PreAuthorize("hasRole('PSICOLOGO')")
    @GetMapping("/me")
    public ResponseEntity<PsicologoDto> getPsicologoDetails(@AuthenticationPrincipal Utente utente) {
        Psicologo psicologo = psicologoRepository.findByUtente(utente)
                .orElseThrow(() -> new RuntimeException("Psicologo non trovato"));
        return ResponseEntity.ok(new PsicologoDto(psicologo));
    }

    @PreAuthorize("hasRole('PSICOLOGO')")
    @GetMapping("/clienti")
    public List<ClienteDto> getClienti(@AuthenticationPrincipal Utente utente) {
        return psicologoService.getClientiDelPsicologo(utente);
    }

    @PreAuthorize("hasRole('PSICOLOGO')")
    @GetMapping("/clienti/{username}")
    public ResponseEntity<ClienteDto> getDettagliCliente(@PathVariable String username) {
        Cliente cliente = clienteRepository.findByUtente_Username(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente non trovato"));

        ClienteDto dto = new ClienteDto();
        dto.setNome(cliente.getUtente().getNome());
        dto.setCognome(cliente.getUtente().getCognome());
        dto.setEmail(cliente.getUtente().getEmail());
        dto.setRisposteQuestionario(cliente.getRisposteQuestionario());

        return ResponseEntity.ok(dto);
    }


    @PreAuthorize("hasRole('PSICOLOGO')")
    @GetMapping("/clienti/{username}/dettaglio")
    public Cliente getClinte(@PathVariable String username) {
        Cliente cliente = clienteRepository.findByUtenteUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente non trovato"));


        return cliente;
    }


}
