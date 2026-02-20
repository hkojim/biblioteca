package miyamoto.biblioteca.business.converter;

import miyamoto.biblioteca.business.dto.in.AutorRequestDTO;
import miyamoto.biblioteca.business.dto.in.ObraRequestDTO;
import miyamoto.biblioteca.business.dto.out.AutorResponseDTO;
import miyamoto.biblioteca.business.dto.out.ObraResponseDTO;
import miyamoto.biblioteca.infrastructure.entity.AutorEntity;
import miyamoto.biblioteca.infrastructure.entity.ObraEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")

public interface ConverterMapper {
    // Converte AutorEntity → AutorResponseDTO
    AutorResponseDTO toAutorResponse(AutorEntity autor);
    // Converte ObraEntity → ObraResponseDTO
    ObraResponseDTO toObraResponse(ObraEntity obra);

    AutorRequestDTO toAutorRequest(AutorEntity autor);
    ObraRequestDTO toObraRequest(ObraEntity obra);
}
