package miyamoto.biblioteca.business;

import miyamoto.biblioteca.business.converter.ConverterMapper;
import miyamoto.biblioteca.business.dto.in.AutorRequestDTO;
import miyamoto.biblioteca.business.dto.in.AutorRequestDTOFixture;
import miyamoto.biblioteca.business.dto.out.AutorResponseDTO;
import miyamoto.biblioteca.business.dto.out.AutorResponseDTOFixture;
import miyamoto.biblioteca.infrastructure.entity.AutorEntity;
import miyamoto.biblioteca.infrastructure.entity.AutorEntityFixture;
import miyamoto.biblioteca.infrastructure.repository.AutorRepository;
import miyamoto.biblioteca.infrastructure.repository.ObraRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AutorServiceTest {
    @Mock
    private AutorRepository autorRepository;

    @Mock
    private ObraRepository obraRepository;

    @Mock
    private ConverterMapper mapper;

    @InjectMocks
    private AutorService autorService;

    // TESTES DO CRIAR (POST)
    @Test
    @DisplayName("Deve criar um autor com sucesso")
    void deveCriarAutorComSucesso() {
        // GIVEN
        AutorRequestDTO request = AutorRequestDTOFixture.umRequest().build();
        AutorEntity autorSalvo = AutorEntityFixture.umaEntity().build();
        AutorResponseDTO responseEsperado = AutorResponseDTOFixture.umaResponse().build();

        when(autorRepository.existsByEmail(request.getEmail())).thenReturn(false);
        when(autorRepository.save(any(AutorEntity.class))).thenReturn(autorSalvo);
        when(mapper.toAutorResponse(autorSalvo)).thenReturn(responseEsperado);

        // WHEN
        AutorResponseDTO resultado = autorService.criarAutor(request);

        // THEN
        assertNotNull(resultado);
        assertEquals(responseEsperado.getNome(), resultado.getNome());
        verify(autorRepository).save(any(AutorEntity.class));
    }

    @Test
    @DisplayName("Deve lançar exceção quando e-mail já existe")
    void deveLancarExcecaoEmailJaCadastrado() {
        AutorRequestDTO request = AutorRequestDTOFixture.umRequest().build();
        when(autorRepository.existsByEmail(request.getEmail())).thenReturn(true);

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                autorService.criarAutor(request)
        );

        assertEquals("Email já cadastrado", exception.getMessage());
        verify(autorRepository, never()).save(any());
    }

    @Test
    @DisplayName("Deve lançar exceção quando autor é brasileiro e não possui CPF")
    void deveLancarExcecaoCpfObrigatorioParaBrasil() {
        AutorRequestDTO request = AutorRequestDTOFixture.umRequest()
                .paisOrigem("Brasil")
                .cpf(null)
                .build();

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                autorService.criarAutor(request)
        );

        assertEquals("CPF obrigatório para autores brasileiros", exception.getMessage());
    }

    //TESTES DO BUSCAR (GET)

    @Test
    @DisplayName("Deve buscar autor por ID com sucesso")
    void deveBuscarAutorPorId() {
        Long id = 1L;
        AutorEntity autor = AutorEntityFixture.umaEntity().build();
        AutorResponseDTO response = AutorResponseDTOFixture.umaResponse().build();

        when(autorRepository.findById(id)).thenReturn(Optional.of(autor));
        when(mapper.toAutorResponse(autor)).thenReturn(response);

        AutorResponseDTO resultado = autorService.buscar(id);

        assertEquals(response.getNome(), resultado.getNome());
    }

    @Test
    @DisplayName("Deve lançar exceção ao buscar ID inexistente")
    void deveLancarExcecaoAutorNaoEncontrado() {
        when(autorRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> autorService.buscar(99L));
    }

    //TESTES DO ATUALIZAR (PATCH)

    @Test
    @DisplayName("Deve atualizar apenas o nome do autor")
    void deveAtualizarApenasNome() {
        // GIVEN
        Long id = 1L;
        AutorEntity autorExistente = AutorEntityFixture.umaEntity().build();
        AutorRequestDTO requestComNovoNome = AutorRequestDTO.builder().nome("Novo Nome").build();

        when(autorRepository.findById(id)).thenReturn(Optional.of(autorExistente));
        when(autorRepository.save(autorExistente)).thenReturn(autorExistente);
        // O mapper apenas devolve o que o repository salvou
        when(mapper.toAutorResponse(autorExistente)).thenReturn(AutorResponseDTO.builder().nome("Novo Nome").build());

        // WHEN
        AutorResponseDTO resultado = autorService.atualizar(id, requestComNovoNome);

        // THEN
        assertEquals("Novo Nome", resultado.getNome());
        verify(autorRepository).save(autorExistente);
    }

    //TESTES DO DELETAR

    @Test
    @DisplayName("Deve chamar o deleteById do repositório")
    void deveDeletarAutor() {
        Long id = 1L;
        doNothing().when(autorRepository).deleteById(id);

        autorService.deletar(id);

        verify(autorRepository, times(1)).deleteById(id);
    }
}
