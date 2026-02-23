package miyamoto.biblioteca.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import miyamoto.biblioteca.business.AutorService;
import miyamoto.biblioteca.business.dto.in.AutorRequestDTO;
import miyamoto.biblioteca.business.dto.in.AutorRequestDTOFixture;
import miyamoto.biblioteca.business.dto.out.AutorResponseDTO;
import miyamoto.biblioteca.business.dto.out.AutorResponseDTOFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AutorController.class)
@AutoConfigureMockMvc(addFilters = false)
public class AutorControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper; // Para converter objetos em JSON

    @MockitoBean
    private AutorService autorService;

    @Test
    @DisplayName("POST - Deve retornar 201 e o autor criado")
    void deveCriarAutor() throws Exception {
        AutorRequestDTO request = AutorRequestDTOFixture.umRequest().build();
        AutorResponseDTO response = AutorResponseDTOFixture.umaResponse().build();

        when(autorService.criarAutor(any(AutorRequestDTO.class))).thenReturn(response);

        mockMvc.perform(post("/autores")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))) // Fecha o perform aqui
                .andExpect(status().isOk()) // Ou .isCreated() se o seu controller retornar 201
                .andExpect(jsonPath("$.nome").value(response.getNome()))
                .andExpect(jsonPath("$.email").value(response.getEmail()));
    }

    @Test
    @DisplayName("GET - Deve listar todos os autores")
    void deveListarAutores() throws Exception {
        AutorResponseDTO response = AutorResponseDTOFixture.umaResponse().build();
        when(autorService.listar()).thenReturn(List.of(response));

        mockMvc.perform(get("/autores"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nome").value(response.getNome()))
                .andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    @DisplayName("GET /{id} - Deve buscar autor por ID")
    void deveBuscarAutorPorId() throws Exception {
        Long id = 1L;
        AutorResponseDTO response = AutorResponseDTOFixture.umaResponse().build();
        when(autorService.buscar(id)).thenReturn(response);

        mockMvc.perform(get("/autores/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.nome").value(response.getNome()));
    }

    @Test
    @DisplayName("PATCH - Deve atualizar autor parcialmente")
    void deveAtualizarAutor() throws Exception {
        Long id = 1L;
        AutorRequestDTO request = AutorRequestDTO.builder().nome("Nome Atualizado").build();
        AutorResponseDTO response = AutorResponseDTOFixture.umaResponse().nome("Nome Atualizado").build();

        when(autorService.atualizar(eq(id), any(AutorRequestDTO.class))).thenReturn(response);

        mockMvc.perform(patch("/autores/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Nome Atualizado"));
    }

    @Test
    @DisplayName("DELETE - Deve deletar autor")
    void deveDeletarAutor() throws Exception {
        Long id = 1L;
        doNothing().when(autorService).deletar(id);

        mockMvc.perform(delete("/autores/{id}", id))
                .andExpect(status().isOk());
    }
}
