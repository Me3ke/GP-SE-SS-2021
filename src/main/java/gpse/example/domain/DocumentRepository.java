package gpse.example.domain;

import org.springframework.data.repository.CrudRepository;

/**
 * the interface from which Spring creates a repository for the Documents.
 */
public interface DocumentRepository extends CrudRepository<Document, Long> {
}
