package miyamoto.biblioteca.controller;

import lombok.RequiredArgsConstructor;
import miyamoto.biblioteca.business.AutorService;
import miyamoto.biblioteca.business.dto.in.AutorRequestDTO;
import miyamoto.biblioteca.business.dto.out.AutorResponseDTO;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//Controller responsável pelos endpoints REST.
@RestController
@RequestMapping("/autores")
@RequiredArgsConstructor

public class AutorController {
    private final AutorService service;

    @PostMapping
    public AutorResponseDTO criarAutor(@RequestBody AutorRequestDTO dto) {
        return service.criarAutor(dto);
    }

    @GetMapping
    public List<AutorResponseDTO> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public AutorResponseDTO buscar(@PathVariable Long id) {
        return service.buscar(id);
    }

    @PatchMapping("/{id}")
    public AutorResponseDTO atualizar(@PathVariable Long id,
                                      @RequestBody AutorRequestDTO dto) {
        return service.atualizar(id, dto);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        service.deletar(id);
    }
}