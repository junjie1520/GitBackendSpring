package git.backend.catan.service;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import git.backend.catan.model.Aresta;
import git.backend.catan.model.EstatPartida;
import git.backend.catan.model.Jugador;
import git.backend.catan.model.Partida;
import git.backend.catan.repository.ArestaRepository;
import git.backend.catan.repository.JugadorRepository;
import git.backend.catan.repository.PartidaRepository;
import git.backend.catan.repository.VertexRepository;

@Service
public class JugadorService {

    @Autowired
    private JugadorRepository jugadorRepository;
    @Autowired
    private PartidaRepository partidaRepository;
    @Autowired
    private ArestaRepository arestaRepository;
    @Autowired
    private VertexRepository vertexRepository;

    @Transactional
    public ResponseEntity<String> construir(int idJugador, String tipus, int posicio) {
        // 1. Buscamos el jugador usando el Optional de Spring Data
        Optional<Jugador> jugadorOpt = jugadorRepository.findById(idJugador);
        if (jugadorOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No existeix aquest jugador");
        }
        Jugador jugador = jugadorOpt.get();

        // 2. Ejecutamos la lógica según el tipo
        try {
            switch (tipus.toUpperCase()) {
                case "CARRETERA":
                    return construirCarreteraLogica(jugador, posicio);
                case "POBLAT":
                    return construirPoblatLogica(jugador, posicio);
                case "CIUTAT":
                    return millorarCiutatLogica(jugador, posicio);
                default:
                    return ResponseEntity.badRequest().body("Tipus de construcció invàlid");
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error construint: " + e.getMessage());
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

        // Trasladamos tus validaciones de JugadorDao.PassarTorn aquí:
        if (partida.getEstatPartida() == EstatPartida.FINALITZADA) {
            return ResponseEntity.badRequest().body("La partida està finalitzada");
        }
        if (!partida.getActiu().equals(jugador)) {
            return ResponseEntity.badRequest().body("No és el teu torn");
        }
        if (partida.isLladrePendent()) {
            return ResponseEntity.badRequest().body("Has de moure el lladre pendent");
        }

        if (partida.getEstatPartida() == EstatPartida.EN_CURS) {
            if (!partida.isDausTirats()) {
                return ResponseEntity.badRequest().body("No has tirat els daus");
            }
            
            // Lógica para pasar al siguiente jugador
            int seguentOrdre = (jugador.getOrdreTorn() % partida.getJugadors().size()) + 1;
            Jugador seguent = jugadorRepository.findByPartidaIdAndOrdreTorn(partida.getId(), seguentOrdre).get();

            partida.setActiu(seguent);
            partida.setDausTirats(false);
            if (seguent.getOrdreTorn() == 1) {
                partida.setRonda(partida.getRonda() + 1);
            }
            
            partidaRepository.save(partida);
            return ResponseEntity.ok("Torn passat correctament. Nou actiu: " + seguent.getNom());
            
        } else if (partida.getEstatPartida() == EstatPartida.PREPARACIO) {
            // Aquí pones tu lógica de preparación (orden serpiente) que tenías en el DAO
            // ...
            return ResponseEntity.ok("Torn de preparació passat");
        }

        return ResponseEntity.badRequest().body("Estat de partida invàlid");
    }

    // --- Métodos privados extraídos de tu antiguo JugadorDao ---

    private ResponseEntity<String> construirCarreteraLogica(Jugador jugador, int idAresta) {
        Partida partida = jugador.getPartida();
        Optional<Aresta> arestaOpt = arestaRepository.findById(idAresta);
        
        if (arestaOpt.isEmpty()) return ResponseEntity.badRequest().body("L'aresta no existeix");
        
        if (!partida.getActiu().equals(jugador)) {
            return ResponseEntity.badRequest().body("No és el teu torn");
        }
        // ... (Aquí pegas el resto de comprobaciones de tu JugadorDao: límites, recursos, si está ocupada) ...
        // ... (Usa arestaRepository y jugadorRepository en lugar de session.createQuery) ...

        return ResponseEntity.ok("Carretera construïda correctament");
    }

    private ResponseEntity<String> construirPoblatLogica(Jugador jugador, int idVertex) {
        // ... Tu lógica adaptada de ConstruirPoblat (distancia, recursos, etc.)
        return ResponseEntity.ok("Poblat construït correctament");
    }

    private ResponseEntity<String> millorarCiutatLogica(Jugador jugador, int idVertex) {
         // ... Tu lógica adaptada de MillorarACiutat
        return ResponseEntity.ok("Ciutat millorada correctament");
    }
}
