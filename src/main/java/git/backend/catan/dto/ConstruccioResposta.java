package git.backend.catan.dto;

import git.backend.catan.model.Assentament;
import git.backend.catan.model.Ciutat;
import git.backend.catan.model.ColorJugador;

public record ConstruccioResposta(Integer posicio, String tipus, String jugador, ColorJugador color) {

	public static ConstruccioResposta de(Assentament a, Integer posicio) {
		String tipus = "POBLAT";
		if (a instanceof Ciutat) {
			tipus = "CIUTAT";
		}
		return new ConstruccioResposta(posicio, tipus, a.getJugador().getNom(), a.getJugador().getColor());
	}

}