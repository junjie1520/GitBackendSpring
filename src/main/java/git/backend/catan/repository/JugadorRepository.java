package git.backend.catan.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import git.backend.catan.model.Jugador;
import java.util.List;


@Repository
public interface JugadorRepository extends JpaRepository<Jugador, Integer> {

    Optional<Jugador> findByPartidaIdAndOrdreTorn(Integer partidaId, Integer ordreTorn);
   

}