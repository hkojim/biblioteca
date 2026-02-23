package miyamoto.biblioteca.infrastructure.entity;

import java.time.LocalDate;
import java.util.HashSet;

public class AutorEntityFixture {
    public static AutorEntity.AutorEntityBuilder umaEntity() {
        return AutorEntity.builder()
                .id(1L)
                .nome("Machado de Assis")
                .sexo("Masculino")
                .email("machado@academia.org.br")
                .dataNascimento(LocalDate.of(1839, 6, 21))
                .paisOrigem("Brasil")
                .cpf("12345678901")
                .obras(new HashSet<>());
    }
}
