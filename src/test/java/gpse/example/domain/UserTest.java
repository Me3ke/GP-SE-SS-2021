package gpse.example.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Nested
    public class AddingRole{

        @Test
        public void addNonExistingRole(){
            final User user = new User("HansSchneider@bla.com", "Hans", "Schneiderr", "123457");
            user.addRole("Owner");
            assertTrue(user.getRoles().contains("Owner"));
        }

        @Test
        public void addExistingRole(){
            final User user = new User("Hans.Schneider@bla.com", "Hanssss", "Schneiderrr", "123456");
            user.addRole("Owner");
            user.addRole("Owner");
            final ArrayList<String> expected = new ArrayList<>();
            expected.add("Owner");
            assertEquals(user.getRoles(), expected);
        }
    }


    @Nested
    public class DeletingRole{

        @Test
        public void deleteNonExistingRole(){
            final User user = new User("HansSchneider12@bla.com", "Hansss", "Schneiderrrr", "123455");
            assertDoesNotThrow(() -> user.deleteRole("Owner"));
        }

        @Test
        public void deleteExistingRole(){
            final User user = new User("HansSchneider21@bla.com", "Hanss", "Schneider", "12345");
            user.addRole("Owner");
            user.deleteRole("Owner");
            assertTrue(user.getRoles().isEmpty());
        }
    }
}
