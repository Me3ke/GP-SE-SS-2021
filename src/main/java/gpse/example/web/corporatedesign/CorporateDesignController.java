package gpse.example.web.corporatedesign;

import gpse.example.domain.corporatedesign.CorporateDesign;
import gpse.example.domain.corporatedesign.CorporateDesignService;
import gpse.example.domain.exceptions.CorporateDesignNotFoundException;
import gpse.example.web.JSONResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * The CorporateDesign Controller handles request regarding the corporate design.
 */
@RestController
@CrossOrigin("http://localhost:8088")
@RequestMapping("/api")
public class CorporateDesignController {

    private static final int STATUS_CODE_OK = 200;
    private static final long CHANGEABLE_DESIGN = 2L;
    private static final long DEFAULT_DESIGN = 1L;
    private static final String SUCCESSFUL = "corporate design changed successfully";

    private CorporateDesignService corporateDesignService;

    @Autowired
    public CorporateDesignController(final CorporateDesignService corporateDesignService) {
        this.corporateDesignService = corporateDesignService;
    }

    /**
     * The getLogo method does a get request on the logo.
     *
     * @return a logos object which contains both logos.
     * @throws CorporateDesignNotFoundException if the default corporateDesign was not properly created.
     */
    @GetMapping("/corporate/logo")
    public LogosRequestBody getLogo() throws CorporateDesignNotFoundException {
        final LogosRequestBody logosRequestBody = new LogosRequestBody();
        CorporateDesign corporateDesign;
        try {
            corporateDesign = corporateDesignService.getCorporateDesign(CHANGEABLE_DESIGN);
        } catch (CorporateDesignNotFoundException e) {
            corporateDesign = corporateDesignService.getCorporateDesign(DEFAULT_DESIGN);
        }
        logosRequestBody.setLogo(corporateDesign.getLogo());
        logosRequestBody.setLogoTyp(corporateDesign.getLogoTyp());
        logosRequestBody.setLogoDark(corporateDesign.getLogoDark());
        logosRequestBody.setLogoDarkTyp(corporateDesign.getLogoDarkTyp());
        return logosRequestBody;
    }

    /**
     * The getColors method does a get request on the colors.
     *
     * @return a String array which contains all the colors.
     * @throws CorporateDesignNotFoundException if the default corporateDesign was not properly created.
     */
    @GetMapping("/corporate/colors")
    public String[] getColors() throws CorporateDesignNotFoundException {
        List<String> colorsList;
        try {
            colorsList = corporateDesignService.getCorporateDesign(CHANGEABLE_DESIGN).getColors();
        } catch (CorporateDesignNotFoundException e) {
            colorsList = corporateDesignService.getCorporateDesign(DEFAULT_DESIGN).getColors();
        }
        return colorsList.toArray(new String[0]);
    }

    /**
     * The changeLogo method does a put request and changes the logo.
     *
     * @param logosRequestBody the request body which contains both logos as byte array.
     * @return a JSONResponseObject showing the status of the request.
     * @throws CorporateDesignNotFoundException if the default corporateDesign was not properly created.
     */
    @Secured("ROLE_ROLE_ADMIN")
    @PutMapping("/corporate/logo")
    public JSONResponseObject changeLogo(@RequestBody final LogosRequestBody logosRequestBody)
        throws CorporateDesignNotFoundException {
        final JSONResponseObject response = new JSONResponseObject();
        CorporateDesign corporateDesign;
        final CorporateDesign defaultDesign = corporateDesignService.getCorporateDesign(DEFAULT_DESIGN);
        try {
            corporateDesign = corporateDesignService.getCorporateDesign(CHANGEABLE_DESIGN);
        } catch (CorporateDesignNotFoundException e) {
            corporateDesign = new CorporateDesign(defaultDesign.getColors().toArray(new String[0]),
                null, null);
        }
        corporateDesign.setLogo(logosRequestBody.getLogo(), logosRequestBody.getLogoTyp());
        corporateDesign.setLogoDark(logosRequestBody.getLogoDark(), logosRequestBody.getLogoDarkTyp());
        corporateDesignService.saveCorporateDesign(corporateDesign);
        response.setStatus(STATUS_CODE_OK);
        response.setMessage(SUCCESSFUL);
        return response;
    }

    /**
     * The changeColors method does a put request to change the colors of the corporate Design.
     *
     * @param colorsRequestBody the given colors that should be implemented in the corporate Design.
     * @return a JSONResponseObject showing the status of the request.
     * @throws CorporateDesignNotFoundException if the default corporateDesign was not properly created.
     */
   // @Secured("ROLE_ADMIN")
    @PutMapping("/corporate/colors")
    public JSONResponseObject changeColors(@RequestBody final ColorsRequestBody colorsRequestBody)
        throws CorporateDesignNotFoundException {
        final JSONResponseObject response = new JSONResponseObject();
        CorporateDesign corporateDesign;
        final CorporateDesign defaultDesign = corporateDesignService.getCorporateDesign(DEFAULT_DESIGN);
        try {
            corporateDesign = corporateDesignService.getCorporateDesign(CHANGEABLE_DESIGN);
        } catch (CorporateDesignNotFoundException e) {
            corporateDesign = new CorporateDesign(null, null, null);
        }
        corporateDesign.setColors(colorsRequestBody.getColors(), defaultDesign.getColors());
        corporateDesignService.saveCorporateDesign(corporateDesign);
        response.setStatus(STATUS_CODE_OK);
        response.setMessage(SUCCESSFUL);
        return response;
    }


}
