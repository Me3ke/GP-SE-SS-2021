package gpse.example.domain.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The filter for the authentification of users who send a login request.
 */
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    private final SecurityConstants securityConstants;

    /**
     * the standard constructor for this filter.
     *
     * @param authenticationManager the standard spring authentication manager
     * @param securityConstants     the security constants that are specific for this server.
     */
    public JwtAuthenticationFilter(final AuthenticationManager authenticationManager,
                                   final SecurityConstants securityConstants) {
        this.authenticationManager = authenticationManager;
        this.securityConstants = securityConstants;

        setFilterProcessesUrl(this.securityConstants.getAuthLoginUrl());
    }

    @Override
    public Authentication attemptAuthentication(final HttpServletRequest request, final HttpServletResponse response) {
        String username = "";
        String password = "";
        final StringBuffer jasonBody = new StringBuffer();
        String line = null;
        try {
            final BufferedReader reader = request.getReader();
            while ((line = reader.readLine()) != null) {
                jasonBody.append(line);
            }
        } catch (Exception e) {
            System.out.println("Exception String parsing from JSON-body");
        }
        try {
            final JSONObject jsonObject = new JSONObject(jasonBody.toString().trim());
            username = jsonObject.getString("username");
            password = jsonObject.getString("password");
        } catch (JSONException err) {
            System.out.println("Exception String parsing to JSON");
        }
        System.out.println(jasonBody.toString());
        System.out.println(username);
        System.out.println(password);

        final UsernamePasswordAuthenticationToken authenticationToken =
            new UsernamePasswordAuthenticationToken(username, password);

        return authenticationManager.authenticate(authenticationToken);
    }

    @Override
    protected void successfulAuthentication(final HttpServletRequest request, final HttpServletResponse response,
                                            final FilterChain filterChain, final Authentication authentication) {
        final UserDetails user = (UserDetails) authentication.getPrincipal();

        final List<String> roles = user.getAuthorities()
            .stream()
            .map(GrantedAuthority::getAuthority)
            .collect(Collectors.toList());

        final byte[] signingKey = securityConstants.getJwtSecret().getBytes();

        final String token = Jwts.builder()
            .signWith(Keys.hmacShaKeyFor(signingKey), SignatureAlgorithm.HS512)
            .setHeaderParam("typ", securityConstants.getTokenType())
            .setIssuer(securityConstants.getTokenIssuer())
            .setAudience(securityConstants.getTokenAudience())
            .setSubject(user.getUsername())
            .setExpiration(new Date(System.currentTimeMillis() + securityConstants.getTokenLifetime()))
            .claim("rol", roles)
            .compact();

        response.addHeader(securityConstants.getTokenHeader(), securityConstants.getTokenPrefix() + token);
    }
}
