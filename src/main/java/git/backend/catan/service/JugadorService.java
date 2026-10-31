package git.backend.catan.service;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import git.backend.catan.model.Aresta;
import git.backend.catan.model.Carretera;
import git.backend.catan.model.Ciutat;
import git.backend.catan.model.EstatPartida;
import git.backend.catan.model.Hexagon;
import git.backend.catan.model.Jugador;
import git.backend.catan.model.Partida;
import git.backend.catan.model.Poblat;
import git.backend.catan.model.RecursJugador;
import git.backend.catan.model.TipusRecurs;
import git.backend.catan.model.TipusTerreny;
import git.backend.catan.model.Vertex;
import git.backend.catan.repository.ArestaRepository;
import git.backend.catan.repository.AssentamentRepository;
import git.backend.catan.repository.CarreteraRepository;
import git.backend.catan.repository.CiutatRepository;
import git.backend.catan.repository.HexagonRepository;
import git.backend.catan.repository.JugadorRepository;
import git.backend.catan.repository.PartidaRepository;
import git.backend.catan.repository.PoblatRepository;
import git.backend.catan.repository.VertexRepository;

@Service
public class JugadorService {

	
	@Autowired
	private CarreteraRepository carreteraRepository;
	@Autowired
	private PoblatRepository poblatRepository;
	@Autowired
	private AssentamentRepository assentamentRepository;
    @Autowired
    private JugadorRepository jugadorRepository;
    @Autowired
    private PartidaRepository partidaRepository;
    @Autowired
    private ArestaRepository arestaRepository;
    @Autowired
    private VertexRepository vertexRepository;
    @Autowired
    private HexagonRepository hexagonRepository;
    @Autowired
    private CiutatRepository ciutatRepository;

    @Transactional
    public ResponseEntity<String> construir(int idJugador, String tipus, int posicio) {
        Optional<Jugador> jugadorOpt = jugadorRepository.findById(idJugador);
        if (jugadorOpt.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"No existeix aquest jugador");
        }
        Jugador jugador = jugadorOpt.get();
        if (tipus == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Falta el tipus de construcció");
        }
        switch (tipus.trim().toUpperCase()) {
            case "CARRETERA":
                return construirCarreteraLogica(jugador, posicio);
            case "POBLAT":
                return construirPoblatLogica(jugador, posicio);
            case "CIUTAT":
                return millorarCiutatLogica(jugador, posicio);

            default:
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Tipus de construcció invàlid");
        }
    }

    @Transactional
    public ResponseEntity<String> passarTorn(int idJugador) {
        Optional<Jugador> jugadorOpt = jugadorRepository.findById(idJugador);
        
        if (jugadorOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Jugador no trobat");
        }
        
        Jugador jugador = jugadorOpt.get();
        Partida partida = jugador.getPartida();

        if (partida.getEstatPartida() == EstatPartida.FINALITZADA) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("La partida ja està finalitzada");
        }
        
        if (!partida.getActiu().equals(jugador)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("No és el teu torn");
        }
        
        if (partida.isLladrePendent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Lladre pendent.");
        }

        if (partida.getEstatPartida() == EstatPartida.EN_CURS) {
            if (!partida.isDausTirats()) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("No has tirat els daus.");
            }
            
            int seguentOrdre = (jugador.getOrdreTorn() % partida.getJugadors().size()) + 1;
            
            Optional<Jugador> seguentOpt = jugadorRepository.findByPartidaIdAndOrdreTorn(partida.getId(), seguentOrdre);
            if (seguentOpt.isEmpty()) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: No s'ha trobat el següent jugador.");
            }
            
            Jugador seguent = seguentOpt.get();

            partida.setActiu(seguent);
            partida.setDausTirats(false);
            
            if (seguent.getOrdreTorn() == 1) {
                partida.setRonda(partida.getRonda() + 1);
            }
            
        } else if (partida.getEstatPartida() == EstatPartida.PREPARACIO) {
            long pobs = poblatRepository.countByPropietariId(idJugador);
            long cars = carreteraRepository.countByPropietariId(idJugador);
            
            if (pobs < partida.getRonda() || cars < partida.getRonda()) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("A preparació has de col·locar poblat i carretera.");
            }

            int nJ = partida.getJugadors().size();
            int oActual = jugador.getOrdreTorn();
            int segOrdre = oActual;
            boolean canviFase = false;

            if (partida.getRonda() == 1) {
                if (oActual < nJ) {
                    segOrdre = oActual + 1;
                } else {
                    partida.setRonda(2);
                }
            } else if (partida.getRonda() == 2) {
                if (oActual > 1) {
                    segOrdre = oActual - 1;
                } else {
                    canviFase = true;
                }
            }

            if (canviFase) {
                partida.setEstatPartida(EstatPartida.EN_CURS);
                partida.setRonda(1);
                segOrdre = 1;
            }

            Optional<Jugador> seguentOpt = jugadorRepository.findByPartidaIdAndOrdreTorn(partida.getId(), segOrdre);
            if (seguentOpt.isEmpty()) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: No s'ha trobat el següent jugador en preparació.");
            }
            Jugador seguent = seguentOpt.get();   
            partida.setActiu(seguent);
            partida.setDausTirats(false);
        }
        partidaRepository.save(partida);
        return ResponseEntity.ok("Torn passat correctament.");
    }

    private ResponseEntity<String> construirCarreteraLogica(Jugador jugador, int idAresta) {
        Partida partida = jugador.getPartida();
        
        if (partida == null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("El jugador no està en cap partida");
        }

        if (partida.getEstatPartida() == EstatPartida.FINALITZADA) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("La partida ja està finalitzada");
        }

        if (partida.getActiu() == null || !partida.getActiu().equals(jugador)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("No és el teu torn");
        }

        if (partida.getEstatPartida() == EstatPartida.EN_CURS) {
            if (!partida.isDausTirats()) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Has de tirar els daus primer");
            }

            if (partida.isLladrePendent()) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Has de moure el lladre");
            }
        } else if (partida.getEstatPartida() != EstatPartida.PREPARACIO) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("La partida no està en un estat vàlid");
        }
        
        Optional<Aresta> arestaOpt = arestaRepository.findById(idAresta);
        if (arestaOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No existeix aquesta aresta");
        }
        
        Aresta aresta = arestaOpt.get();
        if (aresta.getPartida() == null || !aresta.getPartida().getId().equals(partida.getId())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("L'aresta no pertany a aquesta partida");
        }
        
        if (carreteraRepository.existsByArestaId(aresta.getId())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("L'aresta ja està ocupada");
        }
        
        if (partida.getEstatPartida() == EstatPartida.PREPARACIO) {
            long numPoblats = poblatRepository.countByPropietariId(jugador.getId());
            long numCarreteres = carreteraRepository.countByPropietariId(jugador.getId());

            if (numPoblats == 0 || numCarreteres >= numPoblats) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Has de construir un poblat abans de la carretera");
            }
            
            List<Poblat> poblats = poblatRepository.findByPropietariIdOrderByIdDesc(jugador.getId());

            if (poblats.isEmpty()) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("No tens cap poblat construït");
            }		
            
            Poblat ultimPoblat = poblats.get(0);
            Integer idVertexPoblat = ultimPoblat.getVertex().getId();
            
            boolean tocaUltimPoblat = aresta.getVertexA().getId().equals(idVertexPoblat) || aresta.getVertexB().getId().equals(idVertexPoblat);
            if (!tocaUltimPoblat) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("La carretera ha de tocar el poblat acabat de construir");
            }
            
        } else {
            long numCarreteres = carreteraRepository.countByPropietariId(jugador.getId());
            if (numCarreteres >= 15) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Límit de 15 carreteres assolit");
            }
            
            List<Integer> extrems = List.of(aresta.getVertexA().getId(), aresta.getVertexB().getId());
            long tocaAssentament = assentamentRepository.countByPropietariIdAndVertexIdIn(jugador.getId(), extrems);
            
            if (tocaAssentament == 0) {
                long tocaCarretera = carreteraRepository.contarCarreterasConectadas(
                        jugador.getId(),
                        aresta.getId(),
                        aresta.getVertexA().getId(),
                        aresta.getVertexB().getId());

                if (tocaCarretera == 0) {
                    return ResponseEntity.status(HttpStatus.CONFLICT).body("La carretera ha d'estar connectada a una construcció pròpia o carretera pròpia");
                }
            }
            
            boolean recursosSuficients = gastarRecursos(jugador, TipusRecurs.FUSTA, 1, TipusRecurs.MAO, 1);
            if (!recursosSuficients) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("No tens recursos per construir la carretera");
            }
        }
        
        Carretera carretera = new Carretera();
        carretera.setJugador(jugador);
        carretera.setAresta(aresta);
        
        carreteraRepository.save(carretera);
        return ResponseEntity.ok("Carretera construïda correctament");
    }

    private ResponseEntity<String> construirPoblatLogica(Jugador jugador, int idVertex) {
        Partida partida = jugador.getPartida();
        
        if (partida == null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("El jugador no està en cap partida");
        }

        if (partida.getEstatPartida() == EstatPartida.FINALITZADA) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("La partida ja està finalitzada");
        }

        if (partida.getActiu() == null || !partida.getActiu().equals(jugador)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("No és el teu torn");
        }

        if (partida.getEstatPartida() == EstatPartida.EN_CURS) {
            if (!partida.isDausTirats()) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Has de tirar els daus primer");
            }

            if (partida.isLladrePendent()) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Has de moure el lladre");
            }
        } else if (partida.getEstatPartida() != EstatPartida.PREPARACIO) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("La partida no està en un estat vàlid");
        }
        
        Optional<Vertex> vertexOpt = vertexRepository.findById(idVertex);
        if (vertexOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No existeix aquest vertex");
        }
        Vertex vertex = vertexOpt.get();
        
        if (vertex.getPartida() == null || !vertex.getPartida().getId().equals(partida.getId())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("El vertex no pertany a aquesta partida");
        }
        
        if (assentamentRepository.existsByVertexId(idVertex)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("El vèrtex està ocupat");
        }
        
        long adjacentsOcupats = assentamentRepository.comptarAdjacentsOcupats(idVertex);
        if (adjacentsOcupats > 0) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Incompleix la regla de distància");
        }

        if (partida.getEstatPartida() == EstatPartida.PREPARACIO) {
            
            long numPoblats = poblatRepository.countByPropietariId(jugador.getId());
            long numCarreteres = carreteraRepository.countByPropietariId(jugador.getId());
            
            if (numPoblats > numCarreteres) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Ja has construït 1 poblat aquest torn");
            }
            
            Poblat p = new Poblat();
            p.setJugador(jugador);
            p.setVertex(vertex);
            poblatRepository.save(p);
            
            if (numPoblats == 1) { 
                List<Hexagon> hexs = hexagonRepository.findHexagonsProductiusPerVertex(idVertex);
                
                for (Hexagon h : hexs) {
                    TipusRecurs tr = null;
                    
                    if (h.getTerreny() == TipusTerreny.BOSC) {
                        tr = TipusRecurs.FUSTA;
                    } else if (h.getTerreny() == TipusTerreny.TURONS) {
                        tr = TipusRecurs.MAO;
                    } else if (h.getTerreny() == TipusTerreny.PASTURES) {
                        tr = TipusRecurs.LLANA;
                    } else if (h.getTerreny() == TipusTerreny.CAMPS) {
                        tr = TipusRecurs.BLAT;
                    } else if (h.getTerreny() == TipusTerreny.MUNTANYES) {
                        tr = TipusRecurs.MINERAL;
                    }
                    
                    if (tr != null) {
                        for (RecursJugador rj : jugador.getInventari()) {
                            if (rj.getTipusRecurs() == tr) {
                                rj.setQuantitat(rj.getQuantitat() + 1);
                            }
                        }
                    }
                }
                jugadorRepository.save(jugador);
            }
            
        } else {
            long numPoblats = poblatRepository.countByPropietariId(jugador.getId());
            if (numPoblats >= 5) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Límit de 5 poblats assolit");
            }

            long connectat = carreteraRepository.comptarConnectadesAVertex(jugador.getId(), idVertex);
            if (connectat == 0) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("No està connectat a la teva carretera");
            }

            boolean recursosSuficients = gastarRecursos(jugador, TipusRecurs.FUSTA, 1, TipusRecurs.MAO, 1, TipusRecurs.LLANA, 1, TipusRecurs.BLAT, 1);
            if (!recursosSuficients) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Sense recursos");
            }
            
            Poblat p = new Poblat();
            p.setJugador(jugador);
            p.setVertex(vertex);
            poblatRepository.save(p);
            jugadorRepository.save(jugador);
        }
                
        return ResponseEntity.ok("Poblat construït correctament");
    }

    private ResponseEntity<String> millorarCiutatLogica(Jugador jugador, int idVertex) {
        Partida partida = jugador.getPartida();
        
        if (partida == null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("El jugador no esta en ninguna partida");
        }
        
        if (partida.getEstatPartida() != EstatPartida.EN_CURS) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("La partida no esta en curs");
        }
        
        if (!partida.getActiu().equals(jugador)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("No és el teu torn.");
        }
        
        if (!partida.isDausTirats() || partida.isLladrePendent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Has de tirar els daus i no tenir lladre pendent.");
        }

        Optional<Poblat> poblatOpt = poblatRepository.findByVertexIdAndPropietariId(idVertex, jugador.getId());
        
        if (poblatOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("No hi ha cap poblat teu aquí.");
        }
        
        Poblat poblat = poblatOpt.get();

        long numCiutats = ciutatRepository.countByPropietariId(jugador.getId());
        
        if (numCiutats >= 4) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Límit de 4 ciutats assolit.");
        }

        if (!gastarRecursos(jugador, TipusRecurs.BLAT, 2, TipusRecurs.MINERAL, 3)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Sense recursos per ciutat.");
        }
        
        Vertex v = poblat.getVertex();
        

        poblatRepository.delete(poblat);
        poblatRepository.flush();
        
        Ciutat c = new Ciutat();
        c.setJugador(jugador);
        c.setVertex(v);
        
        ciutatRepository.save(c);
        jugadorRepository.save(jugador);
        
        return ResponseEntity.ok("Ciutat construïda!");
    }
    
    private boolean gastarRecursos(Jugador jugador, Object... tipusIQuantitat) {

        // 1. Comprobar que tiene todos los recursos necesarios.
        for (int i = 0; i < tipusIQuantitat.length; i += 2) {

            TipusRecurs tipus = (TipusRecurs) tipusIQuantitat[i];
            int quantitatNecessaria = (int) tipusIQuantitat[i + 1];
            boolean teSuficient = false;
            for (RecursJugador r : jugador.getInventari()) {
                if (r.getTipusRecurs() == tipus && r.getQuantitat() >= quantitatNecessaria) {
                    teSuficient = true;
                }
            }

            if (!teSuficient) {
                return false;
            }
        }

        // 2. Si tiene todo, descontar los recursos.
        for (int i = 0; i < tipusIQuantitat.length; i += 2) {

            TipusRecurs tipus = (TipusRecurs) tipusIQuantitat[i];
            int quantitatNecessaria = (int) tipusIQuantitat[i + 1];

            for (RecursJugador r : jugador.getInventari()) {
                if (r.getTipusRecurs() == tipus) {
                    r.setQuantitat(
                            r.getQuantitat() - quantitatNecessaria);
                }
            }
        }

        return true;
    }
    
    private TipusRecurs recursTerreny(TipusTerreny terreny) {
        return switch (terreny) {
            case BOSC -> TipusRecurs.FUSTA;
            case TURONS -> TipusRecurs.MAO;
            case PASTURES -> TipusRecurs.LLANA;
            case CAMPS -> TipusRecurs.BLAT;
            case MUNTANYES -> TipusRecurs.MINERAL;
            default -> null;
        };
    }
}
