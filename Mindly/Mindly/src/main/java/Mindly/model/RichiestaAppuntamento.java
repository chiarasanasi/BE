package Mindly.model;

import Mindly.enumaration.StatoRichiesta;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;


@Entity
@Data
public class RichiestaAppuntamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate data;
    private LocalTime ora;
    private String messaggio;

    @Enumerated(EnumType.STRING)
    private StatoRichiesta stato = StatoRichiesta.IN_ATTESA;


    @ManyToOne
    @JsonIgnore
    private Cliente cliente;

    @ManyToOne
    @JsonIgnore
    private Psicologo psicologo;

}

