package Salvador_20230690.Salvador_20230690.Repositories;

import Salvador_20230690.Salvador_20230690.Entities.EntityLibros;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositoryLibros extends JpaRepository<EntityLibros, Long> {
}
