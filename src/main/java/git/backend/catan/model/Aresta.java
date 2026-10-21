package git.backend.catan.model;

import java.util.Objects;
import org.hibernate.annotations.DynamicInsert;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Entity	
@Table(name= "aresta") 
@DynamicInsert
public class Aresta {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@NotNull(message="El codi no pot ser null")
	@Column(nullable = false)
	@Min(value = 1, message = "El codi ha de ser major o igual a 1")
	private Integer codi;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)  
	@JoinColumn(name = "vertex_a_id", nullable = false)
	private Vertex vertexA;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)  
	@JoinColumn(name = "vertex_b_id", nullable = false)
	private Vertex vertexB;
	
	
	@OneToOne(mappedBy = "aresta", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
	private Carretera carretera;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "partida_aresta_id", nullable = false)
	private Partida partida;
	
	public Aresta() {}

	public Aresta(Integer codi) {
		this.codi = codi;
	}

	public Integer getId() {
		return id; 
		}
	public Integer getCodi() {
		return codi;
	}
	public void setCodi(Integer codi) { 
		this.codi = codi; 
	}
	public Vertex getVertexA() { 
		return vertexA;
	}
	public void setVertexA(Vertex vertexA) { 
		this.vertexA = vertexA; 
	}
	public Vertex getVertexB() {
		return vertexB; 
	}
	public void setVertexB(Vertex vertexB) { 
		this.vertexB = vertexB;
	}

	public Carretera getCarretera() {
		return carretera;
	}

	public void setCarretera(Carretera carretera) {
		this.carretera = carretera;
	}

	public Partida getPartida() {
		return partida;
	}

	public void setPartida(Partida partida) {
		this.partida = partida;
	}

	@Override
	public String toString() {
		return "Aresta [id=" + id + ", codi=" + codi + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(codi, id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null || getClass() != obj.getClass()) return false;
		Aresta other = (Aresta) obj;
		return Objects.equals(codi, other.codi) && Objects.equals(id, other.id);
	}
}