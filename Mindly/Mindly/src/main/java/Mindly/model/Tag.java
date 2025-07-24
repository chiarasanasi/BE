package Mindly.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Tag {
    @Id
    @GeneratedValue
    private int id;

    private String nome;

    @ManyToMany(mappedBy = "tagList")
    @JsonBackReference
    private List<Psicologo> psicologi;
}


