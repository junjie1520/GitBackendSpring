package git.backend.catan.repository;

import git.backend.catan.model.Poblat;
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
 * AOT generated JPA repository implementation for {@link PoblatRepository}.
 */
@Generated
public class PoblatRepositoryImpl__AotRepository extends AotRepositoryFragmentSupport {
  private final RepositoryFactoryBeanSupport.FragmentCreationContext context;

  private final EntityManager entityManager;

  public PoblatRepositoryImpl__AotRepository(EntityManager entityManager,
      RepositoryFactoryBeanSupport.FragmentCreationContext context) {
    super(QueryEnhancerSelector.DEFAULT_SELECTOR, context);
    this.entityManager = entityManager;
    this.context = context;
  }

  /**
   * AOT generated implementation of {@link PoblatRepository#buscarDelJugadorDelUltimoAlPrimero(java.lang.Integer)}.
   */
  public List<Poblat> buscarDelJugadorDelUltimoAlPrimero(@Param("idJugador") Integer idJugador) {
    String queryString = "SELECT p FROM Poblat p WHERE p.propietari.id = :idJugador ORDER BY p.id DESC";
    Query query = this.entityManager.createQuery(queryString);
    query.setParameter("idJugador", idJugador);

    return (List<Poblat>) query.getResultList();
  }

  /**
   * AOT generated implementation of {@link PoblatRepository#contarDelJugador(java.lang.Integer)}.
   */
  public long contarDelJugador(@Param("idJugador") Integer idJugador) {
    String queryString = "SELECT COUNT(p) FROM Poblat p WHERE p.propietari.id = :idJugador";
    Query query = this.entityManager.createQuery(queryString);
    query.setParameter("idJugador", idJugador);

    return (Long) convertOne(query.getSingleResultOrNull(), false, Long.class);
  }

  /**
   * AOT generated implementation of {@link PoblatRepository#countByPropietariId(java.lang.Integer)}.
   */
  public long countByPropietariId(Integer propietariId) {
    String queryString = "SELECT COUNT(p) FROM Poblat p WHERE p.propietari.id = :propietariId";
    Query query = this.entityManager.createQuery(queryString);
    query.setParameter("propietariId", propietariId);

    return (Long) convertOne(query.getSingleResultOrNull(), false, Long.class);
  }

  /**
   * AOT generated implementation of {@link PoblatRepository#findByPropietariIdOrderByIdDesc(java.lang.Integer)}.
   */
  public List<Poblat> findByPropietariIdOrderByIdDesc(Integer propietariId) {
    String queryString = "SELECT p FROM Poblat p WHERE p.propietari.id = :propietariId ORDER BY p.id desc";
    Query query = this.entityManager.createQuery(queryString);
    query.setParameter("propietariId", propietariId);

    return (List<Poblat>) query.getResultList();
  }
}
