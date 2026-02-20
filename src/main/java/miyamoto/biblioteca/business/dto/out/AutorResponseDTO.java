package miyamoto.biblioteca.business.dto.out;

import lombok.*;

import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
//DTO utilizado para retornar dados ao cliente.
public class AutorResponseDTO {
    private Long id;
    private String nome;
    private String email;
    private LocalDate dataNascimento;
    private String paisOrigem;
    private Set<Long> obrasIds;
}
