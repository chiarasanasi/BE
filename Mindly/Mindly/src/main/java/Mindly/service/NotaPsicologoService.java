package Mindly.service;

import Mindly.exception.NotFoundException;
import Mindly.model.Cliente;
import Mindly.model.NotaPsicologo;
import Mindly.model.Psicologo;
import Mindly.repository.ClienteRepository;
import Mindly.repository.NotaPsicologoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class NotaPsicologoService {

    @Autowired
    private NotaPsicologoRepository notaPsicologoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Transactional
    public NotaPsicologo creaNotaPsicologo(Psicologo psicologo, String contenuto, Cliente cliente){
        NotaPsicologo notaPsicologo = new NotaPsicologo();
        notaPsicologo.setPsicologo(psicologo);
        notaPsicologo.setContenuto(contenuto);
        notaPsicologo.setDataCreazione(LocalDateTime.now());
        notaPsicologo.setCliente(cliente);

        return notaPsicologoRepository.save(notaPsicologo);
    }

    public List<NotaPsicologo> getNotePsicologoPerCliente(Psicologo psicologo, String usernameCliente) throws NotFoundException {
        Cliente cliente = clienteRepository.findByUtenteUsername(usernameCliente)
                .orElseThrow(() -> new NotFoundException("Cliente non trovato"));
        return notaPsicologoRepository.findByPsicologoAndClienteOrderByDataCreazioneDesc(psicologo, cliente);
    }


    @Transactional
    public NotaPsicologo aggiornaNotaPsicologo(int id, String contenuto) throws NotFoundException {
        NotaPsicologo notaPsicologo = notaPsicologoRepository.findById(id);
        notaPsicologo.setContenuto(contenuto);
        return notaPsicologoRepository.save(notaPsicologo);
    }

    @Transactional
    public void eliminaNotaPsicologo(int id) throws NotFoundException {
        NotaPsicologo notaPsicologo = notaPsicologoRepository.findById(id);

        notaPsicologoRepository.delete(notaPsicologo);
    }

}
