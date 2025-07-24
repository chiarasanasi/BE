package Mindly.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cliente {
    @Id
    @GeneratedValue
    private int id;

    @OneToOne
    @MapsId
    @JsonIgnore
    private Utente utente;

    @Column(columnDefinition = "TEXT")
    private String risposteQuestionario;

    @ManyToOne
    @JoinColumn(name = "psicologo_id")
    private Psicologo psicologo;

    @OneToMany(mappedBy = "cliente")
    @JsonIgnore
    private List<Risposta> risposte;


}

