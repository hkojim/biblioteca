package miyamoto.biblioteca.business.dto.out;

import java.time.LocalDate;
import java.util.Set;

public class AutorResponseDTOFixture {
    public static AutorResponseDTO.AutorResponseDTOBuilder umaResponse() {
        return AutorResponseDTO.builder()
                .id(1L)
                .nome("Machado de Assis")
                .email("machado@academia.org.br")
                .dataNascimento(LocalDate.of(1839, 6, 21))
                .paisOrigem("Brasil")
                .obrasIds(Set.of(10L, 11L));
    }
}
