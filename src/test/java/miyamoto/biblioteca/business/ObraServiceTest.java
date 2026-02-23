package miyamoto.biblioteca.business;

import miyamoto.biblioteca.business.converter.ConverterMapper;
import miyamoto.biblioteca.business.dto.in.ObraRequestDTO;
import miyamoto.biblioteca.business.dto.in.ObraRequestDTOFixture;
import miyamoto.biblioteca.business.dto.out.ObraResponseDTO;
import miyamoto.biblioteca.business.dto.out.ObraResponseDTOFixture;
import miyamoto.biblioteca.infrastructure.entity.AutorEntity;
import miyamoto.biblioteca.infrastructure.entity.AutorEntityFixture;
import miyamoto.biblioteca.infrastructure.entity.ObraEntity;
import miyamoto.biblioteca.infrastructure.entity.ObraEntityFixture;
import miyamoto.biblioteca.infrastructure.repository.AutorRepository;
import miyamoto.biblioteca.infrastructure.repository.ObraRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ObraServiceTest {

    @Mock
    private ObraRepository obraRepository;

    @Mock
    private AutorRepository autorRepository;

    @Mock
    private ConverterMapper mapper;

    @InjectMocks
    private ObraService obraService;

    @Test
    @DisplayName("Deve criar uma obra vinculando autores com sucesso")
    void deveCriarObraComAutores() {
        // GIVEN
        ObraRequestDTO request = ObraRequestDTOFixture.umRequest().build();
        AutorEntity autor = AutorEntityFixture.umaEntity().build();
        ObraEntity obraSalva = ObraEntityFixture.umaObraEntity().build();
        ObraResponseDTO responseEsperada = ObraResponseDTOFixture.umaResponse().build();

        // Simulando a busca dos autores no banco pelo ID enviado no DTO
        when(autorRepository.findAllById(request.getAutoresIds())).thenReturn(List.of(autor));
        when(obraRepository.save(any(ObraEntity.class))).thenReturn(obraSalva);
        when(mapper.toObraResponse(obraSalva)).thenReturn(responseEsperada);

        // WHEN
        ObraResponseDTO resultado = obraService.criar(request);

        // THEN
        assertThat(resultado).isNotNull();
        assertThat(resultado.getNome()).isEqualTo(responseEsperada.getNome());
        verify(autorRepository).findAllById(request.getAutoresIds());
        verify(obraRepository).save(any(ObraEntity.class));
    }

    @Test
    @DisplayName("Deve listar todas as obras")
    void deveListarObras() {
        // GIVEN
        ObraEntity obra = ObraEntityFixture.umaObraEntity().build();
        ObraResponseDTO response = ObraResponseDTOFixture.umaResponse().build();

        when(obraRepository.findAll()).thenReturn(List.of(obra));
        when(mapper.toObraResponse(obra)).thenReturn(response);

        // WHEN
        List<ObraResponseDTO> resultado = obraService.listar();

        // THEN
        assertThat(resultado).hasSize(1);
        assertThat(resultado.get(0).getNome()).isEqualTo(response.getNome());
    }

    @Test
    @DisplayName("Deve buscar obra por ID")
    void deveBuscarPorId() {
        // GIVEN
        Long id = 10L;
        ObraEntity obra = ObraEntityFixture.umaObraEntity().build();
        ObraResponseDTO response = ObraResponseDTOFixture.umaResponse().build();

        when(obraRepository.findById(id)).thenReturn(Optional.of(obra));
        when(mapper.toObraResponse(obra)).thenReturn(response);

        // WHEN
        ObraResponseDTO resultado = obraService.buscar(id);

        // THEN
        assertThat(resultado.getNome()).isEqualTo(response.getNome());
    }

    @Test
    @DisplayName("Deve atualizar apenas a descrição da obra (PATCH)")
    void deveAtualizarApenasDescricao() {
        // GIVEN
        Long id = 10L;
        ObraEntity obraExistente = ObraEntityFixture.umaObraEntity().build();
        ObraRequestDTO requestUpdate = ObraRequestDTO.builder()
                .descricao("Nova descrição atualizada")
                .build();

        when(obraRepository.findById(id)).thenReturn(Optional.of(obraExistente));
        when(obraRepository.save(obraExistente)).thenReturn(obraExistente);

        // Mock do retorno do mapper para o DTO de resposta
        ObraResponseDTO responseAtualizado = ObraResponseDTOFixture.umaResponse()
                .descricao("Nova descrição atualizada").build();
        when(mapper.toObraResponse(obraExistente)).thenReturn(responseAtualizado);

        // WHEN
        ObraResponseDTO resultado = obraService.atualizar(id, requestUpdate);

        // THEN
        assertThat(resultado.getDescricao()).isEqualTo("Nova descrição atualizada");
        // Verifica se o nome permaneceu o mesmo da fixture original (não foi sobrescrito por null)
        assertThat(obraExistente.getNome()).isEqualTo(ObraEntityFixture.umaObraEntity().build().getNome());
        verify(obraRepository).save(obraExistente);
    }

    @Test
    @DisplayName("Deve deletar obra com sucesso")
    void deveDeletarObra() {
        Long id = 10L;

        // Execução
        obraService.deletar(id);

        // Verificação
        verify(obraRepository, times(1)).deleteById(id);
    }
}