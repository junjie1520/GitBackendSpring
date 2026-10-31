package git.backend.catan.repository;

import git.backend.catan.model.Jugador;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import java.lang.Integer;
import java.lang.String;
import java.util.Optional;
import org.springframework.aot.generate.Generated;
import org.springframework.data.jpa.repository.aot.AotRepositoryFragmentSupport;
import org.springframework.data.jpa.repository.query.QueryEnhancerSelector;
import org.springframework.data.repository.core.support.RepositoryFactoryBeanSupport;

/**
 * AOT generated JPA repository implementation for {@link JugadorRepository}.
 */
@Generated
public class JugadorRepositoryImpl__AotRepository extends AotRepositoryFragmentSupport {
  private final RepositoryFactoryBeanSupport.FragmentCreationContext context;

  private final EntityManager entityManager;

  public JugadorRepositoryImpl__AotRepository(EntityManager entityManager,
      RepositoryFactoryBeanSupport.FragmentCreationContext context) {
    super(QueryEnhancerSelector.DEFAULT_SELECTOR, context);
    this.entityManager = entityManager;
    this.context = context;
  }

  /**
   * AOT generated implementation of {@link JugadorRepository#findByPartidaIdAndOrdreTorn(java.lang.Integer,java.lang.Integer)}.
   */
  public Optional<Jugador> findByPartidaIdAndOrdreTorn(Integer partidaId, Integer ordreTorn) {
    String queryString = "SELECT j FROM Jugador j WHERE j.partida.id = :partidaId AND j.ordreTorn = :ordreTorn";
    Query query = this.entityManager.createQuery(queryString);
    query.setParameter("partidaId", partidaId);
    query.setParameter("ordreTorn", ordreTorn);

    return Optional.ofNullable((Jugador) convertOne(query.getSingleResultOrNull(), false, Jugador.class));
  }
}
