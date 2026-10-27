package git.backend.catan.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import git.backend.catan.model.Partida;

public interface PartidaRepository extends JpaRepository<Partida, Integer> {
}
