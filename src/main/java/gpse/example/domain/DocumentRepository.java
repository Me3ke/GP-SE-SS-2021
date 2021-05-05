package gpse.example.domain;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * the interface from which Spring creates a repository for the Documents.
 */
@Repository
public interface DocumentRepository extends CrudRepository<Document, Long> {
}
