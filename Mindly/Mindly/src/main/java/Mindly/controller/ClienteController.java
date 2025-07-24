package Mindly.controller;

import Mindly.dto.ClienteDto;
import Mindly.dto.PsicologoDto;
import Mindly.model.Cliente;
import Mindly.model.Psicologo;
import Mindly.model.Utente;
import Mindly.repository.ClienteRepository;
import Mindly.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class ClienteController {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired private ClienteService clienteService;

    @PreAuthorize("hasRole('CLIENTE')")
    @GetMapping("/cliente/psicologo")
    public ResponseEntity<PsicologoDto> getPsicologo(@AuthenticationPrincipal Utente utente) {
        Cliente cliente = clienteRepository.findByUtente(utente)
                .orElseThrow(() -> new RuntimeException("Cliente non trovato"));
        Psicologo psicologo = cliente.getPsicologo();
        if (psicologo == null) return ResponseEntity.notFound().build();

        return ResponseEntity.ok(new PsicologoDto(psicologo));
    }

    @PreAuthorize("hasRole('CLIENTE')")
    @GetMapping("/cliente/me")
    public ResponseEntity<ClienteDto> getClienteDetails(@AuthenticationPrincipal Utente utente) {
        Cliente cliente = clienteRepository.findByUtente(utente)
                .orElseThrow(() -> new RuntimeException("Cliente non trovato"));
        return ResponseEntity.ok(new ClienteDto(cliente));
    }

}