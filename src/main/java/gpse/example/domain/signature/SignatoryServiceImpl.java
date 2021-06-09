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
    public List<Signatory> saveSignatories(final List<Signatory> signatories) {
        /*for (final Signatory signatory : signatories) {
            signatoryRepository.save(signatory);
        }
        return signatories;
         */
        return signatories;
    }

    @Override
    public  void delete(final List<Signatory> signatories) {
        /*for (final Signatory signatory : signatories) {
            signatoryRepository.deleteById(signatory.getId());
        }
         */
    }

    @Override
    public Signatory saveSignatory(final Signatory signatory) {
        return signatoryRepository.save(signatory);
    }

    @Override
    public void deleteAll() {
        //signatoryRepository.deleteAll();
    }
}
