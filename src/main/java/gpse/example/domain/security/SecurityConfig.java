package gpse.example.domain.security;

import gpse.example.domain.exceptions.SecurityConfigException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.*;

import java.util.Arrays;

/**
 * This class configures the spring-security settings.
 */
@Configuration
@EnableWebSecurity
@EnableAutoConfiguration
@EnableConfigurationProperties(SecurityConstants.class)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final SecurityConstants securityConstants;

    @Autowired
    public SecurityConfig(final SecurityConstants securityConstants) {
        super();
        this.securityConstants = securityConstants;
    }


    @Override
    protected void configure(final HttpSecurity http) throws SecurityConfigException {
        try {
            http.cors().and().csrf().disable()
                .authorizeRequests()
                .antMatchers("/api/authenticate").permitAll()
                .antMatchers("/api/user/newpassword*").permitAll()
                .antMatchers("/api/corporate/logo").permitAll()
                .antMatchers("/api/corporate/colors").permitAll()
                .and()
                .addFilter(new JwtAuthenticationFilter(authenticationManager(), securityConstants))
                .addFilter(new JwtAuthorizationFilter(authenticationManager(), securityConstants))
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        } catch (Exception exception) {
            throw new SecurityConfigException(exception);

        }

    }

    /**
     * configure global mathod.
     *
     * @param userDetailsService userDetailsservice
     * @param passwordEncoder    passwordEncoder
     * @param auth               authenticationmanager
     * @throws SecurityConfigException Exception Thrown if authentication failed.
     */
    @Autowired
    public void configureGlobal(final UserDetailsService userDetailsService,
                                final PasswordEncoder passwordEncoder,
                                final AuthenticationManagerBuilder auth) throws SecurityConfigException {

        try {
            auth.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder);
        } catch (Exception exception) {
            throw new SecurityConfigException(exception);
        }
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    /**
     * This method returns a configured state for cors.
     *
     * @return a configured CorsConfigurationSource
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        final CorsConfiguration corsConfiguration = new CorsConfiguration().applyPermitDefaultValues();
        corsConfiguration.setAllowedMethods(Arrays.asList("POST", "GET", "PUT", "PATCH", "DELETE"));

        corsConfiguration.setAllowedHeaders(Arrays.asList("X-Requested-With", "Origin",
            "Content-Type", "Accept", "Authorization", "token"));

        // This allow us to expose the headers
        corsConfiguration.setExposedHeaders(Arrays.asList("Access-Control-Allow-Headers",
            "Authorization, x-xsrf-token, Access-Control-Allow-Headers, Origin, Accept, X-Requested-With, "
                + "Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers"));

        source.registerCorsConfiguration("/**", corsConfiguration);

        return source;
    }
}
