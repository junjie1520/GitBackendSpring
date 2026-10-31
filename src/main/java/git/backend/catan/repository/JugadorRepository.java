package git.backend.catan.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import git.backend.catan.model.Jugador;
import java.util.List;


@Repository
public interface JugadorRepository extends JpaRepository<Jugador, Integer> {

    // Spring deduce la consulta SQL solo leyendo el nombre del método
    // Sustituye a: SELECT j FROM Jugador j WHERE j.partida.id = :pId AND j.ordreTorn = :ordre
    Optional<Jugador> findByPartidaIdAndOrdreTorn(Integer partidaId, Integer ordreTorn);
   

}