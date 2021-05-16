package gpse.example.web;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

/**
 * The welcome controller.
 */
@CrossOrigin
@RestController
@RequestMapping("/api")
public class WelcomeController {
    @GetMapping("/helloworld")
    @Secured("ROLE_USER")
    public String welcome() {
        return "Hallo aus dem Backend! "
            + LocalDateTime.now().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL));
    }

}
