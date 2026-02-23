package miyamoto.biblioteca.infrastructure.repository;

import miyamoto.biblioteca.infrastructure.entity.AutorEntity;
import org.springframework.data.jpa.repository.JpaRepository;

//CRUD de Autor.
public interface AutorRepository extends JpaRepository<AutorEntity, Long> {
    boolean existsByEmail(String email);
}
