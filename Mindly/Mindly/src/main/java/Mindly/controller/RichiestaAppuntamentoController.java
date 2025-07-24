package Mindly.controller;

import Mindly.dto.EventoDto;
import Mindly.dto.RichiestaDto;
import Mindly.enumaration.StatoRichiesta;
import Mindly.model.Psicologo;
import Mindly.model.RichiestaAppuntamento;
import Mindly.repository.PsicologoRepository;
import Mindly.security.JwtTool;
import Mindly.service.ClienteService;
import Mindly.service.RichiestaAppuntamentoService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/richieste-appuntamento")
public class RichiestaAppuntamentoController {

    @Autowired
    private RichiestaAppuntamentoService service;

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private JwtTool jwtTool;
    @Autowired
    private PsicologoRepository psicologoRepository;

    @PostMapping
    public ResponseEntity<RichiestaAppuntamento> creaRichiesta(@RequestBody RichiestaDto dto, HttpServletRequest request) {
        Integer clienteId = clienteService.getLoggedClienteId(request);
        RichiestaAppuntamento nuova = service.creaRichiesta(dto, clienteId );
        return ResponseEntity.ok(nuova);
    }

    @GetMapping("/cliente")
    public List<RichiestaAppuntamento> getPerCliente(HttpServletRequest request) {
        Integer clienteId = clienteService.getLoggedClienteId(request);
        return service.getRichiesteCliente(clienteId);
    }

    @GetMapping("/psicologo")
    @PreAuthorize("hasRole('PSICOLOGO')")
    public List<RichiestaDto> getPerPsicologo(HttpServletRequest request) {
        String token = request.getHeader("Authorization").replace("Bearer ", "");
        String username = jwtTool.extractUsername(token);

        Psicologo psicologo = psicologoRepository.findByUtenteUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Psicologo non trovato"));

        return service.getRichiestePsicologo(psicologo.getId());
    }




    @PutMapping("/{id}/stato")
    public ResponseEntity<RichiestaAppuntamento> aggiornaStato(
            @PathVariable Integer id,
            @RequestParam StatoRichiesta stato
    ) {
        return ResponseEntity.ok(service.aggiornaStato(id, stato));
    }


    @GetMapping("/eventi")
    public List<EventoDto> getEventiAccettati(HttpServletRequest request) {
        Integer clienteId = clienteService.getLoggedClienteId(request);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");


        return service.getAppuntamentiAccettati(clienteId).stream()
                .map(r -> {
                    String timestamp = r.getData().atTime(r.getOra()).format(formatter);
                    return new EventoDto(
                            String.valueOf(r.getId()),
                            "Appuntamento con " + r.getPsicologo().getUtente().getNome() + " " + r.getPsicologo().getUtente().getCognome(),
                            timestamp,
                            timestamp,
                            r.getMessaggio()
                    );
                })
                .toList();
    }

    @PreAuthorize("hasRole('PSICOLOGO')")
    @GetMapping("/psicologo/eventi")
    public List<EventoDto> getEventiAccettatiPsicologo(HttpServletRequest request) {
        String token = request.getHeader("Authorization").replace("Bearer ", "");
        String username = jwtTool.extractUsername(token);

        Psicologo psicologo = psicologoRepository.findByUtenteUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Psicologo non trovato"));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");

        return service.getAppuntamentiAccettatiPsicologo(psicologo.getId()).stream()
                .map(r -> {
                    String timestamp = r.getData().atTime(r.getOra()).format(formatter);
                    return new EventoDto(
                            String.valueOf(r.getId()),
                            "Appuntamento con " + r.getCliente().getUtente().getNome() + " " + r.getCliente().getUtente().getCognome(),
                            timestamp,
                            timestamp,
                            r.getMessaggio()
                    );
                })
                .toList();
    }




}

