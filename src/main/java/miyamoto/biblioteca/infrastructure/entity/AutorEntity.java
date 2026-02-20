package miyamoto.biblioteca.infrastructure.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

//Entidade responsável por representar a tabela AUTOR no banco de dados.
@Entity
@Table(name = "autores",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "email"),
                @UniqueConstraint(columnNames = "cpf")
        })
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class AutorEntity {

    // Identificador único do autor (Primary Key)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Nome obrigatório
    @Column(nullable = false)
    private String nome;

    // Sexo do autor (opcional)
    private String sexo;

    // Email obrigatório e único
    @Column(nullable = false, unique = true)
    private String email;

    // Data de nascimento validada no service
    private LocalDate dataNascimento;

    // País de origem obrigatório
    @Column(nullable = false)
    private String paisOrigem;

    // CPF obrigatório apenas se país for Brasil
    @Column(unique = true)
    private String cpf;


    //Relacionamento ManyToMany com Obra.
    //Um autor pode ter várias obras.

    @ManyToMany(mappedBy = "autores")
    private Set<ObraEntity> obras = new HashSet<>();
}