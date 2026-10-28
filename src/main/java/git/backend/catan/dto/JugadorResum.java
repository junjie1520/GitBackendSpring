package git.backend.catan.dto;

import git.backend.catan.model.ColorJugador;
import git.backend.catan.model.Jugador;

public record JugadorResum(Integer id, String nom, ColorJugador color, Integer ordreTorn) {

	public static JugadorResum de(Jugador j) {
		return new JugadorResum(j.getId(), j.getNom(), j.getColor(), j.getOrdreTorn());
	}

}