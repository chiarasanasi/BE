package Mindly.service;

import Mindly.dto.RegistrazioneDto;
import Mindly.enumaration.Ruolo;
import Mindly.exception.AlreadyExistException;
import Mindly.exception.NotFoundException;
import Mindly.model.Cliente;
import Mindly.model.Psicologo;
import Mindly.model.Risposta;
import Mindly.model.Utente;
import Mindly.repository.ClienteRepository;
import Mindly.repository.PsicologoRepository;
import Mindly.repository.UtenteRepository;
import Mindly.security.JwtTool;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
@Data
public class UtenteService {

    private final UtenteRepository utenteRepository;
    private final ClienteRepository clienteRepository;
    private final PsicologoRepository psicologoRepository;
    private final TagService tagService;
    private final ObjectMapper objectMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtTool jwtTool;

    public Utente getUtenteByUsername(String username) throws NotFoundException {
        return utenteRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException("Utente non trovato."));
    }

    public Utente getUtenteByEmail(String email) throws NotFoundException {
        return utenteRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("Utente non trovato con email: " + email));
    }

    public Utente getUtenteById(int id) throws NotFoundException {
        return utenteRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Utente con il seguenti id " + id + " non trovato."));
    }

    public ResponseEntity<?> registrazione(RegistrazioneDto registrazioneDto) throws AlreadyExistException, JsonProcessingException {
        if (utenteRepository.existsByEmail(registrazioneDto.getEmail())) {
            throw new AlreadyExistException("Email già registrata.");
        }
        if (utenteRepository.existsByUsername(registrazioneDto.getUsername())) {
            throw new AlreadyExistException("Username già in uso.");
        }
        if (registrazioneDto.getRuolo() == Ruolo.CLIENTE && registrazioneDto.getRisposteQuestionario() == null) {
            throw new IllegalArgumentException("Il cliente deve aver completato il questionario.");
        }
        if (registrazioneDto.getRuolo() == Ruolo.PSICOLOGO &&
                (registrazioneDto.getSpecializzazione() == null || registrazioneDto.getSpecializzazione().isBlank())) {
            throw new IllegalArgumentException("Lo psicologo deve specificare una specializzazione.");
        }

        // Creazione utente
        Utente utente = new Utente();
        utente.setEmail(registrazioneDto.getEmail());
        utente.setUsername(registrazioneDto.getUsername());
        utente.setPassword(passwordEncoder.encode(registrazioneDto.getPassword()));
        utente.setNome(registrazioneDto.getNome());
        utente.setCognome(registrazioneDto.getCognome());
        utente.setRuoli(Set.of(registrazioneDto.getRuolo()));
        utente.setImmagineProfilo("https://api.dicebear.com/7.x/thumbs/svg?seed=" + utente.getUsername());
        utenteRepository.save(utente);

        if (registrazioneDto.getRuolo() == Ruolo.CLIENTE) {
            Cliente cliente = new Cliente();
            cliente.setUtente(utente);
            cliente.setRisposteQuestionario(objectMapper.writeValueAsString(registrazioneDto.getRisposteQuestionario()));

            List<Risposta> risposte = convertiRisposte(registrazioneDto.getRisposteQuestionario());
            Psicologo psicologoAssegnato = tagService.assegnaPsicologo(cliente, risposte);
            cliente.setPsicologo(psicologoAssegnato);

            clienteRepository.save(cliente);
        } else if (registrazioneDto.getRuolo() == Ruolo.PSICOLOGO) {
            Psicologo psicologo = new Psicologo();
            psicologo.setUtente(utente);
            psicologo.setSpecializzazione(registrazioneDto.getSpecializzazione());
            psicologo.setDescrizione(registrazioneDto.getDescrizione());
            psicologoRepository.save(psicologo);
        }

        Map<String, String> response = Map.of("message", "Registrazione dell'utente avvenuta con successo!", "username", utente.getUsername());
        return ResponseEntity.ok(response);
    }

    private List<Risposta> convertiRisposte(Map<String, List<String>> risposteMap) {
        List<Risposta> lista = new ArrayList<>();
        risposteMap.forEach((domanda, risposte) -> {
            for (String risposta : risposte) {
                lista.add(new Risposta(domanda, risposta));
            }
        });
        return lista;
    }

    public Utente findByUsername(String username) {
        return utenteRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Utente non trovato"));
    }
}
