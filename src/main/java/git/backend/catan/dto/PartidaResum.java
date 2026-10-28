package git.backend.catan.dto;

import java.util.ArrayList;
import java.util.List;

import git.backend.catan.model.EstatPartida;
import git.backend.catan.model.Hexagon;
import git.backend.catan.model.Jugador;
import git.backend.catan.model.Partida;

public record PartidaResum(Integer id, EstatPartida estat, Integer puntsPerGuanyar
		, boolean dausTirats, boolean lladrePendent, List<JugadorResum> jugadors
		, JugadorResum actiu, List<HexagonResum> hexagons, HexagonResum lladre) {
	public static PartidaResum de(Partida p) {
		List<JugadorResum> jr = new ArrayList<>();
		for (Jugador j : p.getJugadors()) {
			jr.add(JugadorResum.de(j));
		}
		List<HexagonResum> hr = new ArrayList<>();
		for (Hexagon h : p.getTauler()) {
			hr.add(HexagonResum.de(h));
		}
		return new PartidaResum(p.getId(), p.getEstatPartida(), p.getPuntsPerGuanyar()
				, p.isDausTirats(), p.isLladrePendent(), jr, JugadorResum.de(p.getActiu())
				, hr, HexagonResum.de(p.getLladre()));
	}
}
