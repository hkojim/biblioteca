package miyamoto.biblioteca.business.dto.in;

import java.time.LocalDate;
import java.util.Set;

public class AutorRequestDTOFixture {

    public static AutorRequestDTO.AutorRequestDTOBuilder umRequest() {
        return AutorRequestDTO.builder()
                .nome("Machado de Assis")
                .sexo("Masculino")
                .email("machado@academia.org.br")
                .dataNascimento(LocalDate.of(1839, 6, 21))
                .paisOrigem("Brasil")
                .cpf("12345678901")
                .obrasIds(Set.of(10L, 11L));
    }

    public static AutorRequestDTO.AutorRequestDTOBuilder umAutorEstrangeiro() {
        return AutorRequestDTO.builder()
                .nome("Gabriel García Márquez")
                .sexo("Masculino")
                .email("gabo@macondo.co")
                .dataNascimento(LocalDate.of(1927, 3, 6))
                .paisOrigem("Colômbia")
                .cpf(null) // Estrangeiros podem não ter CPF
                .obrasIds(Set.of(2L));
    }
}