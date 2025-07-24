package Mindly.dto;

import Mindly.model.Cliente;
import Mindly.model.Utente;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ClienteDto {
    private String nome;
    private String cognome;
    private String email;
    private String ruolo;
    private boolean questionarioCompletato;
    private PsicologoDto psicologo;
    private String risposteQuestionario;
    private String username;


    public ClienteDto(Cliente cliente) {
        Utente utente = cliente.getUtente();
        this.nome = utente.getNome();
        this.cognome = utente.getCognome();
        this.email = utente.getEmail();
        this.username = utente.getUsername();
        this.ruolo = utente.getRuoli() != null
                ? String.join(", ",
                utente.getRuoli().stream()
                        .map(Enum::name)
                        .toList())
                : "";
        this.questionarioCompletato = cliente.getPsicologo() != null;
        this.psicologo = cliente.getPsicologo() != null ? new PsicologoDto(cliente.getPsicologo()) : null;
        this.risposteQuestionario = cliente.getRisposteQuestionario();

    }
}
