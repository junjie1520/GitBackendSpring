package git.backend.catan.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import git.backend.catan.model.Assentament;
import git.backend.catan.model.Hexagon;
import git.backend.catan.model.HexagonVertex;
import git.backend.catan.model.TipusTerreny;

public record HexagonResposta(Integer id, Integer codi, TipusTerreny terreny, Integer numero,
		boolean lladre, List<ConstruccioResposta> construccions) {

	public static HexagonResposta de(Hexagon h, Integer idLladre, Map<Integer, Assentament> assentaments) {
		ArrayList<ConstruccioResposta> construccions = new ArrayList<>();
		for (HexagonVertex hv : h.getHexagonVertices()) {
			Assentament a = assentaments.get(hv.getVertex().getId());
			if (a != null) {
				construccions.add(ConstruccioResposta.de(a, hv.getPosicio()));
			}
		}
		boolean lladre = h.getId().equals(idLladre);
		return new HexagonResposta(h.getId(), h.getCodi(), h.getTerreny(), h.getNumero(), lladre, construccions);
	}

}