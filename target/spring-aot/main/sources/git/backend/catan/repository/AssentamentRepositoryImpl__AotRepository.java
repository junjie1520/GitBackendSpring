package git.backend.catan.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import java.lang.Integer;
import java.lang.Long;
import java.lang.String;
import java.util.List;
import org.springframework.aot.generate.Generated;
import org.springframework.data.jpa.repository.aot.AotRepositoryFragmentSupport;
import org.springframework.data.jpa.repository.query.QueryEnhancerSelector;
import org.springframework.data.repository.core.support.RepositoryFactoryBeanSupport;
import org.springframework.data.repository.query.Param;

/**
 * AOT generated JPA repository implementation for {@link AssentamentRepository}.
 */
@Generated
public class AssentamentRepositoryImpl__AotRepository extends AotRepositoryFragmentSupport {
  private final RepositoryFactoryBeanSupport.FragmentCreationContext context;

  private final EntityManager entityManager;

  public AssentamentRepositoryImpl__AotRepository(EntityManager entityManager,
      RepositoryFactoryBeanSupport.FragmentCreationContext context) {
    super(QueryEnhancerSelector.DEFAULT_SELECTOR, context);
    this.entityManager = entityManager;
    this.context = context;
  }

  /**
   * AOT generated implementation of {@link AssentamentRepository#contarEnExtremos(java.lang.Integer,java.lang.Integer,java.lang.Integer)}.
   */
  public long contarEnExtremos(@Param("idJugador") Integer idJugador, @Param("vA") Integer vA,
      @Param("vB") Integer vB) {
    String queryString = "SELECT COUNT(a) FROM Assentament a WHERE a.propietari.id = :idJugador AND (a.vertex.id = :vA OR a.vertex.id = :vB)";
    Query query = this.entityManager.createQuery(queryString);
    query.setParameter("idJugador", idJugador);
    query.setParameter("vA", vA);
    query.setParameter("vB", vB);

    return (Long) convertOne(query.getSingleResultOrNull(), false, Long.class);
  }

  /**
   * AOT generated implementation of {@link AssentamentRepository#countByPropietariIdAndVertexIdIn(java.lang.Integer,java.util.List)}.
   */
  public long countByPropietariIdAndVertexIdIn(Integer propietariId, List<Integer> vertexIds) {
    String queryString = "SELECT COUNT(a) FROM Assentament a WHERE a.propietari.id = :propietariId AND a.vertex.id IN :vertexIds";
    Query query = this.entityManager.createQuery(queryString);
    query.setParameter("propietariId", propietariId);
    query.setParameter("vertexIds", vertexIds);

    return (Long) convertOne(query.getSingleResultOrNull(), false, Long.class);
  }

  /**
   * AOT generated implementation of {@link AssentamentRepository#existsByVertexId(java.lang.Integer)}.
   */
  public boolean existsByVertexId(Integer vertexId) {
    String queryString = "SELECT a.id FROM Assentament a WHERE a.vertex.id = :vertexId";
    Query query = this.entityManager.createQuery(queryString);
    query.setParameter("vertexId", vertexId);
    query.setMaxResults(1);

    return !query.getResultList().isEmpty();
  }
}
