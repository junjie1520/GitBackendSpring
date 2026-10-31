package git.backend.catan.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import git.backend.catan.service.JugadorService;


@RestController
@RequestMapping(path="/jugador")
public class JugadorController {

    @Autowired
    private JugadorService service;

    // POST /api/jugador/{idJugador}/construir?tipus=CARRETERA&posicio=3
    @PostMapping(path="/{idJugador}/construir")
    public ResponseEntity<String> construir(
            @PathVariable int idJugador,
            @RequestParam String tipus,
            @RequestParam int posicio) {
        
        // Delegamos al servicio y retornamos su respuesta directamente
        return service.construir(idJugador, tipus, posicio);
    }

    // POST /api/jugador/{idJugador}/passar
    @PostMapping(path="/{idJugador}/passar")
    public ResponseEntity<String> passarTorn(@PathVariable int idJugador) {
        return service.passarTorn(idJugador);
    }
}