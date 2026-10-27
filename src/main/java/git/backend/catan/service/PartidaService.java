package git.backend.catan.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import git.backend.catan.dto.HexagonResposta;
import git.backend.catan.model.Assentament;
import git.backend.catan.model.Hexagon;
import git.backend.catan.model.Partida;
import git.backend.catan.repository.AssentamentRepository;
import git.backend.catan.repository.HexagonRepository;
import git.backend.catan.repository.PartidaRepository;

@Service
public class PartidaService {
	@Autowired
	private PartidaRepository pRepository;

	@Autowired
	private HexagonRepository hRepository;

	@Autowired
	private AssentamentRepository aRepository;
	
	@Transactional(readOnly = true)
	public ResponseEntity<?> tauler(int idPartida) {
		Optional<Partida> p = pRepository.findById(idPartida);
		if (!p.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.header("Header-Customitzat", "ID " + idPartida + " NO TROBAT")
					.body("NO EXISTEIX CAP PARTIDA AMB ID " + idPartida);
		}

		Integer idLladre = null;
		if (p.get().getLladre() != null) {
			idLladre = p.get().getLladre().getId();
		}

		HashMap<Integer, Assentament> assentaments = new HashMap<>();
		for (Assentament a : aRepository.llistarPerPartida(idPartida)) {
			assentaments.put(a.getVertex().getId(), a);
		}

		ArrayList<HexagonResposta> resultat = new ArrayList<>();
		for (Hexagon h : hRepository.llistarTauler(idPartida)) {
			resultat.add(HexagonResposta.de(h, idLladre, assentaments));
		}
		return ResponseEntity.ok()
				.header("Header-Customitzat", "Tauler de la partida " + idPartida)
				.body(resultat);
	}


}
