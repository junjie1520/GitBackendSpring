package git.backend.catan.repository;

import git.backend.catan.model.Hexagon;
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
 * AOT generated JPA repository implementation for {@link HexagonRepository}.
 */
@Generated
public class HexagonRepositoryImpl__AotRepository extends AotRepositoryFragmentSupport {
  private final RepositoryFactoryBeanSupport.FragmentCreationContext context;

  private final EntityManager entityManager;

  public HexagonRepositoryImpl__AotRepository(EntityManager entityManager,
      RepositoryFactoryBeanSupport.FragmentCreationContext context) {
    super(QueryEnhancerSelector.DEFAULT_SELECTOR, context);
    this.entityManager = entityManager;
    this.context = context;
  }

  /**
   * AOT generated implementation of {@link HexagonRepository#llistarTauler(java.lang.Integer)}.
   */
  public List<Hexagon> llistarTauler(@Param("idPartida") Integer idPartida) {
    String queryString = "SELECT DISTINCT h FROM Hexagon h\n"
            + "LEFT JOIN FETCH h.hexagonVertices hv\n"
            + "LEFT JOIN FETCH hv.vertex\n"
            + "WHERE h.partida.id = :idPartida\n"
            + "ORDER BY h.codi, hv.posicio\n";
    Query query = this.entityManager.createQuery(queryString);
    query.setParameter("idPartida", idPartida);

    return (List<Hexagon>) query.getResultList();
  }
}
