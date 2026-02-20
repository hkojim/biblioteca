package miyamoto.biblioteca.infrastructure.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

//Entidade responsável por representar a tabela OBRA no banco.
@Entity
@Table(name = "obras")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class ObraEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Nome obrigatório
    @Column(nullable = false)
    private String nome;

    // Descrição limitada a 240 caracteres
    @Column(length = 240)
    private String descricao;

    // Pelo menos uma das datas deve ser informada (validado no service)
    private LocalDate dataPublicacao;
    private LocalDate dataExposicao;

    //ManyToMany com Autor
    @ManyToMany
    @JoinTable(
            name = "autor_obra",
            joinColumns = @JoinColumn(name = "obra_id"),
            inverseJoinColumns = @JoinColumn(name = "autor_id")
    )
    private Set<AutorEntity> autores = new HashSet<>();
}
