package git.backend.catan.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import git.backend.catan.model.Poblat;

@Repository
public interface PoblatRepository extends JpaRepository<Poblat, Integer> {

    @Query("SELECT COUNT(p) FROM Poblat p " +
           "WHERE p.propietari.id = :idJugador")
    long contarDelJugador(
            @Param("idJugador") Integer idJugador);

    @Query("SELECT p FROM Poblat p " +
           "WHERE p.propietari.id = :idJugador " +
           "ORDER BY p.id DESC")
    List<Poblat> buscarDelJugadorDelUltimoAlPrimero(
            @Param("idJugador") Integer idJugador);
    
    long countByPropietariId(Integer propietariId);
 // Devuelve los poblados del jugador ordenados de más reciente a más antiguo (OrderByIdDesc)
    List<Poblat> findByPropietariIdOrderByIdDesc(Integer propietariId);
    
 // Spring fa sol la consulta per buscar un poblat concret d'un vèrtex i jugador[cite: 14]
    Optional<Poblat> findByVertexIdAndPropietariId(Integer vertexId, Integer propietariId);
    
}