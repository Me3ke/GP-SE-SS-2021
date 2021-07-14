package gpse.example.domain.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.SignatureException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This Class is a custom Filter for the spring-security FilterChain.
 * It secures the Authorization with ROLES.
 */
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    private static final Logger LOG = LoggerFactory.getLogger(JwtAuthorizationFilter.class);
    private final  SecurityConstants securityConstants;

    public JwtAuthorizationFilter(final AuthenticationManager authenticationManager,
                                  final SecurityConstants securityConstants) {
        super(authenticationManager);
        this.securityConstants = securityConstants;
    }

    @Override
    protected void doFilterInternal(final HttpServletRequest request, final HttpServletResponse response,
                                    final FilterChain filterChain) throws IOException, ServletException {
        final UsernamePasswordAuthenticationToken authentication = getAuthentication(request);
        if (authentication == null) {
            filterChain.doFilter(request, response);
            return;
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(final HttpServletRequest request) {

        final String token = request.getHeader(securityConstants.getTokenHeader());
        if (token != null && !token.equals("") && token.startsWith(securityConstants.getTokenPrefix())) {
            try {
                final UsernamePasswordAuthenticationToken username = getUsernamePasswordAuthenticationToken(token);
                if (username != null) {
                    return username;
                }
            } catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException | SignatureException
                | IllegalArgumentException exception) {
                logException(exception, token);
            }
        }

        return null;
    }

    private void logException(final Exception exception, final String token) {
        final String className =
            (String) exception.getClass().getName().subSequence(exception.getClass().getName().lastIndexOf(".") + 1,
                exception.getClass().getName().length());
        switch (className) {
            case "ExpiredJwtException":
                LOG.warn("Request to parse expired JWT : {} failed : {}", token, exception.getMessage());
                break;
            case "UnsupportedJwtException":
                LOG.warn("Request to parse unsupported JWT : {} failed : {}", token, exception.getMessage());
                break;
            case "MalformedJwtException":
                LOG.warn("Request to parse invalid JWT : {} failed : {}", token, exception.getMessage());
                break;
            case "SignatureException":
                LOG.warn("Request to parse JWT with invalid signature : {} failed : {}", token, exception.getMessage());
                break;
            case "IllegalArgumentException":
                LOG.warn("Request to parse empty or null JWT : {} failed : {}", token, exception.getMessage());
                break;
            default:
                LOG.warn("something else JWT : {} failed : {}", token, exception.getMessage());
                break;
        }
    }

    private UsernamePasswordAuthenticationToken getUsernamePasswordAuthenticationToken(final String token) {
        final byte[] signingKey = securityConstants.getJwtSecret().getBytes();

        final Jws<Claims> parsedToken = Jwts.parserBuilder()
            .setSigningKey(signingKey).build()
            .parseClaimsJws(token.replace(securityConstants.getTokenPrefix(), "").strip());

        final String username = parsedToken.getBody().getSubject();

        final List<SimpleGrantedAuthority> authorities = ((List<?>) parsedToken.getBody()
            .get("rol")).stream()
            .map(authority -> new SimpleGrantedAuthority((String) authority))
            .collect(Collectors.toList());

        if (username != null && !username.equals("")) {
            return new UsernamePasswordAuthenticationToken(username, null, authorities);
        }
        return null;
    }
}
