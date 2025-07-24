package Mindly.controller;


import Mindly.dto.LoginDto;
import Mindly.dto.RegistrazioneDto;
import Mindly.dto.UtenteDto;
import Mindly.exception.AlreadyExistException;
import Mindly.exception.NotFoundException;
import Mindly.exception.ValidationException;
import Mindly.model.Utente;
import Mindly.security.JwtTool;
import Mindly.service.AuthService;
import Mindly.service.UtenteService;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AuthController {
    @Autowired
    private UtenteService utenteService;
    @Autowired
    private AuthService authService;
    @Autowired
    private JwtTool jwtTool;

    @PostMapping("/auth/registrazione")
    public ResponseEntity<?> registrazione(@RequestBody @Validated RegistrazioneDto registrazioneDto, BindingResult bindingResult) throws ValidationException, AlreadyExistException, JsonProcessingException {
        System.out.println("DTO in arrivo: " + registrazioneDto);
        if(bindingResult.hasErrors()){
            throw new ValidationException(bindingResult.getAllErrors().stream().map(objectError -> objectError.getDefaultMessage()).reduce("",(s, e)->s+e));
        }
        return  utenteService.registrazione(registrazioneDto);
    }

    @PostMapping("/auth/login")
    public String login(@RequestBody @Validated LoginDto loginDto, BindingResult bindingResult) throws ValidationException, NotFoundException {

        if(bindingResult.hasErrors()){
            throw new ValidationException(bindingResult.getAllErrors().stream().map(objectError -> objectError.getDefaultMessage()).reduce("",(s,e)->s+e));
        }
        return authService.login(loginDto);
    }

    @GetMapping("/me")
    public ResponseEntity<UtenteDto> getMe(HttpServletRequest request) {
        String token = request.getHeader("Authorization").replace("Bearer ", "");
        String username = jwtTool.extractUsername(token);
        Utente utente = utenteService.findByUsername(username);

        List<String> ruoli = utente.getRuoli().stream()
                .map(Enum::name)
                .toList();

        UtenteDto dto = new UtenteDto(
                utente.getId(),
                utente.getUsername(),
                utente.getNome(),
                utente.getCognome(),
                ruoli
        );

        return ResponseEntity.ok(dto);
    }

}