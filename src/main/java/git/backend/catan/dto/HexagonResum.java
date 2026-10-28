package git.backend.catan.dto;

import git.backend.catan.model.Hexagon;
import git.backend.catan.model.TipusTerreny;

public record HexagonResum(Integer id, Integer codi, TipusTerreny terreny, Integer numero) {

	public static HexagonResum de(Hexagon h) {
		return new HexagonResum(h.getId(), h.getCodi(), h.getTerreny(), h.getNumero());
	}

}
