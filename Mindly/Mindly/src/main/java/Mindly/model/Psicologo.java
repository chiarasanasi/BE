package Mindly.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Psicologo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int eta;

    private String genere;

    private String specializzazione;

    @OneToOne
    @JoinColumn(name = "utente_id", referencedColumnName = "id")
    private Utente utente;


    @OneToMany(mappedBy = "psicologo")
    private List<Cliente> clienti;


    @ManyToMany
    @JsonManagedReference
    private List<Tag> tagList;

    @Column(length = 1000)
    private String descrizione;



}

