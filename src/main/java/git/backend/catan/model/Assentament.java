package git.backend.catan.model;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
	
@Entity
@Table(name = "assentament")
public abstract class Assentament extends Construccio { 
	
	@OneToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "vertex_id", unique = true, nullable = false)
	private Vertex vertex; 
	
	public Assentament() { 
		super(); 
		} 
	public Assentament(Vertex vertex) {
		super(); 
		this.vertex = vertex; 
		} 
	public Vertex getVertex() {
		return vertex; 
		} 
	public void setVertex(Vertex vertex) {
		this.vertex = vertex; 
		} 
	@Override public String toString() { 
		return getClass().getSimpleName() + " [id=" + getId() + "]"; 
		} 
}
	
	


	
