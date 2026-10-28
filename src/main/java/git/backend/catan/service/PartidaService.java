package git.backend.catan.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import git.backend.catan.dto.HexagonResposta;
import git.backend.catan.dto.PartidaPeticio;
import git.backend.catan.dto.PartidaResum;
import git.backend.catan.model.Aresta;
import git.backend.catan.model.Assentament;
import git.backend.catan.model.CartaDesenvolupament;
import git.backend.catan.model.ColorJugador;
import git.backend.catan.model.EstatPartida;
import git.backend.catan.model.Hexagon;
import git.backend.catan.model.HexagonVertex;
import git.backend.catan.model.Jugador;
import git.backend.catan.model.Partida;
import git.backend.catan.model.RecursJugador;
import git.backend.catan.model.TipusCarta;
import git.backend.catan.model.TipusRecurs;
import git.backend.catan.model.TipusTerreny;
import git.backend.catan.model.Vertex;
import git.backend.catan.repository.ArestaRepository;
import git.backend.catan.repository.AssentamentRepository;
import git.backend.catan.repository.HexagonRepository;
import git.backend.catan.repository.PartidaRepository;
import git.backend.catan.repository.VertexRepository;

@Service
public class PartidaService {
	@Autowired
	private PartidaRepository pRepository;

	@Autowired
	private HexagonRepository hRepository;

	@Autowired
	private AssentamentRepository aRepository;
	@Autowired
	private VertexRepository vRepository;
	@Autowired
	private ArestaRepository arRepository;
	private static final Random random = new Random();
	
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
	public ResponseEntity<?> inicialitzarPartida(PartidaPeticio peticio) throws IOException{
		Partida partida = crearPartida(peticio.puntsPerGuanyar());	
		crearJugadors(partida, peticio.nomJugadors());
		pRepository.save(partida);
		Map<Integer, Vertex> vertexs = carregarVertexs(partida);
		carregarArestes(partida, vertexs);
		Map<Integer, Hexagon> hexagons = carregarHexagons(vertexs);
		Hexagon desert = assignarTerrenysINumeros(partida, hexagons);
		partida.setLladre(desert);
		crearPila(partida);
		return ResponseEntity.ok()
				.header("Header-Customitzat", "Partida creada amb ID: " + partida.getId())
				.body(PartidaResum.de(partida));
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
	
	private void crearPila(Partida partida) {
		for (int i = 0;i!=14;i++) {
			CartaDesenvolupament carta = new CartaDesenvolupament();
			carta.setTipusCarta(TipusCarta.CAVALLER);
			partida.afegirPila(carta);
		}
		for (int i = 0;i!=5;i++) {
			CartaDesenvolupament carta = new CartaDesenvolupament();
			carta.setTipusCarta(TipusCarta.PUNT_VICTORIA);
			partida.afegirPila(carta);
		}
		for (int i = 0;i!=2;i++) {
			CartaDesenvolupament carta = new CartaDesenvolupament();
			carta.setTipusCarta(TipusCarta.MONOPOLI);
			partida.afegirPila(carta);
		}
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
	
	private HashMap<Integer, Vertex> carregarVertexs(Partida partida) throws IOException {
		HashMap<Integer, Vertex> vertexs = new HashMap<>();
		for (String[] f : llegirCsv("vertexs.csv")) {
			Vertex v = new Vertex(Integer.parseInt(f[0]));
			partida.afegirVertex(v);
			vRepository.save(v);
			vertexs.put(v.getCodi(), v);
		}
		return vertexs;
	}
	
	private void carregarArestes(Partida partida, Map<Integer, Vertex> vertexs) throws IOException{
		for (String[] f : llegirCsv("arestes.csv")) {
			Aresta a = new Aresta(Integer.parseInt(f[0]));
			a.setVertexA(vertexs.get(Integer.parseInt(f[1])));
			a.setVertexB(vertexs.get(Integer.parseInt(f[2])));
			partida.afegirAresta(a);
			arRepository.save(a);
		}
	}
	
	private Map<Integer, Hexagon> carregarHexagons(Map<Integer, Vertex> vertexs) throws IOException{
		Map<Integer, Hexagon> hexagons = new LinkedHashMap<>();
		for (String[] f : llegirCsv("hexagons.csv")) {
			Hexagon h = new Hexagon();
			h.setCodi(Integer.parseInt(f[0]));
			h.setQ(Integer.parseInt(f[1]));
			h.setR(Integer.parseInt(f[2]));
			hexagons.put(h.getCodi(), h);
		}
		for (String[] f : llegirCsv("hexagon_vertex.csv")) {
			Hexagon h = hexagons.get(Integer.parseInt(f[0]));
			Vertex v = vertexs.get(Integer.parseInt(f[1]));
			HexagonVertex hv = new HexagonVertex(Integer.parseInt(f[2]), v, h);
			h.getHexagonVertices().add(hv);
			v.getHexagonVertices().add(hv);
		}
		return hexagons;
	}
	
	private Hexagon assignarTerrenysINumeros(Partida partida, Map<Integer, Hexagon> hexagons){
		List<TipusTerreny> terrenys = new ArrayList<>();
		afegirN(terrenys, TipusTerreny.BOSC, 4);
		afegirN(terrenys, TipusTerreny.PASTURES, 4);
		afegirN(terrenys, TipusTerreny.CAMPS, 4);
		afegirN(terrenys, TipusTerreny.TURONS, 3);
		afegirN(terrenys, TipusTerreny.MUNTANYES, 3);
		afegirN(terrenys, TipusTerreny.DESERT, 1);
		Collections.shuffle(terrenys, random);

		List<Integer> numeros = new ArrayList<>(List.of(2, 3, 3, 4, 4, 5, 5, 6, 6, 8, 8, 9, 9, 10, 10, 11, 11, 12));
		Collections.shuffle(numeros, random);

		if (terrenys.size() != hexagons.size()) {
			throw new IllegalStateException(
					"El CSV té " + hexagons.size() + " hexàgons i n'hi ha d'haver " + terrenys.size());
		}

		Hexagon desert = null;
		int iTerreny = 0;
		int iNumero = 0;
		for (Hexagon h : hexagons.values()) {
			TipusTerreny t = terrenys.get(iTerreny++);
			h.setTerreny(t);
			if (t == TipusTerreny.DESERT) {
				h.setNumero(null);
				desert = h;
			} else {
				h.setNumero(numeros.get(iNumero++));
			}
			if (h.getTerreny() == TipusTerreny.DESERT) {
				if (h.getNumero() != null) {
					throw new IllegalStateException("El desert ha de tenir el número null.");
				}
			} else {
				if (h.getNumero() == null) {
					throw new IllegalStateException("Aquest terreny requereix un número.");
				}
				if (h.getNumero() == 7) {
					throw new IllegalStateException("El número de l'hexàgon mai pot ser 7.");
				}
			}
			partida.afegirHexagon(h);
			hRepository.save(h);
		}
		return desert;
	}
	
	private static <T> void afegirN(List<T> llista, T valor, int n) {
		for (int i = 0; i < n; i++) {
			llista.add(valor);
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
