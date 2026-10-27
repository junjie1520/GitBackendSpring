package git.backend.catan.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import git.backend.catan.model.Assentament;

public interface AssentamentRepository extends JpaRepository<Assentament,Integer>{
	
	@Query("""
			SELECT a FROM Assentament a
			JOIN FETCH a.vertex v
			JOIN FETCH a.propietari
			WHERE v.partida.id = :idPartida
			""")
	List<Assentament> llistarPerPartida(@Param("idPartida") Integer idPartida);
}
