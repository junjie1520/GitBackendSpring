package git.backend.catan.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import java.lang.Integer;
import java.lang.Object;
import java.lang.String;
import java.util.List;
import org.springframework.aot.generate.Generated;
import org.springframework.data.jpa.repository.aot.AotRepositoryFragmentSupport;
import org.springframework.data.jpa.repository.query.QueryEnhancerSelector;
import org.springframework.data.repository.core.support.RepositoryFactoryBeanSupport;
import org.springframework.data.repository.query.Param;

/**
 * AOT generated JPA repository implementation for {@link PartidaRepository}.
 */
@Generated
public class PartidaRepositoryImpl__AotRepository extends AotRepositoryFragmentSupport {
  private final RepositoryFactoryBeanSupport.FragmentCreationContext context;

  private final EntityManager entityManager;

  public PartidaRepositoryImpl__AotRepository(EntityManager entityManager,
      RepositoryFactoryBeanSupport.FragmentCreationContext context) {
    super(QueryEnhancerSelector.DEFAULT_SELECTOR, context);
    this.entityManager = entityManager;
    this.context = context;
  }

  /**
   * AOT generated implementation of {@link PartidaRepository#findTauler(java.lang.Integer)}.
   */
  public List<Object[]> findTauler(@Param("idPartida") Integer idPartida) {
    String queryString = "select h, hv.posicio, a, j from Hexagon h left join h.hexagonVertices hv left join Assentament a on a.vertex = hv.vertex left join a.propietari j where h.partida.id = :idPartida order by h.codi, hv.posicio";
    Query query = this.entityManager.createQuery(queryString);
    query.setParameter("idPartida", idPartida);

    return (List<Object[]>) convertMany(query.getResultList(), false, Object[].class);
  }
}
