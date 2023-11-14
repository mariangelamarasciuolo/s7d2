package mariangelamarasciuoloyt.gmail.com.Progettos6d5.services;

import mariangelamarasciuoloyt.gmail.com.Progettos6d5.entities.Utente;
import mariangelamarasciuoloyt.gmail.com.Progettos6d5.exceptions.UnauthorizedException;
import mariangelamarasciuoloyt.gmail.com.Progettos6d5.payloads.UtenteLoginDTO;
import mariangelamarasciuoloyt.gmail.com.Progettos6d5.security.JWTTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    private UtenteService utenteService;

    @Autowired
    private JWTTools jwtTools;

    public String authenticateUser(UtenteLoginDTO body) {
        // 1. Verifichiamo che l'email dell'utente sia nel db
        Utente utente = utenteService.findByEmail(body.email());

        // 2. In caso affermativo, verifichiamo se la password corrisponde a quella trovata nel db
        if (body.password().equals(utente.getPassword())) {
            // 3. Se le credenziali sono OK --> Genero un JWT e lo restituisco
            return jwtTools.createToken(utente);
        } else {
            // 4. Se le credenziali NON sono OK --> 401
            throw new UnauthorizedException("Credenziali non valide!");
        }


    }
}
