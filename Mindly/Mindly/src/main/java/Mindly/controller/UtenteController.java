package Mindly.controller;


import Mindly.dto.RispostaDto;
import Mindly.exception.NotFoundException;
import Mindly.model.Cliente;
import Mindly.model.Risposta;
import Mindly.model.Utente;
import Mindly.repository.ClienteRepository;
import Mindly.repository.RispostaRepository;
import Mindly.repository.UtenteRepository;
import Mindly.service.UtenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
public class UtenteController {
    @Autowired
    private UtenteRepository utenteRepository;

    @Autowired
    private RispostaRepository rispostaRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @PutMapping("/utenti/{id}/immagine")
    public ResponseEntity<?> aggiornaImmagine(@PathVariable int id, @RequestBody String nuovaUrl) {
        Utente u = utenteRepository.findById(id).orElseThrow();
        u.setImmagineProfilo(nuovaUrl);
        utenteRepository.save(u);
        return ResponseEntity.ok("Immagine aggiornata");
    }

    @PostMapping("/questionario")
    public ResponseEntity<Void> salvaRisposte(@RequestBody List<RispostaDto> risposte, @AuthenticationPrincipal Utente utente) throws NotFoundException {
        Cliente cliente = clienteRepository.findByUtente(utente).orElseThrow();
        List<Risposta> risposteEntity = risposte.stream().map(r -> new Risposta(r.getDomanda(), r.getRisposta(), cliente)).toList();
        rispostaRepository.saveAll(risposteEntity);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/utente/{username}")
    public Utente getUtente(@PathVariable String username) {
        return utenteRepository.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Utente non trovato"));
    }


}
