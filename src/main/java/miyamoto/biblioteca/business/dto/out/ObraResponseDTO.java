package miyamoto.biblioteca.business.dto.out;

import lombok.*;

import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
//DTO de saída para retorno de obra.
public class ObraResponseDTO {
    private Long id;
    private String nome;
    private String descricao;
    private LocalDate dataPublicacao;
    private LocalDate dataExposicao;
    private Set<Long> autoresIds;
}