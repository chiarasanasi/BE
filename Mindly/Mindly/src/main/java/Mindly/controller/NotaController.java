package Mindly.controller;

import Mindly.dto.NotaDto;
import Mindly.model.Cliente;
import Mindly.model.Nota;
import Mindly.model.Utente;
import Mindly.repository.ClienteRepository;
import Mindly.service.NotaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/note")
@PreAuthorize("hasRole('CLIENTE')")
public class NotaController {

    @Autowired
    private NotaService notaService;

    @Autowired
    private ClienteRepository clienteRepo;

    @PostMapping
    public ResponseEntity<Nota> creaNota(@RequestBody NotaDto dto, @AuthenticationPrincipal Utente utente) {
        Cliente cliente = clienteRepo.findByUtente(utente).orElseThrow();
        Nota nota = notaService.creaNota(cliente, dto.getContenuto());
        return ResponseEntity.ok(nota);
    }




    @GetMapping
    public ResponseEntity<List<Nota>> getNote(@AuthenticationPrincipal Utente utente) {
        Cliente cliente = clienteRepo.findByUtente(utente)
                .orElseThrow(() -> new RuntimeException("Cliente non trovato"));
        List<Nota> note = notaService.getNoteCliente(cliente);
        return ResponseEntity.ok(note);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Nota> aggiornaNota(@PathVariable int id, @RequestBody NotaDto dto,
                                             @AuthenticationPrincipal Utente utente) {
        Cliente cliente = clienteRepo.findByUtente(utente).orElseThrow();
        Nota notaAggiornata = notaService.aggiornaNota(id, cliente, dto.getContenuto());
        return ResponseEntity.ok(notaAggiornata);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminaNota(
            @PathVariable int id,
            @AuthenticationPrincipal Utente utente) {

        Cliente cliente = clienteRepo.findByUtente(utente)
                .orElseThrow(() -> new RuntimeException("Cliente non trovato"));

        notaService.eliminaNota(id, cliente);
        return ResponseEntity.noContent().build();
    }

}
