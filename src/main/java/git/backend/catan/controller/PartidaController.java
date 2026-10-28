package git.backend.catan.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import git.backend.catan.dto.PartidaPeticio;
import git.backend.catan.service.PartidaService;
import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "/partida")
public class PartidaController {

	@Autowired
	private PartidaService service;
	@GetMapping(path = "/{idPartida}/tauler")
	public ResponseEntity<?> tauler(@PathVariable int idPartida) {
		return service.tauler(idPartida);
	}
	@PostMapping
	public ResponseEntity<?> inicialitzarPartida(@Valid @RequestBody PartidaPeticio peticio) throws IOException {
		return service.inicialitzarPartida(peticio);
	}

}