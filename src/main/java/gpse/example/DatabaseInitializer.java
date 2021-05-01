package gpse.example;
import gpse.example.domain.*;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class DatabaseInitializer implements InitializingBean {

    @Autowired
    public DatabaseInitializer() {
    }

    @Override
    public void afterPropertiesSet() {
    }
}
