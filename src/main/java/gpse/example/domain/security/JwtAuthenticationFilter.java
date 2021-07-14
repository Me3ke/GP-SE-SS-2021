package gpse.example.domain.security;


import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This Class is a custom Filter for the spring-security FilterChain.
 * It consumes Login Requests and adds JWT to Response.
 */
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    private final SecurityConstants securityConstants;

    /**
     * Creates instance of this Filter.
     * @param authenticationManager given from spring-boot
     * @param securityConstants instance of SecurityConstants class
     */
    public JwtAuthenticationFilter(final AuthenticationManager authenticationManager,
                                   final SecurityConstants securityConstants) {
        super();
        this.authenticationManager = authenticationManager;
        this.securityConstants = securityConstants;

        setFilterProcessesUrl(this.securityConstants.getAuthLoginUrl());
    }

    @Override
    public Authentication attemptAuthentication(final HttpServletRequest request, final HttpServletResponse response) {
        final String username = request.getParameter("username");
        final String password = request.getParameter("password");
        final UsernamePasswordAuthenticationToken authenticationToken
            = new UsernamePasswordAuthenticationToken(username, password);

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
            .setExpiration(new Date(System.currentTimeMillis() + securityConstants.getTokenTimeout()))
            .claim("rol", roles)
            .compact();

        response.addHeader(securityConstants.getTokenHeader(), securityConstants.getTokenPrefix() + token);
    }
}
