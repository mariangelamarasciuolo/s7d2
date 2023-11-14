package mariangelamarasciuoloyt.gmail.com.Progettos6d5.services;

import mariangelamarasciuoloyt.gmail.com.Progettos6d5.entities.Role;
import mariangelamarasciuoloyt.gmail.com.Progettos6d5.entities.Utente;
import mariangelamarasciuoloyt.gmail.com.Progettos6d5.exceptions.BadRequestException;
import mariangelamarasciuoloyt.gmail.com.Progettos6d5.exceptions.UnauthorizedException;
import mariangelamarasciuoloyt.gmail.com.Progettos6d5.payloads.UtenteDTO;
import mariangelamarasciuoloyt.gmail.com.Progettos6d5.payloads.UtenteLoginDTO;
import mariangelamarasciuoloyt.gmail.com.Progettos6d5.repository.UtenteRepository;
import mariangelamarasciuoloyt.gmail.com.Progettos6d5.security.JWTTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class AuthService {
    @Autowired
    private UtenteService utenteService;

    @Autowired
    private PasswordEncoder bcrypt;

    @Autowired
    private JWTTools jwtTools;

    @Autowired
    private UtenteRepository utenteRepository;


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

    public Utente registerUtente(UtenteDTO body) throws IOException {

        // verifico se l'email è già utilizzata
        utenteRepository.findByEmail(body.email()).ifPresent(utente -> {
            throw new BadRequestException("L'email " + utente.getEmail() + " è già utilizzata!");
        });

        Utente newUtente = new Utente();
        newUtente.setAvatar("http://ui-avatars.com/api/?name=" + body.name() + "+" + body.surname());
        newUtente.setName(body.name());
        newUtente.setSurname(body.surname());
        newUtente.setPassword(bcrypt.encode(body.password())); // $2a$11$wQyZ17wrGu8AZeb2GCTcR.QOotbcVd9JwQnnCeqONWWP3wRi60tAO
        newUtente.setEmail(body.email());
        newUtente.setRole(Role.UTENTE);
        Utente savedUtente = utenteRepository.save(newUtente);
        return savedUtente;
    }

}
