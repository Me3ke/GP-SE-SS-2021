package gpse.example.domain.security;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.cors.*;

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
            .authorizeRequests()
            .antMatchers("/api/**").permitAll()
            .and()
            .addFilter(new JwtAuthenticationFilter(authenticationManager(), securityConstants))
            .addFilter(new JwtAuthorizationFilter(authenticationManager(), securityConstants))
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
