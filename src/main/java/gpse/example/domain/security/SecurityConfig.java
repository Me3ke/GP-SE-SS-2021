package gpse.example.domain.security;

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
import org.springframework.security.web.session.ConcurrentSessionFilter;
import org.springframework.web.cors.*;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@EnableAutoConfiguration
@EnableConfigurationProperties(SecurityConstants.class)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final SecurityConstants securityConstants;

    @Autowired
    public SecurityConfig(SecurityConstants securityConstants) {
        this.securityConstants = securityConstants;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
            .addFilter(new JwtAuthenticationFilter(authenticationManager(), securityConstants))
            .authorizeRequests(authorizeRequests ->
                authorizeRequests
                    .antMatchers("/api/authenticate").permitAll()
                    .anyRequest().authenticated()
            )
            //.addFilter(new JwtAuthorizationFilter(authenticationManager(), securityConstants))
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Autowired
    public void configureGlobal(final UserDetailsService userDetailsService,
                                final PasswordEncoder passwordEncoder,
                                final AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
            .passwordEncoder(passwordEncoder);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration corsConfiguration = new CorsConfiguration().applyPermitDefaultValues();
        corsConfiguration.setAllowedMethods(Arrays.asList("POST", "GET", "PUT", "PATCH", "DELETE"));
        source.registerCorsConfiguration("/**", corsConfiguration);

        return source;
    }
}
