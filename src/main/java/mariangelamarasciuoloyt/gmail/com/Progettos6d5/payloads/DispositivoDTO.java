package mariangelamarasciuoloyt.gmail.com.Progettos6d5.payloads;

import jakarta.validation.constraints.NotEmpty;

public record DispositivoDTO(@NotEmpty(message = "Campo dello stato Obbligatorio!") String stato,
                             @NotEmpty(message = "Campo del tipo Obbligatorio!") String tipo) {
}
