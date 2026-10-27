package git.backend.catan.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import git.backend.catan.model.Vertex;

@Repository
public interface VertexRepository extends JpaRepository<Vertex, Integer> {

    // Pasamos tu JPQL exacto a Spring. Usamos :esPreparacio para simplificar el IF que tenías en el DAO.
    @Query("SELECT DISTINCT v FROM HexagonVertex hv JOIN hv.vertex v WHERE hv.hexagon.partida.id = :partidaId " +
           "AND v.id NOT IN (SELECT a.vertex.id FROM Assentament a) " +
           "AND v.id NOT IN (SELECT ar.vertexB.id FROM Aresta ar WHERE ar.vertexA.id IN (SELECT a1.vertex.id FROM Assentament a1)) " + 
           "AND v.id NOT IN (SELECT ar2.vertexA.id FROM Aresta ar2 WHERE ar2.vertexB.id IN (SELECT a2.vertex.id FROM Assentament a2)) " +
           "AND (:esPreparacio = true OR (v.id IN (SELECT c1.aresta.vertexA.id FROM Carretera c1 WHERE c1.propietari.id = :jugadorId) " +
           "OR v.id IN (SELECT c2.aresta.vertexB.id FROM Carretera c2 WHERE c2.propietari.id = :jugadorId)))")
    List<Vertex> findVertexsDisponibles(
            @Param("partidaId") Integer partidaId, 
            @Param("jugadorId") Integer jugadorId, 
            @Param("esPreparacio") boolean esPreparacio);

}