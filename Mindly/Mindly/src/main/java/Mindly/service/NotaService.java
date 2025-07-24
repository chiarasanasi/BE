package Mindly.service;

import Mindly.model.Cliente;
import Mindly.model.Nota;
import Mindly.repository.NotaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class NotaService {

    @Autowired
    private NotaRepository notaRepo;

    @Transactional
    public Nota creaNota(Cliente cliente, String contenuto) {
        Nota nota = new Nota();
        nota.setCliente(cliente);
        nota.setContenuto(contenuto);
        nota.setDataCreazione(LocalDateTime.now());
        return notaRepo.save(nota);
    }

    public List<Nota> getNoteCliente(Cliente cliente) {
        return notaRepo.findByClienteOrderByDataCreazioneDesc(cliente);
    }

    @Transactional
    public Nota aggiornaNota(int id, Cliente cliente, String nuovoContenuto) {
        Nota nota = notaRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Nota non trovata"));

        if (!nota.getCliente().equals(cliente)) {
            throw new RuntimeException("Non puoi modificare questa nota");
        }

        nota.setContenuto(nuovoContenuto);
        return notaRepo.save(nota);
    }

    @Transactional
    public void eliminaNota(int id, Cliente cliente) {
        Nota nota = notaRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Nota non trovata"));

        if (!nota.getCliente().equals(cliente)) {
            throw new RuntimeException("Non puoi cancellare questa nota");
        }

        notaRepo.delete(nota);
    }

}
