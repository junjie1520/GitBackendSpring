package git.backend.catan.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import git.backend.catan.model.Carretera;

@Repository
public interface CarreteraRepository extends JpaRepository<Carretera, Integer> {

    boolean existsByArestaId(Integer idAresta);

    long countByPropietariId(Integer propietariId);

    @Query("SELECT COUNT(c) FROM Carretera c " +
           "WHERE c.propietari.id = :idJugador " +
           "AND c.aresta.id <> :idAresta " +
           "AND (c.aresta.vertexA.id = :vA " +
           "OR c.aresta.vertexB.id = :vA " +
           "OR c.aresta.vertexA.id = :vB " +
           "OR c.aresta.vertexB.id = :vB)")
    long contarCarreterasConectadas(
            @Param("idJugador") Integer idJugador,
            @Param("idAresta") Integer idAresta,
            @Param("vA") Integer vA,
            @Param("vB") Integer vB);

    @Query("SELECT COUNT(c) FROM Carretera c WHERE c.propietari.id = :jugadorId AND (c.aresta.vertexA.id = :vertexId OR c.aresta.vertexB.id = :vertexId)")
    long comptarConnectadesAVertex(
            @Param("jugadorId") Integer jugadorId, 
            @Param("vertexId") Integer vertexId);
}