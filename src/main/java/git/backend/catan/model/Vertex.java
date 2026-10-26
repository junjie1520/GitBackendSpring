package git.backend.catan.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.hibernate.annotations.DynamicInsert;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Entity	
@Table(name= "vertex")
@DynamicInsert
public class Vertex {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@NotNull(message="El codi no pot ser null")
	@Column(nullable = false)
	@Min(value = 1, message = "El codi ha de ser major o igual a 1")
	private Integer codi;
	
	@OneToMany(mappedBy = "vertex", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonIgnore
	private List<HexagonVertex> hexagonVertices = new ArrayList<>();
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "partida_vertex_id", nullable = false)
	@JsonIgnore
	private Partida partida;

	public Vertex() {}

	public Vertex(Integer codi) {
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
	public List<HexagonVertex> getHexagonVertices() {
		return hexagonVertices; 
	}
	public void setHexagonVertices(List<HexagonVertex> hexagonVertices) {
		this.hexagonVertices = hexagonVertices;
	}

	public Partida getPartida() {
		return partida;
	}

	public void setPartida(Partida partida) {
		this.partida = partida;
	}

	@Override
	public String toString() {
		return "Vertex [id=" + id + ", codi=" + codi + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(codi, id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null || getClass() != obj.getClass()) return false;
		Vertex other = (Vertex) obj;
		return Objects.equals(codi, other.codi) && Objects.equals(id, other.id);
	}
}