package git.backend.catan.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.hibernate.annotations.DynamicInsert;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity	
@Table(name= "hexagon")
@DynamicInsert
public class Hexagon {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@NotNull(message="El codi no pot ser null")
	@Column(nullable = false)
	@Min(value = 1, message = "El codi ha de ser major o igual a 1")
	private Integer codi;
	
	@NotNull(message="El terreny no pot ser null")
	@Enumerated(EnumType.STRING)
	private TipusTerreny terreny;

	@Min(value = 2, message="No pot ser menys de 2")
	@Max(value = 12, message="No pot ser mes de 12")
	@Column(nullable = true)
	private Integer numero;
	
	@NotNull(message="La coordenada q no pot ser null")
	@Column
	private Integer q;
	
	@NotNull(message="La coordenada r no pot ser null")
	@Column
	private Integer r;
	
	@OneToMany(mappedBy = "hexagon", cascade = CascadeType.ALL, orphanRemoval = true)
	@Size(min = 6, max = 6, message = "Un hexàgon ha de tenir exactament 6 vèrtexs")
	private List<HexagonVertex> hexagonVertices = new ArrayList<>();
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "partida_hexagon_id", nullable = false)
	private Partida partida;
	
	public Hexagon() {
		
	}	

	public Hexagon(@NotNull(message = "El codi no pot ser null") Integer codi,
			@NotNull(message = "El terreny no pot ser null") TipusTerreny terreny,
			@Min(value = 2, message = "No pot ser menys de 2") @Max(value = 12, message = "No pot ser mes de 12") Integer numero,
			@NotNull(message = "La coordenada q no pot ser null") @Min(value = 0, message = "No pot ser negatiu") Integer q,
			@NotNull(message = "La coordenada r no pot ser null") @Min(value = 0, message = "No pot ser negatiu") Integer r,
			@Size(min = 6, max = 6, message = "Un hexàgon ha de tenir exactament 6 vèrtexs") List<HexagonVertex> hexagonVertices) {
		super();
		this.codi = codi;
		this.terreny = terreny;
		this.numero = numero;
		this.q = q;
		this.r = r;
		this.hexagonVertices = hexagonVertices;
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
	
	public TipusTerreny getTerreny() {
		return terreny;
	}
	public void setTerreny(TipusTerreny terreny) {
		this.terreny = terreny;
	}
	
	public Integer getNumero() { 
		return numero;
	}
	
	public void setNumero(Integer numero) { 
		this.numero = numero; 
	}
	
	public Integer getQ() { 
		return q; 
	}
	
	public void setQ(Integer q) { 
		this.q = q;
	}
	
	public Integer getR() { 
		return r; 
	}
	
	
	public void setR(Integer r) { 
		this.r = r; 
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
		return "Hexagon [id=" + id + ", codi=" + codi + ", terreny=" + terreny + ", numero=" + numero + ", q=" + q + ", r=" + r + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(codi, id, numero, q, r, terreny);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null || getClass() != obj.getClass()) return false;
		Hexagon other = (Hexagon) obj;
		return Objects.equals(codi, other.codi) && Objects.equals(id, other.id) && 
		       Objects.equals(numero, other.numero) && Objects.equals(q, other.q) && 
		       Objects.equals(r, other.r) && terreny == other.terreny;
	}
}