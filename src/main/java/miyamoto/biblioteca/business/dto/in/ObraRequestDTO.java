package miyamoto.biblioteca.business.dto.in;

import lombok.*;

import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
//DTO de entrada para cadastro de obra.
public class ObraRequestDTO {
    private String nome;
    private String descricao;
    private LocalDate dataPublicacao;
    private LocalDate dataExposicao;

    private Set<Long> autoresIds;
}