package git.backend.catan.model;

import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "construccio")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Construccio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    public Integer getId() {
        return id;
    }

    public Construccio() {
        super();
    }

    public Construccio(Integer id) {
        super();
        this.id = id;
    }
    
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "jugador_id", nullable = false)
    private Jugador propietari;

    public Jugador getJugador() {
        return propietari;
    }

    public void setJugador(Jugador jugador) {
        this.propietari = jugador;
    }

    @Override
    public String toString() {
        return "Construccio [id=" + id + "]";
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Construccio other = (Construccio) obj;
        return Objects.equals(id, other.id);
    }
}
