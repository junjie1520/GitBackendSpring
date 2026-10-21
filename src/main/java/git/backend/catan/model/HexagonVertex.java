package git.backend.catan.model;

import java.util.Objects;

import org.hibernate.annotations.DynamicInsert;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
@Entity	
@Table(name= "hexagon_vertex")
@DynamicInsert
public class HexagonVertex {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@NotNull(message="El posicio no pot ser null")
	@Min(value = 0, message="No pot ser menys de 0")
	@Max(value = 5, message="No pot ser mes de 5")
	@Column
	private Integer posicio;

	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "vertex_id", nullable = false)
	private Vertex vertex;
	
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "hexagon_id", nullable = false)
	private Hexagon hexagon;
	
	public HexagonVertex() {
		
	}

	public HexagonVertex(
			@NotNull(message = "El posicio no pot ser null") @Min(value = 0, message = "No pot ser menys de 0") @Max(value = 5, message = "No pot ser mes de 5") Integer posicio,
			Vertex vertex, Hexagon hexagon) {
		super();
		this.posicio = posicio;
		this.vertex = vertex;
		this.hexagon = hexagon;
	}

	public Integer getPosicio() { 
		return posicio; 
	}
	
	public void setPosicio(Integer posicio) {
		this.posicio = posicio;
	}
	
	public Integer getId() {
		return id;
	}
	
	public Vertex getVertex() {
		return vertex;
	}
	
	public void setVertex(Vertex vertex) { 
		this.vertex = vertex; 
	}
	
	public Hexagon getHexagon() {
		return hexagon;
	}
	
	public void setHexagon(Hexagon hexagon) {
		this.hexagon = hexagon;
	}

	@Override
	public String toString() {
		return "HexagonVertex [id=" + id + ", posicio=" + posicio + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, posicio);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null || getClass() != obj.getClass()) return false;
		HexagonVertex other = (HexagonVertex) obj;
		return Objects.equals(id, other.id) && Objects.equals(posicio, other.posicio);
	}
}
	
