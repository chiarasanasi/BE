package Mindly.service;

import Mindly.dto.LoginDto;
import Mindly.exception.NotFoundException;
import Mindly.model.Utente;
import Mindly.repository.UtenteRepository;
import Mindly.security.JwtTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {


    @Autowired
    private UtenteRepository utenteRepository;
    @Autowired
    private JwtTool jwtTool;
    @Autowired
    private PasswordEncoder passwordEncoder;


    public  String login(LoginDto loginDto) throws NotFoundException {
        Utente utente = utenteRepository.findByUsernameAndEmail((loginDto.getUsername()) , loginDto.getEmail()).orElseThrow(()->new NotFoundException("L'utente con questo username " + loginDto.getUsername() + " e questa email " + loginDto.getEmail() +  " non è stato trovato."));

        if((passwordEncoder.matches(loginDto.getPassword(), utente.getPassword()))){
            return jwtTool.createToken(utente);
        }else {
            throw  new NotFoundException("L'utente con questo username/password non è stato trovato");
        }
    }

}
