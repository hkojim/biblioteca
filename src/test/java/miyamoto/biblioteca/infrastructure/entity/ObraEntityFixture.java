package miyamoto.biblioteca.infrastructure.entity;

import java.time.LocalDate;
import java.util.HashSet;

public class ObraEntityFixture {
    public static ObraEntity.ObraEntityBuilder umaObraEntity() {
        return ObraEntity.builder()
                .id(10L)
                .nome("Dom Casmurro")
                .descricao("Uma das obras mais famosas da literatura brasileira, explorando temas de ciúme e ambiguidade.")
                .dataPublicacao(LocalDate.of(1899, 1, 1))
                .dataExposicao(null) // Exemplo: Obra literária não tem exposição
                .autores(new HashSet<>()); // Pode ser preenchido com AutorEntity nos testes
    }
}
