package Mindly.dto;

import Mindly.enumaration.Ruolo;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegistrazioneDto {

    @NotBlank(message = "Il nome è obbligatorio")
    private String nome;

    @NotBlank(message = "Il cognome è obbligatorio")
    private String cognome;

    @Email(message = "Email non valida")
    @NotBlank(message = "L'email è obbligatoria")
    private String email;

    @NotBlank(message = "Lo username è obbligatorio")
    private String username;

    @NotBlank(message = "La password è obbligatoria")
    private String password;

    @NotNull(message = "Il ruolo è obbligatorio")
    private Ruolo ruolo;

    private String specializzazione;

    private Map<String, List<String>> risposteQuestionario;

    private String descrizione;
}
