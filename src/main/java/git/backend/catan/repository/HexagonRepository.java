package git.backend.catan.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import git.backend.catan.model.Hexagon;

public interface HexagonRepository extends JpaRepository<Hexagon, Integer>{
	@Query("""
			SELECT DISTINCT h FROM Hexagon h
			LEFT JOIN FETCH h.hexagonVertices hv
			LEFT JOIN FETCH hv.vertex
			WHERE h.partida.id = :idPartida
			ORDER BY h.codi, hv.posicio
			""")
	List<Hexagon> llistarTauler(@Param("idPartida") Integer idPartida);
}
