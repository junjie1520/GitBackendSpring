package git.backend.catan.model;

import java.util.Objects;

import org.hibernate.annotations.DynamicInsert;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Entity
@DynamicInsert
@Table(
		name = "recursJugador",
		uniqueConstraints = @UniqueConstraint(
        columnNames = {"jugador_id", "tipusRecurs"}
    )
)
@NamedQuery(name = "RecursJugador.perJugador",
query = "SELECT r FROM RecursJugador r WHERE r.jugador.id = :id")
public class RecursJugador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "El jugador no pot ser null")
    @ManyToOne
    @JoinColumn(name = "jugador_id", nullable = false)
    private Jugador jugador;

    @NotNull(message = "El tipus de recurs no pot ser null")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipusRecurs tipusRecurs;

    @NotNull(message = "La quantitat no pot ser null")
    @Min(value = 0, message = "La quantitat no pot ser negativa")
    @Column(nullable = false)
    private Integer quantitat;

	public Jugador getJugador() {
		return jugador;
	}

	public void setJugador(Jugador jugador) {
		this.jugador = jugador;
	}

	public TipusRecurs getTipusRecurs() {
		return tipusRecurs;
	}

	public void setTipusRecurs(TipusRecurs tipusRecurs) {
		this.tipusRecurs = tipusRecurs;
	}

	public Integer getQuantitat() {
		return quantitat;
	}

	public void setQuantitat(Integer quantitat) {
		this.quantitat = quantitat;
	}

	public Integer getId() {
		return id;
	}
	
	public RecursJugador() {
	}
	
	public RecursJugador(Jugador jugador, TipusRecurs tipusRecurs, Integer quantitat) {
		super();
		this.jugador = jugador;
		this.tipusRecurs = tipusRecurs;
		this.quantitat = quantitat;
	}

	@Override
	public String toString() {
		return "RecursJugador [id=" + id + ", tipusRecurs=" + tipusRecurs + ", quantitat=" + quantitat + "]";
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
		RecursJugador other = (RecursJugador) obj;
		return id != null && Objects.equals(id, other.id);
	}
    
    
}