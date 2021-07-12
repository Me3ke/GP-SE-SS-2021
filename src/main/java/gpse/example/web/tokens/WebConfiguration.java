package gpse.example.web.tokens;


import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * This class is responsible for catching all routes made by URL requests.
 */
@Configuration
public class WebConfiguration implements WebMvcConfigurer {

    private static final String FORWARD = "forward:/";

    @Override
    public void addViewControllers(final ViewControllerRegistry registry) {
        registry.addViewController("/**/{path:[^\\.]+}")
                .setViewName(FORWARD);
    }

}
