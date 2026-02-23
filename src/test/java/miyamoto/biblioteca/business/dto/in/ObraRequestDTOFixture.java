package miyamoto.biblioteca.business.dto.in;

import java.time.LocalDate;
import java.util.Set;

public class ObraRequestDTOFixture {
    public static ObraRequestDTO.ObraRequestDTOBuilder umRequest() {
    return ObraRequestDTO.builder()
            .nome("Dom Casmurro")
            .descricao("Uma das obras mais famosas da literatura brasileira, explorando temas de ciúme e ambiguidade.")
            .dataPublicacao(LocalDate.of(1899, 1, 1))
            .dataExposicao(null)
            .autoresIds(Set.of(1L)); // ID do autor (ex: Machado de Assis)
}
}


