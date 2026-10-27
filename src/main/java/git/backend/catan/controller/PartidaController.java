package git.backend.catan.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import git.backend.catan.service.PartidaService;

@RestController
@RequestMapping(path = "/partida")
public class PartidaController {

	@Autowired
	private PartidaService service;
	@GetMapping(path = "/{idPartida}/tauler")
	public ResponseEntity<?> tauler(@PathVariable int idPartida) {
		return service.tauler(idPartida);
	}

}