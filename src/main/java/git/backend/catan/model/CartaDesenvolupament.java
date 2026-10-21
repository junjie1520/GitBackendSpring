package git.backend.catan.model;

import java.util.Objects;

import org.hibernate.annotations.DynamicInsert;

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
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "cartaDesenvolupament")
@DynamicInsert
@NamedQuery(name = "CartaDesenvolupament.comptarPerTipus",
query = "SELECT COUNT(c) FROM CartaDesenvolupament c WHERE c.jugador.id = :id AND c.tipusCarta = :tipus")
public class CartaDesenvolupament {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@NotNull(message = "El tipus de carta no pot ser null")
	@Enumerated(EnumType.STRING)
	private TipusCarta tipusCarta;
	
	@Column(nullable = false)
	private boolean usada;
	
	@Min(value = 0, message = "La ronda de compra no pot ser negatiu")
	@Column(nullable = true)
	private Integer rondaCompra;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = true)
	@JoinColumn(name = "partida_carta_id", nullable = true)
	private Partida partida;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = true)
	@JoinColumn(name = "jugador_carta_id", nullable = true)
	private Jugador jugador;
	
	public CartaDesenvolupament() {
	}

	public CartaDesenvolupament(@NotNull(message = "El tipus de carta no pot ser null") TipusCarta tipusCarta,
			boolean usada,
			@NotNull(message = "La ronda de compra no pot ser null") @Min(value = 0, message = "La ronda de compra no pot ser negatiu") Integer rondaCompra,
			Partida partida, Jugador jugador) {
		super();
		this.tipusCarta = tipusCarta;
		this.usada = usada;
		this.rondaCompra = rondaCompra;
		this.partida = partida;
		this.jugador = jugador;
	}

	public CartaDesenvolupament(@NotNull(message = "El tipus de carta no pot ser null") TipusCarta tipusCarta,
			@NotNull(message = "La ronda de compra no pot ser null") @Min(value = 0, message = "La ronda de compra no pot ser negatiu") Integer rondaCompra,
			Partida partida, Jugador jugador) {
		super();
		this.usada = false;
		this.tipusCarta = tipusCarta;
		this.rondaCompra = rondaCompra;
		this.partida = partida;
		this.jugador = jugador;
	}

	public TipusCarta getTipusCarta() {
		return tipusCarta;
	}

	public void setTipusCarta(TipusCarta tipusCarta) {
		this.tipusCarta = tipusCarta;
	}

	public boolean isUsada() {
		return usada;
	}

	public void setUsada(boolean usada) {
		this.usada = usada;
	}

	public Integer getRondaCompra() {
		return rondaCompra;
	}

	public void setRondaCompra(Integer rondaCompra) {
		this.rondaCompra = rondaCompra;
	}

	public Integer getId() {
		return id;
	}

	public Partida getPartida() {
		return partida;
	}

	public void setPartida(Partida partida) {
		this.partida = partida;
	}

	public Jugador getJugador() {
		return jugador;
	}

	public void setJugador(Jugador jugador) {
		this.jugador = jugador;
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
		CartaDesenvolupament other = (CartaDesenvolupament) obj;
		return id != null && Objects.equals(id, other.id);
	}

	@Override
	public String toString() {
		return "CartaDesenvolupament [id=" + id + ", tipusCarta=" + tipusCarta + ", usada=" + usada + ", rondaCompra="
				+ rondaCompra + "]";
	}
	
	
}
