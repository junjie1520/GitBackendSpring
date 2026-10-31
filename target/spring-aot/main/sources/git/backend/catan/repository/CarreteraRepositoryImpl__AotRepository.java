package git.backend.catan.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import java.lang.Integer;
import java.lang.Long;
import java.lang.String;
import org.springframework.aot.generate.Generated;
import org.springframework.data.jpa.repository.aot.AotRepositoryFragmentSupport;
import org.springframework.data.jpa.repository.query.QueryEnhancerSelector;
import org.springframework.data.repository.core.support.RepositoryFactoryBeanSupport;
import org.springframework.data.repository.query.Param;

/**
 * AOT generated JPA repository implementation for {@link CarreteraRepository}.
 */
@Generated
public class CarreteraRepositoryImpl__AotRepository extends AotRepositoryFragmentSupport {
  private final RepositoryFactoryBeanSupport.FragmentCreationContext context;

  private final EntityManager entityManager;

  public CarreteraRepositoryImpl__AotRepository(EntityManager entityManager,
      RepositoryFactoryBeanSupport.FragmentCreationContext context) {
    super(QueryEnhancerSelector.DEFAULT_SELECTOR, context);
    this.entityManager = entityManager;
    this.context = context;
  }

  /**
   * AOT generated implementation of {@link CarreteraRepository#comptarConnectadesAVertex(java.lang.Integer,java.lang.Integer)}.
   */
  public long comptarConnectadesAVertex(@Param("jugadorId") Integer jugadorId,
      @Param("vertexId") Integer vertexId) {
    String queryString = "SELECT COUNT(c) FROM Carretera c WHERE c.propietari.id = :jugadorId AND (c.aresta.vertexA.id = :vertexId OR c.aresta.vertexB.id = :vertexId)";
    Query query = this.entityManager.createQuery(queryString);
    query.setParameter("jugadorId", jugadorId);
    query.setParameter("vertexId", vertexId);

    return (Long) convertOne(query.getSingleResultOrNull(), false, Long.class);
  }

  /**
   * AOT generated implementation of {@link CarreteraRepository#contarCarreterasConectadas(java.lang.Integer,java.lang.Integer,java.lang.Integer,java.lang.Integer)}.
   */
  public long contarCarreterasConectadas(@Param("idJugador") Integer idJugador,
      @Param("idAresta") Integer idAresta, @Param("vA") Integer vA, @Param("vB") Integer vB) {
    String queryString = "SELECT COUNT(c) FROM Carretera c WHERE c.propietari.id = :idJugador AND c.aresta.id <> :idAresta AND (c.aresta.vertexA.id = :vA OR c.aresta.vertexB.id = :vA OR c.aresta.vertexA.id = :vB OR c.aresta.vertexB.id = :vB)";
    Query query = this.entityManager.createQuery(queryString);
    query.setParameter("idJugador", idJugador);
    query.setParameter("idAresta", idAresta);
    query.setParameter("vA", vA);
    query.setParameter("vB", vB);

    return (Long) convertOne(query.getSingleResultOrNull(), false, Long.class);
  }

  /**
   * AOT generated implementation of {@link CarreteraRepository#countByPropietariId(java.lang.Integer)}.
   */
  public long countByPropietariId(Integer propietariId) {
    String queryString = "SELECT COUNT(c) FROM Carretera c WHERE c.propietari.id = :propietariId";
    Query query = this.entityManager.createQuery(queryString);
    query.setParameter("propietariId", propietariId);

    return (Long) convertOne(query.getSingleResultOrNull(), false, Long.class);
  }

  /**
   * AOT generated implementation of {@link CarreteraRepository#existsByArestaId(java.lang.Integer)}.
   */
  public boolean existsByArestaId(Integer idAresta) {
    String queryString = "SELECT c.id FROM Carretera c WHERE c.aresta.id = :idAresta";
    Query query = this.entityManager.createQuery(queryString);
    query.setParameter("idAresta", idAresta);
    query.setMaxResults(1);

    return !query.getResultList().isEmpty();
  }
}
