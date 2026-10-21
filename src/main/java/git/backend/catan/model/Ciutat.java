package git.backend.catan.model;

import jakarta.persistence.Entity;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;

@Entity
@Table(name = "ciutat")
@NamedQuery(name = "Ciutat.comptarPerJugador",
query = "SELECT COUNT(p) FROM Ciutat p WHERE p.propietari.id = :id")
public class Ciutat extends Assentament {

	public Ciutat() {
		super();
	}
	
} 
