package git.backend.catan.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import git.backend.catan.dto.HexagonResposta;
import git.backend.catan.dto.PartidaPeticio;
import git.backend.catan.model.Assentament;
import git.backend.catan.model.ColorJugador;
import git.backend.catan.model.EstatPartida;
import git.backend.catan.model.Hexagon;
import git.backend.catan.model.Jugador;
import git.backend.catan.model.Partida;
import git.backend.catan.model.RecursJugador;
import git.backend.catan.model.TipusRecurs;
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

	@Transactional(rollbackFor = Exception.class)
	public ResponseEntity<?> inicialitzarPartida(PartidaPeticio peticio){
		Partida p = crearPartida(peticio.puntsPerrGuanyar());	
		crearJugadors(p, peticio.nomJugadors());
		pRepository.save(p);
	}
	
	private Partida crearPartida(Integer punts) {
		Partida partida = new Partida();
		partida.setEstatPartida(EstatPartida.PREPARACIO);
		partida.setDataInici(LocalDate.now());
		partida.setRonda(1);
		partida.setUltimaTirada(0);
		if (punts != null) {
			partida.setPuntsPerGuanyar(punts);
		}else {
			partida.setPuntsPerGuanyar(10);
		}
		partida.setDausTirats(false);
		partida.setLladrePendent(false);
		return partida;
	}
	
	private void crearJugadors(Partida partida, List<String> nomsJugadors) {
		List<String> noms = new ArrayList<>(nomsJugadors);
		Collections.shuffle(noms);
		for (int i = 0; i < noms.size(); i++) {
			Jugador j = new Jugador();
			j.setNom(noms.get(i));
			j.setOrdreTorn(i + 1);
			j.setColor(ColorJugador.values()[i]);
			j.setCavallersJugats(0);
			for (TipusRecurs tipus : TipusRecurs.values()) {
				j.getInventari().add(new RecursJugador(j, tipus, 0));
			}
			partida.afegirJugador(j);
			if (i == 0) {
				partida.setActiu(j);
			}
		}
	}
	
	private ArrayList<String[]> llegirCsv(String fitxer) throws IOException {
		ClassPathResource recurs = new ClassPathResource(fitxer);
		InputStreamReader isr = new InputStreamReader(recurs.getInputStream(), StandardCharsets.UTF_8);
		BufferedReader br = new BufferedReader(isr);

		ArrayList<String[]> files = new ArrayList<>();
		br.readLine();                          // capçalera
		while (br.ready()) {
			String linia = br.readLine();
			if (!linia.isBlank()) {
				files.add(linia.split(",", -1));
			}
		}
		br.close();

		return files;
	}

}
