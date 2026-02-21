package miyamoto.biblioteca.business;

import lombok.RequiredArgsConstructor;
import miyamoto.biblioteca.business.converter.ConverterMapper;
import miyamoto.biblioteca.business.dto.in.ObraRequestDTO;
import miyamoto.biblioteca.business.dto.out.ObraResponseDTO;
import miyamoto.biblioteca.infrastructure.entity.AutorEntity;
import miyamoto.biblioteca.infrastructure.entity.ObraEntity;
import miyamoto.biblioteca.infrastructure.repository.AutorRepository;
import miyamoto.biblioteca.infrastructure.repository.ObraRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

//Camada de regras de negócio de Obra.
@Service
@RequiredArgsConstructor
public class ObraService {
    private final ObraRepository obraRepository;
    private final AutorRepository autorRepository;
    private final ConverterMapper mapper;

    // POST
    public ObraResponseDTO criar(ObraRequestDTO dto) {

        ObraEntity obra = ObraEntity.builder()
                .nome(dto.getNome())
                .descricao(dto.getDescricao())
                .dataPublicacao(dto.getDataPublicacao())
                .dataExposicao(dto.getDataExposicao())
                .build();

        if (dto.getAutoresIds() != null) {
            Set<AutorEntity> autores =
                    autorRepository.findAllById(dto.getAutoresIds())
                            .stream()
                            .collect(Collectors.toSet());

            obra.setAutores(autores);
        }

        return mapper.toObraResponse(obraRepository.save(obra));
    }

    // GET LIST
    public List<ObraResponseDTO> listar() {
        return obraRepository.findAll()
                .stream()
                .map(mapper::toObraResponse)
                .toList();
    }

    //GET POR ID
    public ObraResponseDTO buscar(Long id) {
        ObraEntity obra = obraRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Obra não encontrada"));
        return mapper.toObraResponse(obra);
    }

    // PATCH
    public ObraResponseDTO atualizar(Long id, ObraRequestDTO dto) {

        ObraEntity obra = obraRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Obra não encontrada"));

        if (dto.getNome() != null)
            obra.setNome(dto.getNome());

        if (dto.getDescricao() != null)
            obra.setDescricao(dto.getDescricao());

        if (dto.getDataPublicacao() != null)
            obra.setDataPublicacao(dto.getDataPublicacao());

        if (dto.getDataExposicao() != null)
            obra.setDataExposicao(dto.getDataExposicao());

        return mapper.toObraResponse(obraRepository.save(obra));
    }

    // DELETE
    public void deletar(Long id) {
        obraRepository.deleteById(id);
    }

}
