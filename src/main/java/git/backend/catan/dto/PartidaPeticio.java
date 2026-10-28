package git.backend.catan.dto;

import java.util.List;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record PartidaPeticio(
	@NotNull(message = "Cal la llista de jugadors")
	@Size(min = 2, max = 4, message = "Hi ha d'haver entre 2 i 4 jugadors")
	List<String> nomJugadors,
	
	@Min(value = 1, message = "Els punts per guanyar han de ser com a mínim 1")
	Integer puntsPerGuanyar) {
}
