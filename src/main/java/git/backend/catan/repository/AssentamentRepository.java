package git.backend.catan.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import git.backend.catan.model.Assentament;

@Repository
public interface AssentamentRepository extends JpaRepository<Assentament, Integer> {

    @Query("SELECT COUNT(a) FROM Assentament a " +
           "WHERE a.propietari.id = :idJugador " +
           "AND (a.vertex.id = :vA OR a.vertex.id = :vB)")
    long contarEnExtremos(
            @Param("idJugador") Integer idJugador,
            @Param("vA") Integer vA,
            @Param("vB") Integer vB);
    @Query("SELECT COUNT(a) FROM Assentament a WHERE a.vertex.id"+
            "IN (SELECT ar.vertexB.id FROM Aresta ar WHERE ar.vertexA.id"+
    		" = :idV) OR a.vertex.id IN (SELECT ar2.vertexA.id FROM Aresta"+
            " ar2 WHERE ar2.vertexB.id = :idV)")
    boolean existsByVertexId(Integer vertexId);
    
    // Nivell 3: Regla de distància
    @Query("SELECT COUNT(a) FROM Assentament a WHERE a.vertex.id IN (SELECT ar.vertexB.id FROM Aresta ar WHERE ar.vertexA.id = :idV) " +
           "OR a.vertex.id IN (SELECT ar2.vertexA.id FROM Aresta ar2 WHERE ar2.vertexB.id = :idV)")
    long comptarAdjacentsOcupats(@Param("idV") Integer idVertex);
 // El "IdIn" hace un "WHERE vertex.id IN (...)" pasándole la lista de los dos extremos
    
    long countByPropietariIdAndVertexIdIn(Integer propietariId, List<Integer> vertexIds);
}