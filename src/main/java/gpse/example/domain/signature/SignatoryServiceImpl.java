package gpse.example.domain.signature;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * The standard implementation for SignatoryService.
 */
@Service
public class SignatoryServiceImpl implements SignatoryService {

    private final SignatoryRepository signatoryRepository;

    @Autowired
    public SignatoryServiceImpl(final SignatoryRepository signatoryRepository) {
        this.signatoryRepository = signatoryRepository;
    }

    @Override
    public  void delete(final List<Signatory> signatories) {
        for (final Signatory signatory : signatories) {
            signatoryRepository.deleteById(signatory.getId());
        }
    }
}
