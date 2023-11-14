package mariangelamarasciuoloyt.gmail.com.Progettos6d5.services;

import mariangelamarasciuoloyt.gmail.com.Progettos6d5.entities.Dispositivo;
import mariangelamarasciuoloyt.gmail.com.Progettos6d5.entities.Utente;
import mariangelamarasciuoloyt.gmail.com.Progettos6d5.exceptions.BadRequestException;
import mariangelamarasciuoloyt.gmail.com.Progettos6d5.exceptions.NotFoundException;
import mariangelamarasciuoloyt.gmail.com.Progettos6d5.payloads.DispositivoDTO;
import mariangelamarasciuoloyt.gmail.com.Progettos6d5.repository.DispositivoRepository;
import mariangelamarasciuoloyt.gmail.com.Progettos6d5.repository.UtenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class DispositivoService {
    @Autowired
    private DispositivoRepository dispositivoRepository;
    @Autowired
    private UtenteRepository utenteRepository;

    public void AssignDipositivoToUtente(int utenteId, int dispositivoId) throws NotFoundException {
        Dispositivo target = dispositivoRepository.findById(dispositivoId).orElseThrow(() -> new NotFoundException(dispositivoId));
        if (target.getStato().equals("assegnato")) {
            throw new BadRequestException("il dispositivo e' gia assegnato");
        } else if (target.getStato().equals("manutenzione")) {
            throw new BadRequestException("il dispositivo e' in manutenzione");
        } else if (target.getStato().equals("dismesso")) {
            throw new BadRequestException("il dispositivo e' stato dismesso");
        } else if (target.getStato().equals("disponibile")) {
            Utente utente = utenteRepository.findById(utenteId).orElseThrow(() -> new NotFoundException(utenteId));
            target.setUtente(utente);
            target.setStato("assegnato");
            dispositivoRepository.save(target);
        }
    }

    public Dispositivo save(DispositivoDTO body) {
        Dispositivo newDispositivo = new Dispositivo();

        newDispositivo.setTipo(body.tipo());
        newDispositivo.setStato(body.stato());
        return dispositivoRepository.save(newDispositivo);
    }

    public Page<Dispositivo> getDispositivo(int page, int size, String orderBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(orderBy));
        return dispositivoRepository.findAll(pageable);
    }

    public Dispositivo findById(int id) {

        return dispositivoRepository.findById(id).orElseThrow(() -> new NotFoundException(id));

    }

    public Dispositivo findAndUpdateById(int id, Dispositivo body) {
        Dispositivo foundD = this.findById(id);

        foundD.setId(id);
        foundD.setStato(body.getStato());
        foundD.setTipo(body.getTipo());

        return foundD;

    }

    public void findAndDeleteById(int id) {
        Dispositivo foundD = this.findById(id);
        dispositivoRepository.delete(foundD);
    }

}
