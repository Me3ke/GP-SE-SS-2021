package gpse.example;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = ELSA.class)
public class ELSAIT {

    @Test
    public void contextLoads() {
        //Tests Application-Start.
    }

}