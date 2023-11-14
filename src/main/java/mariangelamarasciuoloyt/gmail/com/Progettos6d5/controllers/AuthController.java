package mariangelamarasciuoloyt.gmail.com.Progettos6d5.controllers;

import io.jsonwebtoken.io.IOException;
import mariangelamarasciuoloyt.gmail.com.Progettos6d5.entities.Utente;
import mariangelamarasciuoloyt.gmail.com.Progettos6d5.exceptions.BadRequestException;
import mariangelamarasciuoloyt.gmail.com.Progettos6d5.payloads.UtenteDTO;
import mariangelamarasciuoloyt.gmail.com.Progettos6d5.payloads.UtenteLoginDTO;
import mariangelamarasciuoloyt.gmail.com.Progettos6d5.payloads.UtenteLoginSuccessDTO;
import mariangelamarasciuoloyt.gmail.com.Progettos6d5.services.AuthService;
import mariangelamarasciuoloyt.gmail.com.Progettos6d5.services.UtenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService authService;
    @Autowired
    private UtenteService utenteService;

    @PostMapping("/login")
    public UtenteLoginSuccessDTO login(@RequestBody UtenteLoginDTO body) {

        return new UtenteLoginSuccessDTO(authService.authenticateUser(body));
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED) // <-- 201
    public Utente saveUtente(@RequestBody @Validated UtenteDTO body, BindingResult validation) {
        if (validation.hasErrors()) {
            throw new BadRequestException(validation.getAllErrors());
        } else {
            try {
                return utenteService.save(body);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}