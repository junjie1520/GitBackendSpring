package git.backend.catan.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

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
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;

@Entity
@Table(name = "partida")
@DynamicInsert
public class Partida {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@NotNull(message = "L'estat no pot ser null")
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private EstatPartida estatPartida;

	@NotNull(message = "Data d'inici no pot ser null")
	@PastOrPresent(message = "No pots iniciar una partida del futur")
	@Column(nullable = false)
	private LocalDate dataInici;

	@NotNull(message = "La ronda no pot ser null")
	@Min(value = 0, message = "El numero de ronda no pot ser negatiu")
	@Column(nullable = false)
	private Integer ronda;

	@NotNull(message = "L'ultima tirada no pot ser null")
	@Min(value = 0, message = "El numero de la tirada no pot ser negatiu")
	@Max(value = 12, message = "El numero de la tirada no pot superar de 12")
	@Column(nullable = false)
	private Integer ultimaTirada;

	@NotNull(message = "Punts per guanyar no pot ser null")
	@Min(value = 1, message = "Punts per guanyar ha de ser major o igual a 1")
	@Column(nullable = false)
	private Integer puntsPerGuanyar;

	@Column(nullable = false)
	private boolean dausTirats;

	@Column(nullable = false)
	private boolean lladrePendent;

	@OneToMany(mappedBy = "partida", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<Jugador> jugadors = new HashSet<>();

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "jugador_actiu_id", nullable = true)
	private Jugador actiu;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "jugador_guanyador_id", nullable = true)
	private Jugador guanyador;
	
	@OneToMany(mappedBy = "partida", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<CartaDesenvolupament> pila = new ArrayList<>();
	
	@OneToMany(mappedBy = "partida", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Hexagon> tauler = new ArrayList<>();
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "hexagon_lladre_id", nullable = true)
	private Hexagon lladre;
	
	@OneToMany(mappedBy = "partida", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Aresta> arestes = new ArrayList<>();
	
	@OneToMany(mappedBy = "partida", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Vertex> vertexs = new ArrayList<>();

	public Partida() {
	}

	public Partida(@NotNull(message = "L'estat no pot ser null") EstatPartida estatPartida,
			@NotNull(message = "Data d'inici no pot ser null") @PastOrPresent(message = "No pots iniciar una partida del futur") LocalDate dataInici,
			@NotNull(message = "La ronda no pot ser null") @Min(value = 0, message = "El numero de ronda no pot ser negatiu") Integer ronda,
			@NotNull(message = "L'ultima tirada no pot ser null") @Min(value = 0, message = "El numero de la tirada no pot ser negatiu") Integer ultimaTirada,
			@NotNull(message = "Punts per guanyar no pot ser null") @Min(value = 0, message = "Punts per guanyar no pot ser negatiu") Integer puntsPerGuanyar,
			boolean dausTirats, boolean lladrePendent, Set<Jugador> jugadors, Jugador actiu, Jugador guanyador,
			List<CartaDesenvolupament> pila) {
		super();
		this.estatPartida = estatPartida;
		this.dataInici = dataInici;
		this.ronda = ronda;
		this.ultimaTirada = ultimaTirada;
		this.puntsPerGuanyar = puntsPerGuanyar;
		this.dausTirats = dausTirats;
		this.lladrePendent = lladrePendent;
		this.jugadors = jugadors;
		this.actiu = actiu;
		this.guanyador = guanyador;
		this.pila = pila;
	}

	public Integer getId() {
		return id;
	}

	public EstatPartida getEstatPartida() {
		return estatPartida;
	}

	public void setEstatPartida(EstatPartida estatPartida) {
		this.estatPartida = estatPartida;
	}

	public LocalDate getDataInici() {
		return dataInici;
	}

	public void setDataInici(LocalDate dataInici) {
		this.dataInici = dataInici;
	}

	public Integer getRonda() {
		return ronda;
	}

	public void setRonda(Integer ronda) {
		this.ronda = ronda;
	}

	public Integer getUltimaTirada() {
		return ultimaTirada;
	}

	public void setUltimaTirada(Integer ultimaTirada) {
		this.ultimaTirada = ultimaTirada;
	}

	public Integer getPuntsPerGuanyar() {
		return puntsPerGuanyar;
	}

	public void setPuntsPerGuanyar(Integer puntsPerGuanyar) {
		this.puntsPerGuanyar = puntsPerGuanyar;
	}

	public boolean isDausTirats() {
		return dausTirats;
	}

	public void setDausTirats(boolean dausTirats) {
		this.dausTirats = dausTirats;
	}

	public boolean isLladrePendent() {
		return lladrePendent;
	}

	public void setLladrePendent(boolean lladrePendent) {
		this.lladrePendent = lladrePendent;
	}

	public Set<Jugador> getJugadors() {
		return jugadors;
	}

	public void setJugadors(Set<Jugador> jugadors) {
		this.jugadors = jugadors;
	}

	public void afegirJugador(Jugador j) {
		this.jugadors.add(j);
		j.setPartida(this);
	}

	public Jugador getActiu() {
		return actiu;
	}

	public void setActiu(Jugador actiu) {
		this.actiu = actiu;
	}

	public Jugador getGuanyador() {
		return guanyador;
	}

	public void setGuanyador(Jugador guanyador) {
		this.guanyador = guanyador;
	}
	
	public List<CartaDesenvolupament> getPila() {
		return pila;
	}

	public void setPila(List<CartaDesenvolupament> pila) {
		this.pila = pila;
	}

	public void afegirPila(CartaDesenvolupament cd) {
		this.pila.add(cd);
		cd.setPartida(this);
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
		Partida other = (Partida) obj;
		return id != null && Objects.equals(id, other.id);
	}

	@Override
	public String toString() {
		return "Partida [id=" + id + ", estatPartida=" + estatPartida + ", dataInici=" + dataInici + ", ronda=" + ronda
				+ ", ultimaTirada=" + ultimaTirada + ", puntsPerGuanyar=" + puntsPerGuanyar + ", dausTirats="
				+ dausTirats + ", lladrePendent=" + lladrePendent + "]";
	}

	public List<Hexagon> getTauler() {
		return tauler;
	}

	public void setTauler(List<Hexagon> tauler) {
		this.tauler = tauler;
	}

	public void afegirHexagon(Hexagon h) {
		this.tauler.add(h);
		h.setPartida(this);
	}

	public Hexagon getLladre() {
		return lladre;
	}

	public void setLladre(Hexagon lladre) {
		this.lladre = lladre;
	}

	public List<Vertex> getVertexs() {
		return vertexs;
	}

	public void setVertexs(List<Vertex> vertexs) {
		this.vertexs = vertexs;
	}

	public void afegirVertex(Vertex v) {
		this.vertexs.add(v);
		v.setPartida(this);
	}

	public List<Aresta> getArestes() {
		return arestes;
	}

	public void setArestes(List<Aresta> arestes) {
		this.arestes = arestes;
	}

	public void afegirAresta(Aresta a) {
		this.arestes.add(a);
		a.setPartida(this);
	}
}
