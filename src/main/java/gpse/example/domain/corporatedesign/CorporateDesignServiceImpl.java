package gpse.example.domain.corporatedesign;

import gpse.example.domain.exceptions.CorporateDesignNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * The service that stores corporate designs in the repository.
 */
@Service
public class CorporateDesignServiceImpl implements CorporateDesignService {

    private CorporateDesignRepository corporateDesignRepository;

    @Autowired
    public CorporateDesignServiceImpl(final CorporateDesignRepository corporateDesignRepository) {
        this.corporateDesignRepository = corporateDesignRepository;
    }

    @Override
    public CorporateDesign saveCorporateDesign(final CorporateDesign corporateDesign) {
        return corporateDesignRepository.save(corporateDesign);
    }

    @Override
    public CorporateDesign getCorporateDesign(final long id) throws CorporateDesignNotFoundException {
        return corporateDesignRepository.findById(id)
            .orElseThrow(() -> new CorporateDesignNotFoundException());
    }

}
