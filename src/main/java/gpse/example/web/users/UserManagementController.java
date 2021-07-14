package gpse.example.web.users;

import gpse.example.domain.security.SecurityConstants;
import gpse.example.domain.users.User;
import gpse.example.domain.users.UserService;
import gpse.example.web.JSONResponseObject;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

/**
 * The controller used for user management operations.
 */
@RestController
@CrossOrigin("http://localhost:8080/")
@RequestMapping("/api")
public class UserManagementController {

    private static final String ROLE_ADMIN = "ROLE_ADMIN";
    private static final String USERID = "userid";
    private static final int STATUS_CODE_WRONG_ROLE = 227;
    private static final int STATUS_CODE_USER_DOESNT_EXIST = 228;
    private static final int STATUS_CODE = 200;
    private static final String NO_ADMIN = "The User That stated this request, is no admin.";
    private static final String NOT_REGISTERED_YET = "either the user that stated the request or the user that should"
        + " be validated, is not registered yet.";

    @Autowired
    private UserService userService;

    @Autowired
    private SecurityConstants securityConstants;

    /**
     * The method used to get a list of all users that are registered with the information that is necessary for user
     * management. It is called by an admin via api-request.
     *
     * @param token the jwt-token of the user that stated the request. It is used to ensure that the request is stated
     *              by an admin.
     * @return a list of all users that are registered with the information that is necessary for user management.
     */
    @GetMapping("/admin/allusers")
    public UserManagementResponseList getAllUsers(final @RequestHeader String token) {
        final byte[] signingKey = securityConstants.getJwtSecret().getBytes();
        final Jws<Claims> parsedToken = Jwts.parserBuilder()
            .setSigningKey(signingKey).build()
            .parseClaimsJws(token.replace(securityConstants.getTokenPrefix(), "").strip());

        try {
            final User user = userService.getUser(parsedToken.getBody().getSubject());
            if (user.getRoles().contains(ROLE_ADMIN)) {
                return new UserManagementResponseList(userService.getAllUsers());
            } else {
                return null;
            }
        } catch (UsernameNotFoundException unfe) {
            return null;
        }
    }

    /**
     * The method used to validate a userAccount. It is called by an admin via api-request.
     *
     * @param userID the id of the account that should be validated.
     * @param token  the jwt-token of the user that stated the request. It is used to ensure that the request is stated
     *               by an admin.
     * @return a fitting response.
     */
    @PutMapping("admin/validate")
    public JSONResponseObject validateUser(@RequestParam(USERID) final String userID,
                                           final @RequestHeader String token) {
        final JSONResponseObject response = checkUserAndRole(userID, token);
        if (response.getStatus() == STATUS_CODE) {
            final User user = userService.getUser(userID);
            user.setAccountNonLocked(true);
            userService.saveUser(user);
        }
        return response;
    }

    /**
     * the method used to make an exitsing user become an admin. It is called by an admin via api-request.
     *
     * @param userID the id of the account that should become an admin.
     * @param token  the jwt-token of the user that stated the request. It is used to ensure that the request is stated
     *               by an admin.
     * @return a fitting response.
     */
    @PutMapping("admin/makeadmin")
    public JSONResponseObject makeAdmin(@RequestParam(USERID) final String userID,
                                        @RequestHeader final String token) {
        final JSONResponseObject response = checkUserAndRole(userID, token);
        if (response.getStatus() == STATUS_CODE) {
            final User user = userService.getUser(userID);
            user.addRole(ROLE_ADMIN);
            userService.saveUser(user);
        }
        return response;
    }

    /**
     * The method to lock an account, called by an admin via api request.
     *
     * @param userID the id of the account that should be locked.
     * @param token  the jwt-token of the user that stated the request. It is used to ensure that the request is stated
     *               by an admin.
     * @return a fitting response.
     */
    @PutMapping("admin/lockUser")
    public JSONResponseObject lockUser(@RequestParam(USERID) final String userID,
                                       @RequestHeader final String token) {
        final JSONResponseObject response = checkUserAndRole(userID, token);
        if (response.getStatus() == STATUS_CODE) {
            final User user = userService.getUser(userID);
            user.setAccountNonLocked(false);
            userService.saveUser(user);
        }
        return response;
    }

    /**
     * makes user seen.
     * @param userID userid
     * @param token jwt Token
     * @return jsonResponseObject
     */
    @PutMapping("admin/userseen")
    public JSONResponseObject changeUserToSeen(@RequestParam(USERID) final String userID,
                                                @RequestHeader final String token) {
        final JSONResponseObject response = checkUserAndRole(userID, token);
        if (response.getStatus() == STATUS_CODE) {
            final User user = userService.getUser(userID);
            user.setToSeenByAdmin();
            userService.saveUser(user);
        }
        return response;
    }

    private JSONResponseObject checkUserAndRole(final String userID, final String token) {
        final JSONResponseObject response = new JSONResponseObject();

        final byte[] signingKey = securityConstants.getJwtSecret().getBytes();
        final Jws<Claims> parsedToken = Jwts.parserBuilder()
            .setSigningKey(signingKey).build()
            .parseClaimsJws(token.replace(securityConstants.getTokenPrefix(), "").strip());

        try {
            final User admin = userService.getUser(parsedToken.getBody().getSubject());
            userService.getUser(userID);
            if (admin.getRoles().contains(ROLE_ADMIN)) {
                response.setStatus(STATUS_CODE);
                return response;
            } else {
                response.setStatus(STATUS_CODE_WRONG_ROLE);
                response.setMessage(NO_ADMIN);
                return response;
            }
        } catch (UsernameNotFoundException unfe) {
            response.setStatus(STATUS_CODE_USER_DOESNT_EXIST);
            response.setMessage(NOT_REGISTERED_YET);
            return response;
        }
    }
}
