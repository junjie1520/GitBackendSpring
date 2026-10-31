package git.backend.catan.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import git.backend.catan.model.Ciutat;

@Repository
public interface CiutatRepository extends JpaRepository<Ciutat, Integer>{
	// Spring fa sol el SELECT COUNT de ciutats[cite: 14]
    long countByPropietariId(Integer propietariId);
}
