package mariangelamarasciuoloyt.gmail.com.Progettos6d5.controllers;

import mariangelamarasciuoloyt.gmail.com.Progettos6d5.entities.Dispositivo;
import mariangelamarasciuoloyt.gmail.com.Progettos6d5.payloads.DispositivoDTO;
import mariangelamarasciuoloyt.gmail.com.Progettos6d5.services.DispositivoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/dispositivi")
public class DispositivoController {
    @Autowired
    private DispositivoService dispositivoService;

    @GetMapping("")
    public Page<Dispositivo> getDispositivo(@RequestParam(defaultValue = "0") int page,
                                            @RequestParam(defaultValue = "10") int size,
                                            @RequestParam(defaultValue = "id") String orderBy) {
        return dispositivoService.getDispositivo(page, size, orderBy);
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED) // <-- 201
    public Dispositivo saveDispositivo(@RequestBody DispositivoDTO body) {
        return dispositivoService.save(body);
    }

    @GetMapping("/{id}")
    public Dispositivo findById(@PathVariable int id) {
        return dispositivoService.findById(id);
    }

    @PutMapping("/{id}")
    public Dispositivo findAndUpdateById(@PathVariable int id, @RequestBody Dispositivo body) {
        return dispositivoService.findAndUpdateById(id, body);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT) // <-- 204 NO CONTENT
    public void findAndDeleteById(@PathVariable int id) {
        dispositivoService.findAndDeleteById(id);
    }

    @PutMapping("/assign/device={deviceId}&user={userId}")
    public void assignDevice(@PathVariable int dispositivoId, @PathVariable int utenteId) throws ChangeSetPersister.NotFoundException {
        dispositivoService.AssignDipositivoToUtente(dispositivoId, utenteId);
    }
}
