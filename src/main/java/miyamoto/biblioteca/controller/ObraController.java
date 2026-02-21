package miyamoto.biblioteca.controller;

import lombok.RequiredArgsConstructor;
import miyamoto.biblioteca.business.ObraService;
import miyamoto.biblioteca.business.dto.in.ObraRequestDTO;
import miyamoto.biblioteca.business.dto.out.AutorResponseDTO;
import miyamoto.biblioteca.business.dto.out.ObraResponseDTO;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/obras")
@RequiredArgsConstructor

public class ObraController {
    private final ObraService service;

    @PostMapping
    public ObraResponseDTO criar(@RequestBody ObraRequestDTO dto) {
        return service.criar(dto);
    }

    @GetMapping
    public List<ObraResponseDTO> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public ObraResponseDTO buscar(@PathVariable Long id) {
        return service.buscar(id);
    }

    @PatchMapping("/{id}")
    public ObraResponseDTO atualizar(@PathVariable Long id,
                                     @RequestBody ObraRequestDTO dto) {
        return service.atualizar(id, dto);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        service.deletar(id);
    }
}
