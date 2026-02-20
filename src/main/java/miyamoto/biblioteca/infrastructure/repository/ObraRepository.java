package miyamoto.biblioteca.infrastructure.repository;

import miyamoto.biblioteca.infrastructure.entity.ObraEntity;
import org.springframework.data.jpa.repository.JpaRepository;

//CRUD de Obra.

public interface ObraRepository extends JpaRepository<ObraEntity, Long> {
}
