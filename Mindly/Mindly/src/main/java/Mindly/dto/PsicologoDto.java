package Mindly.dto;

import Mindly.model.Psicologo;
import lombok.Data;

import java.util.List;

@Data
public class PsicologoDto {
    private String nome;
    private String cognome;
    private String email;
    private String immagineProfilo;
    private String genere;
    private int eta;
    private String specializzazione;
    private List<String> tagList;
    private String descrizione;

    public PsicologoDto(Psicologo p) {
        this.nome = p.getUtente().getNome();
        this.cognome = p.getUtente().getCognome();
        this.email = p.getUtente().getEmail();
        this.immagineProfilo = p.getUtente().getImmagineProfilo();
        this.genere = p.getGenere();
        this.eta = p.getEta();
        this.specializzazione = p.getSpecializzazione();
        this.tagList = p.getTagList().stream().map(tag -> tag.getNome()).toList();
        this.descrizione = p.getDescrizione();
    }
}
