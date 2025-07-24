package Mindly.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Risposta {
    @Id
    @GeneratedValue
    private int id;

    private String domanda;
    private String risposta;

    @ManyToOne
    private Cliente cliente;

    public Risposta(String domanda, String risposta, Cliente cliente) {
        this.domanda = domanda;
        this.risposta = risposta;
        this.cliente = cliente;
    }

    public Risposta(String domanda, String risposta) {
        this.domanda = domanda;
        this.risposta = risposta;
    }
}

