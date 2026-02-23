package miyamoto.biblioteca.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import miyamoto.biblioteca.business.ObraService;
import miyamoto.biblioteca.business.dto.in.ObraRequestDTO;
import miyamoto.biblioteca.business.dto.in.ObraRequestDTOFixture;
import miyamoto.biblioteca.business.dto.out.ObraResponseDTO;
import miyamoto.biblioteca.business.dto.out.ObraResponseDTOFixture;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ObraController.class)
@AutoConfigureMockMvc(addFilters = false)
public class ObraControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private ObraService obraService;

    @Test
    @DisplayName("POST - Deve criar uma obra e retornar 200")
    void deveCriarObra() throws Exception {
        // GIVEN
        ObraRequestDTO request = ObraRequestDTOFixture.umRequest().build();
        ObraResponseDTO response = ObraResponseDTOFixture.umaResponse().build();

        when(obraService.criar(any(ObraRequestDTO.class))).thenReturn(response);

        // WHEN & THEN
        mockMvc.perform(post("/obras")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value(response.getNome()))
                .andExpect(jsonPath("$.descricao").value(response.getDescricao()));
    }

    @Test
    @DisplayName("GET - Deve listar todas as obras")
    void deveListarObras() throws Exception {
        // GIVEN
        ObraResponseDTO response = ObraResponseDTOFixture.umaResponse().build();
        when(obraService.listar()).thenReturn(List.of(response));

        // WHEN & THEN
        mockMvc.perform(get("/obras"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nome").value(response.getNome()))
                .andExpect(jsonPath("$.size()").value(1));
    }

    @Test
    @DisplayName("GET /{id} - Deve retornar obra por ID")
    void deveBuscarObraPorId() throws Exception {
        // GIVEN
        Long id = 10L;
        ObraResponseDTO response = ObraResponseDTOFixture.umaResponse().build();
        when(obraService.buscar(id)).thenReturn(response);

        // WHEN & THEN
        mockMvc.perform(get("/obras/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.nome").value(response.getNome()));
    }

    @Test
    @DisplayName("PATCH - Deve atualizar obra parcialmente")
    void deveAtualizarObra() throws Exception {
        // GIVEN
        Long id = 10L;
        ObraRequestDTO request = ObraRequestDTO.builder().nome("Novo Título").build();
        ObraResponseDTO response = ObraResponseDTOFixture.umaResponse().nome("Novo Título").build();

        when(obraService.atualizar(eq(id), any(ObraRequestDTO.class))).thenReturn(response);

        // WHEN & THEN
        mockMvc.perform(patch("/obras/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Novo Título"));
    }

    @Test
    @DisplayName("DELETE - Deve remover obra com sucesso")
    void deveDeletarObra() throws Exception {
        // GIVEN
        Long id = 10L;
        doNothing().when(obraService).deletar(id);

        // WHEN & THEN
        mockMvc.perform(delete("/obras/{id}", id))
                .andExpect(status().isOk());
    }
}
