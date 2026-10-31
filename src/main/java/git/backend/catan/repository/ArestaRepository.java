package git.backend.catan.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import git.backend.catan.model.Aresta;

@Repository
public interface ArestaRepository extends JpaRepository<Aresta, Integer> {
	
	@Query("SELECT COUNT(a) FROM Aresta a " +
		       "WHERE a.id = :idAresta " +
		       "AND a.partida.id = :idPartida")
		long contarDePartida(
		        @Param("idAresta") Integer idAresta,
		        @Param("idPartida") Integer idPartida);

	
		
}