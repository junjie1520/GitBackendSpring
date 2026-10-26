package git.backend.catan.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import git.backend.catan.model.Partida;

public interface PartidaRepository extends JpaRepository<Partida, Integer> {
	@Query("select h, hv.posicio, a, j "
			+ "from Hexagon h "
			+ "left join h.hexagonVertices hv "
			+ "left join Assentament a on a.vertex = hv.vertex "
			+ "left join a.propietari j "
			+ "where h.partida.id = :idPartida "
			+ "order by h.codi, hv.posicio")
	List<Object[]> findTauler(@Param("idPartida") Integer idPartida);
}
