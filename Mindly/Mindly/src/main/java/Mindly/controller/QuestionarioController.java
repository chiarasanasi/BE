package Mindly.controller;

import Mindly.exception.NotFoundException;
import Mindly.model.Cliente;
import Mindly.model.Risposta;
import Mindly.model.Utente;
import Mindly.repository.ClienteRepository;
import Mindly.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/questionario")
public class QuestionarioController {

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private ClienteRepository clienteRepository;

    @PostMapping("/completa")
    public ResponseEntity<String> completaQuestionario(
            @RequestBody List<Risposta> risposte,
            @AuthenticationPrincipal Utente utente
    ) throws NotFoundException {

        Cliente cliente = clienteRepository.findByUtente(utente)
                .orElseThrow(() -> new NotFoundException("Cliente non trovato per l'utente autenticato."));

        clienteService.completaQuestionario(cliente, risposte);
        return ResponseEntity.ok("Psicologo assegnato con successo!");
    }
}
