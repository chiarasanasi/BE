package Mindly.dto;

import Mindly.enumaration.StatoRichiesta;
import Mindly.model.RichiestaAppuntamento;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RichiestaDto {
    private Long id;
    private String data;
    private String ora;
    private String messaggio;
    private String stato;
    private String nomeCliente;
    private String cognomeCliente;
    private String emailCliente;

    public RichiestaDto(RichiestaAppuntamento r) {
        this.id = r.getId();
        this.data = r.getData().toString();
        this.ora = r.getOra().toString();
        this.messaggio = r.getMessaggio();
        this.stato = r.getStato().name();

        if (r.getCliente() != null && r.getCliente().getUtente() != null) {
            this.nomeCliente = r.getCliente().getUtente().getNome();
            this.cognomeCliente = r.getCliente().getUtente().getCognome();
            this.emailCliente = r.getCliente().getUtente().getEmail();
        } else {
            this.nomeCliente = "Sconosciuto";
            this.cognomeCliente = "";
            this.emailCliente = "";
        }
    }
}
