package git.backend.catan.repository;

import git.backend.catan.model.Vertex;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import java.lang.Integer;
import java.lang.String;
import java.util.List;
import org.springframework.aot.generate.Generated;
import org.springframework.data.jpa.repository.aot.AotRepositoryFragmentSupport;
import org.springframework.data.jpa.repository.query.QueryEnhancerSelector;
import org.springframework.data.repository.core.support.RepositoryFactoryBeanSupport;
import org.springframework.data.repository.query.Param;

/**
 * AOT generated JPA repository implementation for {@link VertexRepository}.
 */
@Generated
public class VertexRepositoryImpl__AotRepository extends AotRepositoryFragmentSupport {
  private final RepositoryFactoryBeanSupport.FragmentCreationContext context;

  private final EntityManager entityManager;

  public VertexRepositoryImpl__AotRepository(EntityManager entityManager,
      RepositoryFactoryBeanSupport.FragmentCreationContext context) {
    super(QueryEnhancerSelector.DEFAULT_SELECTOR, context);
    this.entityManager = entityManager;
    this.context = context;
  }

  /**
   * AOT generated implementation of {@link VertexRepository#findVertexsDisponibles(java.lang.Integer,java.lang.Integer,boolean)}.
   */
  public List<Vertex> findVertexsDisponibles(@Param("partidaId") Integer partidaId,
      @Param("jugadorId") Integer jugadorId, @Param("esPreparacio") boolean esPreparacio) {
    String queryString = "SELECT DISTINCT v FROM HexagonVertex hv JOIN hv.vertex v WHERE hv.hexagon.partida.id = :partidaId AND v.id NOT IN (SELECT a.vertex.id FROM Assentament a) AND v.id NOT IN (SELECT ar.vertexB.id FROM Aresta ar WHERE ar.vertexA.id IN (SELECT a1.vertex.id FROM Assentament a1)) AND v.id NOT IN (SELECT ar2.vertexA.id FROM Aresta ar2 WHERE ar2.vertexB.id IN (SELECT a2.vertex.id FROM Assentament a2)) AND (:esPreparacio = true OR (v.id IN (SELECT c1.aresta.vertexA.id FROM Carretera c1 WHERE c1.propietari.id = :jugadorId) OR v.id IN (SELECT c2.aresta.vertexB.id FROM Carretera c2 WHERE c2.propietari.id = :jugadorId)))";
    Query query = this.entityManager.createQuery(queryString);
    query.setParameter("partidaId", partidaId);
    query.setParameter("esPreparacio", esPreparacio);
    query.setParameter("jugadorId", jugadorId);

    return (List<Vertex>) query.getResultList();
  }
}
