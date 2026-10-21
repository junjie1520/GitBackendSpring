package git.backend.catan.model;

import jakarta.persistence.Entity;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;

@Entity
@Table(name = "poblat")
@NamedQuery(name = "Poblat.comptarPerJugador",
query = "SELECT COUNT(p) FROM Poblat p WHERE p.propietari.id = :id")
public class Poblat extends Assentament {

	public Poblat() {
		super();
	}
	
} 
