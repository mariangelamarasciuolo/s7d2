package mariangelamarasciuoloyt.gmail.com.Progettos6d5.payloads;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

public record UtenteDTO(@NotEmpty(message = "Campo del nome Obbligatorio!") String name,
                        @NotEmpty(message = "Campo del cognome Obbligatorio!") String surname,
                        @NotEmpty(message = "Campo dell'username Obbligatorio!") String username,
                        @Pattern(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$", message = "L'email inserita non è valida") String email,

                        String avatar) {
}
