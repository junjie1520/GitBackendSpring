package git.backend.catan.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import git.backend.catan.repository.PartidaRepository;

public class PartidaService {
	@Autowired
	private PartidaRepository partidaRepository;
	
	public ResponseEntity<List<Object[]>> findTauler(){
		return null;
		
	}
}
