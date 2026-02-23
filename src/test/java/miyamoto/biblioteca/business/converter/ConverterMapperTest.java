package miyamoto.biblioteca.business.converter;

import miyamoto.biblioteca.business.dto.in.AutorRequestDTO;
import miyamoto.biblioteca.business.dto.out.AutorResponseDTO;
import miyamoto.biblioteca.business.dto.out.ObraResponseDTO;
import miyamoto.biblioteca.infrastructure.entity.AutorEntity;
import miyamoto.biblioteca.infrastructure.entity.AutorEntityFixture;
import miyamoto.biblioteca.infrastructure.entity.ObraEntity;
import miyamoto.biblioteca.infrastructure.entity.ObraEntityFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


public class ConverterMapperTest {

    // Instancia a implementação gerada pelo MapStruct
    private final ConverterMapper mapper = Mappers.getMapper(ConverterMapper.class);

    @Test
    @DisplayName("Deve converter AutorEntity para AutorResponseDTO com sucesso")
    void deveConverterAutorEntityParaAutorResponseDTO() {
        // GIVEN
        AutorEntity autorEntity = AutorEntityFixture.umaEntity().build();

        // WHEN
        AutorResponseDTO response = mapper.toAutorResponse(autorEntity);

        // THEN
        assertThat(response).isNotNull();
        assertThat(response.getId()).isEqualTo(autorEntity.getId());
        assertThat(response.getNome()).isEqualTo(autorEntity.getNome());
        assertThat(response.getEmail()).isEqualTo(autorEntity.getEmail());
        assertThat(response.getDataNascimento()).isEqualTo(autorEntity.getDataNascimento());
        assertThat(response.getPaisOrigem()).isEqualTo(autorEntity.getPaisOrigem());
    }

    @Test
    @DisplayName("Deve converter ObraEntity para ObraResponseDTO com sucesso")
    void deveConverterObraEntityParaObraResponseDTO() {
        // GIVEN
        ObraEntity obraEntity = ObraEntityFixture.umaObraEntity().build();

        // WHEN
        ObraResponseDTO response = mapper.toObraResponse(obraEntity);

        // THEN
        assertThat(response).isNotNull();
        assertThat(response.getId()).isEqualTo(obraEntity.getId());
        assertThat(response.getNome()).isEqualTo(obraEntity.getNome());
        assertThat(response.getDescricao()).isEqualTo(obraEntity.getDescricao());
        assertThat(response.getDataPublicacao()).isEqualTo(obraEntity.getDataPublicacao());
        assertThat(response.getDataExposicao()).isEqualTo(obraEntity.getDataExposicao());
    }

    @Test
    @DisplayName("Deve converter AutorEntity para AutorRequestDTO (Reverse Mapping)")
    void deveConverterAutorEntityParaAutorRequestDTO() {
        // GIVEN
        AutorEntity autorEntity = AutorEntityFixture.umaEntity().build();

        // WHEN
        AutorRequestDTO request = mapper.toAutorRequest(autorEntity);

        // THEN
        assertThat(request.getNome()).isEqualTo(autorEntity.getNome());
        assertThat(request.getCpf()).isEqualTo(autorEntity.getCpf());
        assertThat(request.getEmail()).isEqualTo(autorEntity.getEmail());
    }
}