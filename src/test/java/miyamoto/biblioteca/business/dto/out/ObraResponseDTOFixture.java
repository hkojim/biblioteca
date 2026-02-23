package miyamoto.biblioteca.business.dto.out;

import java.time.LocalDate;
import java.util.Set;

public class ObraResponseDTOFixture {
    public static ObraResponseDTO.ObraResponseDTOBuilder umaResponse() {
        return ObraResponseDTO.builder()
                .id(10L)
                .nome("Dom Casmurro")
                .descricao("Uma das obras mais famosas da literatura brasileira, explorando temas de ciúme e ambiguidade.")
                .dataPublicacao(LocalDate.of(1899, 1, 1))
                .dataExposicao(null)
                .autoresIds(Set.of(1L));
    }
}
