package gpse.example.domain.corporatedesign;


import gpse.example.domain.exceptions.CorporateDesignNotFoundException;

/**
 * The interface for a corporateDesignService.
 */
public interface CorporateDesignService {
    CorporateDesign saveCorporateDesign(CorporateDesign corporateDesign);
    CorporateDesign getCorporateDesign(long id) throws CorporateDesignNotFoundException;
}
