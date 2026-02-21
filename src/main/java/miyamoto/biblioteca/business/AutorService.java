package miyamoto.biblioteca.business;

import lombok.RequiredArgsConstructor;
import miyamoto.biblioteca.business.converter.ConverterMapper;
import miyamoto.biblioteca.business.dto.in.AutorRequestDTO;
import miyamoto.biblioteca.business.dto.in.ObraRequestDTO;
import miyamoto.biblioteca.business.dto.out.AutorResponseDTO;
import miyamoto.biblioteca.business.dto.out.ObraResponseDTO;
import miyamoto.biblioteca.infrastructure.entity.AutorEntity;
import miyamoto.biblioteca.infrastructure.entity.ObraEntity;
import miyamoto.biblioteca.infrastructure.repository.AutorRepository;
import miyamoto.biblioteca.infrastructure.repository.ObraRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

//Camada de regra de negócio.

@Service
@RequiredArgsConstructor

public class AutorService {
    private final AutorRepository autorRepository;
    private final ObraRepository obraRepository;
    private final ConverterMapper mapper;

    //POST
    public AutorResponseDTO criarAutor(AutorRequestDTO dto) {

        if (autorRepository.existsByEmail(dto.getEmail())) {
            throw new RuntimeException("Email já cadastrado");
        }

        if ("Brasil".equalsIgnoreCase(dto.getPaisOrigem()) && dto.getCpf() == null) {
            throw new RuntimeException("CPF obrigatório para autores brasileiros");
        }

        AutorEntity autor = AutorEntity.builder()
                .nome(dto.getNome())
                .sexo(dto.getSexo())
                .email(dto.getEmail())
                .dataNascimento(dto.getDataNascimento())
                .paisOrigem(dto.getPaisOrigem())
                .cpf(dto.getCpf())
                .build();

        if (dto.getObrasIds() != null) {
            Set<ObraEntity> obras = obraRepository.findAllById(dto.getObrasIds())
                    .stream()
                    .collect(Collectors.toSet());
            autor.setObras(obras);
        }
        return mapper.toAutorResponse(autorRepository.save(autor));
    }

    //GET
    public List<AutorResponseDTO> listar() {
        return autorRepository.findAll()
                .stream()
                .map(mapper::toAutorResponse)
                .toList();
    }

    // GET POR ID
    public AutorResponseDTO buscar(Long id) {
        AutorEntity autor = autorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Autor não encontrado"));
        return mapper.toAutorResponse(autor);
    }

    // PATCH (atualização parcial)
    public AutorResponseDTO atualizar(Long id, AutorRequestDTO dto) {

        AutorEntity autor = autorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Autor não encontrado"));

        if (dto.getNome() != null)
            autor.setNome(dto.getNome());

        if (dto.getEmail() != null)
            autor.setEmail(dto.getEmail());

        if (dto.getSexo() != null)
            autor.setSexo(dto.getSexo());

        if (dto.getPaisOrigem() != null)
            autor.setPaisOrigem(dto.getPaisOrigem());

        if (dto.getCpf() != null)
            autor.setCpf(dto.getCpf());
        return mapper.toAutorResponse(autorRepository.save(autor));
    }

    // DELETE
    public void deletar(Long id) {
        autorRepository.deleteById(id);
    }
}