package git.backend.catan.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.hibernate.annotations.DynamicInsert;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "jugador")
@DynamicInsert
public class Jugador {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Size(max = 50, message = "El nom no pot superar de 50 caracters")
	@NotBlank(message = "El nom no pot ser buit")
	@Column(nullable = false, length = 50)
	private String nom;

	@NotNull(message = "El color no pot ser null")
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private ColorJugador color;

	@Min(value = 1, message = "L'ordre ha de ser com a minim 1")
	@Max(value = 4, message = "L'ordre ha de ser com a maxim 4")
	@NotNull(message = "L'ordre de torn no pot ser null")
	@Column(nullable = false)
	private Integer ordreTorn;

	@Min(value = 0, message = "Els cavallers jugats no poden ser negatius")
	@NotNull(message = "Els cavallers jugats no poden ser null")
	@Column(nullable = false)
	private Integer cavallersJugats;

	@OneToMany(mappedBy = "jugador", cascade = CascadeType.ALL, orphanRemoval = true)
	@Size(min = 5, max = 5, message = "L'inventari ha de tenir exactament 5 recursos")
	private List<RecursJugador> inventari = new ArrayList<>();

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "partida_jugador_id", nullable = false)
	private Partida partida;
	
	@OneToMany(mappedBy = "jugador", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<CartaDesenvolupament> ma = new ArrayList<>();
	
	@OneToMany(mappedBy = "propietari")
	private List<Construccio> construccions = new ArrayList<>();

	public Jugador() {
	}

	public Integer getId() {
		return id;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public ColorJugador getColor() {
		return color;
	}

	public void setColor(ColorJugador color) {
		this.color = color;
	}

	public Integer getOrdreTorn() {
		return ordreTorn;
	}

	public void setOrdreTorn(Integer ordreTorn) {
		this.ordreTorn = ordreTorn;
	}

	public Integer getCavallersJugats() {
		return cavallersJugats;
	}

	public void setCavallersJugats(Integer cavallersJugats) {
		this.cavallersJugats = cavallersJugats;
	}

	public List<RecursJugador> getInventari() {
		return inventari;
	}

	public void setInventari(List<RecursJugador> inventari) {
		this.inventari = inventari;
	}

	public Partida getPartida() {
		return partida;
	}

	public void setPartida(Partida partida) {
		this.partida = partida;
	}

	public void afegirRecurs(RecursJugador rj) {
		if (this.inventari.size() < 5) {
			this.inventari.add(rj);
			rj.setJugador(this);
		} else {
			System.out.println("El jugador no pot tindre més de 5 recursos");
		}
	}

	public List<CartaDesenvolupament> getMa() {
		return ma;
	}

	public void setMa(List<CartaDesenvolupament> ma) {
		this.ma = ma;
	}
	
	public void afegirMa(CartaDesenvolupament cd) {
		this.ma.add(cd);
		cd.setJugador(this);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass())
			return false;
		Jugador other = (Jugador) obj;
		return id != null && Objects.equals(id, other.id);
	}

	@Override
	public String toString() {
		return "Jugador [id=" + id + ", nom=" + nom + ", color=" + color + ", ordreTorn=" + ordreTorn
				+ ", cavallersJugats=" + cavallersJugats + "]";
	}

	public List<Construccio> getConstruccions() {
		return construccions;
	}

	public void setConstruccions(List<Construccio> construccions) {
		this.construccions = construccions;
	}

	public void afegirConstruccio(Construccio c) {
		this.construccions.add(c);
		c.setJugador(this);
	}
}
