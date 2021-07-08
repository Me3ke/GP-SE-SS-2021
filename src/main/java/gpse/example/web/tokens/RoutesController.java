package gpse.example.web.tokens;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/")
public class RoutesController {

    public static final String FORWARD = "forward:/";

    @GetMapping(value = "{_:^(?!index\\.html|api).*$}")
    public String redirectApi() {
        return FORWARD;
    }

    @GetMapping("{_:^(?!index\\.html|api).*$}/*")
    public String redirectAll() {
        return FORWARD;
    }

    @GetMapping("{_:^(?!index\\.html|api).*$}/*/register/confirm/{token}")
    public String redirectRegister(@PathVariable("token") final String token) {
        System.out.println("test");
        return FORWARD;
    }

}
