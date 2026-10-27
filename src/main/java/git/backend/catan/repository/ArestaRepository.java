package git.backend.catan.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import git.backend.catan.model.Aresta;

@Repository
public interface ArestaRepository extends JpaRepository<Aresta, Integer> {
}