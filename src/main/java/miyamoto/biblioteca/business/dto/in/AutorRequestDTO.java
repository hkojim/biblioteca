package miyamoto.biblioteca.business.dto.in;

import lombok.*;

import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
//DTO utilizado para receber dados de entrada da API.
public class AutorRequestDTO {
    private String nome;
    private String sexo;
    private String email;
    private LocalDate dataNascimento;
    private String paisOrigem;
    private String cpf;

    // Lista de IDs das obras relacionadas
    private Set<Long> obrasIds;
}