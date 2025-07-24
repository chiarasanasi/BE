package Mindly.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UtenteDto {
    private Integer id;
    private String username;
    private String nome;
    private String cognome;
    private List<String> ruoli;
}

