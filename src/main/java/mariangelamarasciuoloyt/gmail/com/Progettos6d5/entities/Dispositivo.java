package mariangelamarasciuoloyt.gmail.com.Progettos6d5.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@Table(name = "dispositivi")
public class Dispositivo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String stato;
    private String tipo;

    @ManyToOne
    @JoinColumn(name = "utente_id")
    private Utente utente;
}
