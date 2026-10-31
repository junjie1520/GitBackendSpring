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
 * AOT generated JPA repository implementation for {@link ArestaRepository}.
 */
@Generated
public class ArestaRepositoryImpl__AotRepository extends AotRepositoryFragmentSupport {
  private final RepositoryFactoryBeanSupport.FragmentCreationContext context;

  private final EntityManager entityManager;

  public ArestaRepositoryImpl__AotRepository(EntityManager entityManager,
      RepositoryFactoryBeanSupport.FragmentCreationContext context) {
    super(QueryEnhancerSelector.DEFAULT_SELECTOR, context);
    this.entityManager = entityManager;
    this.context = context;
  }

  /**
   * AOT generated implementation of {@link ArestaRepository#contarDePartida(java.lang.Integer,java.lang.Integer)}.
   */
  public long contarDePartida(@Param("idAresta") Integer idAresta,
      @Param("idPartida") Integer idPartida) {
    String queryString = "SELECT COUNT(a) FROM Aresta a WHERE a.id = :idAresta AND a.partida.id = :idPartida";
    Query query = this.entityManager.createQuery(queryString);
    query.setParameter("idAresta", idAresta);
    query.setParameter("idPartida", idPartida);

    return (Long) convertOne(query.getSingleResultOrNull(), false, Long.class);
  }
}
