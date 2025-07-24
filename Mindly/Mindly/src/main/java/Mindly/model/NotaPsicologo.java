package Mindly.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotaPsicologo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String contenuto;

    private LocalDateTime dataCreazione;

    @ManyToOne
    @JoinColumn(name = "psicologo_id")
    @JsonIgnore
    private Psicologo psicologo;

    @ManyToOne
    @JoinColumn(name = "clienti_id")
    @JsonIgnore
    private Cliente cliente;
}
