package gpse.example.domain.documents;

import org.springframework.data.repository.CrudRepository;

/**
 * The standard repository for DocumentMetadata.
 */
public interface DocumentMetaDataRepository extends CrudRepository<DocumentMetaData, Long> {
}
