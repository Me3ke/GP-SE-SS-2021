package gpse.example.web.corporatedesign;

import gpse.example.domain.corporatedesign.CorporateDesign;
import gpse.example.domain.corporatedesign.CorporateDesignService;
import gpse.example.domain.exceptions.CorporateDesignNotFoundException;
import gpse.example.domain.security.SecurityConstants;
import gpse.example.domain.users.User;
import gpse.example.domain.users.UserService;
import gpse.example.web.JSONResponseObject;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
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
    private static final int STATUS_CODE_WRONG_ROLE = 227;
    private static final String SUCCESS_MESSAGE = "Success";
    private static final String ADMIN_VALIDATION_REQUIRED = "You are not an admin";
    private static final long DEFAULT_DESIGN = 1L;
    private static final String SUCCESSFUL = "corporate design changed successfully";
    private final CorporateDesignService corporateDesignService;

    @Autowired
    public CorporateDesignController(final CorporateDesignService corporateDesignService) {
        this.corporateDesignService = corporateDesignService;
    }

    @Lazy
    @Autowired
    private SecurityConstants securityConstants;

    @Autowired
    private UserService userService;

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
        logosRequestBody.setLogoType(corporateDesign.getLogoType());
        logosRequestBody.setLogoDark(corporateDesign.getLogoDark());
        logosRequestBody.setLogoDarkType(corporateDesign.getLogoDarkType());
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
    // @Secured("ROLE_ADMIN")
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
                new byte[0], new byte[0]);
        }
        if (logosRequestBody.isDark()) {
            corporateDesign.setLogoDark(logosRequestBody.getLogo(), logosRequestBody.getLogoType());
        } else {
            corporateDesign.setLogo(logosRequestBody.getLogo(), logosRequestBody.getLogoType());
        }
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
            corporateDesign = new CorporateDesign(null, new byte[0], new byte[0]);
        }
        corporateDesign.setColors(colorsRequestBody.getColors(), defaultDesign.getColors());
        corporateDesignService.saveCorporateDesign(corporateDesign);
        response.setStatus(STATUS_CODE_OK);
        response.setMessage(SUCCESSFUL);
        return response;
    }

    @GetMapping("impressum")
    public JSONResponseObject getImpressumsText() throws CorporateDesignNotFoundException {
        CorporateDesign corporateDesign;
        JSONResponseObject jsonResponseObject = new JSONResponseObject();
        try {
            corporateDesign = corporateDesignService.getCorporateDesign(CHANGEABLE_DESIGN);
        } catch (CorporateDesignNotFoundException e) {
            corporateDesign = corporateDesignService.getCorporateDesign(DEFAULT_DESIGN);
        }
        jsonResponseObject.setStatus(STATUS_CODE_OK);
        jsonResponseObject.setMessage(corporateDesign.getImpressumsText());
        return jsonResponseObject;
    }

    @PutMapping("impressum")
    public JSONResponseObject setImpressumsText(@RequestHeader final String token,
                                                @RequestBody final String impressumText)
            throws CorporateDesignNotFoundException {
        JSONResponseObject jsonResponseObject = new JSONResponseObject();
        if (checkIfAdmin(token)) {
            CorporateDesign corporateDesign;
            final CorporateDesign defaultDesign = corporateDesignService.getCorporateDesign(DEFAULT_DESIGN);
            try {
                corporateDesign = corporateDesignService.getCorporateDesign(CHANGEABLE_DESIGN);
            } catch (CorporateDesignNotFoundException e) {
                corporateDesign = new CorporateDesign(defaultDesign.getColors().toArray(new String[0]),
                        defaultDesign.getLogo(), defaultDesign.getLogoDark());
            }
            corporateDesign.setImpressumsText(impressumText);
            corporateDesignService.saveCorporateDesign(corporateDesign);
            jsonResponseObject.setStatus(STATUS_CODE_OK);
            jsonResponseObject.setMessage(SUCCESSFUL);
        } else {
            jsonResponseObject.setStatus(STATUS_CODE_WRONG_ROLE);
            jsonResponseObject.setMessage(ADMIN_VALIDATION_REQUIRED);
        }
        return jsonResponseObject;
    }

    private boolean checkIfAdmin(final String token) {
        final byte[] signingKey = securityConstants.getJwtSecret().getBytes();
        final Jws<Claims> parsedToken = Jwts.parserBuilder()
                .setSigningKey(signingKey).build()
                .parseClaimsJws(token.replace(securityConstants.getTokenPrefix(), "").strip());

        final User user = userService.getUser(parsedToken.getBody().getSubject());
        return user.getRoles().contains("ROLE_ADMIN");
    }
}
