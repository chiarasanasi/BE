package Mindly.service;

import Mindly.model.Cliente;
import Mindly.model.Psicologo;
import Mindly.model.Risposta;
import Mindly.repository.ClienteRepository;
import Mindly.repository.RispostaRepository;
import Mindly.security.JwtTool;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepo;

    @Autowired
    private RispostaRepository rispostaRepo;

    @Autowired
    private TagService tagService;

    @Autowired
    private JwtTool jwtTool;

    @Transactional
    public void completaQuestionario(Cliente cliente, List<Risposta> risposte) {
        for (Risposta r : risposte) {
            r.setCliente(cliente);
            rispostaRepo.save(r);
        }

        Psicologo assegnato = tagService.assegnaPsicologo(cliente, risposte);
        cliente.setPsicologo(assegnato);
        clienteRepo.save(cliente);
    }

    public Integer getLoggedClienteId(HttpServletRequest request) {
        String token = request.getHeader("Authorization").replace("Bearer ", "");
        String username = jwtTool.extractUsername(token);

        Cliente cliente = clienteRepo.findByUtenteUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Cliente non trovato"));

        return cliente.getId();
    }


}
