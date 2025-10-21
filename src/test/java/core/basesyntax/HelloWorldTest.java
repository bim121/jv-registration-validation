package core.basesyntax;

import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Feel free to remove this class and create your own.
 */
public class HelloWorldTest {
    private RegistrationServiceImpl registrationServiceImpl;

    @BeforeEach
    void setUp() {
        registrationServiceImpl = new RegistrationServiceImpl();
    }

    @Test
    void register_validUser_ok() {
        User user = new User();
        user.setLogin("validLogin");
        user.setPassword("securePass");
        user.setAge(25);

        User registered = registrationServiceImpl.register(user);

        assertNotNull(registered);
        assertEquals("validLogin", registered.getLogin());
        assertEquals(25, registered.getAge());
    }

    @Test
    void register_nullUser_notOk() {
        assertThrows(RegistrationException.class,
                () -> registrationServiceImpl.register(null));
    }

    @Test
    void register_nullLogin_notOk() {
        User user = new User();
        user.setPassword("password");
        user.setAge(25);

        assertThrows(RegistrationException.class,
                () -> registrationServiceImpl.register(user));
    }

    @Test
    void register_shortLogin_notOk() {
        User user = new User();
        user.setLogin("abc");
        user.setPassword("password");
        user.setAge(25);

        assertThrows(RegistrationException.class,
                () -> registrationServiceImpl.register(user));
    }

    @Test
    void register_nullPassword_notOk() {
        User user = new User();
        user.setLogin("validLogin");
        user.setAge(25);

        assertThrows(RegistrationException.class,
                () -> registrationServiceImpl.register(user));
    }

    @Test
    void register_shortPassword_notOk() {
        User user = new User();
        user.setLogin("validLogin");
        user.setPassword("123");
        user.setAge(25);

        assertThrows(RegistrationException.class,
                () -> registrationServiceImpl.register(user));
    }

    @Test
    void register_nullAge_notOk() {
        User user = new User();
        user.setLogin("validLogin");
        user.setPassword("securePass");

        assertThrows(RegistrationException.class,
                () -> registrationServiceImpl.register(user));
    }

    @Test
    void register_underageUser_notOk() {
        User user = new User();
        user.setLogin("teenUser");
        user.setPassword("securePass");
        user.setAge(17);

        assertThrows(RegistrationException.class,
                () -> registrationServiceImpl.register(user));
    }

    @Test
    void register_duplicateLogin_notOk() {
        User user1 = new User();
        user1.setLogin("sameLogin");
        user1.setPassword("passOne");
        user1.setAge(30);

        User user2 = new User();
        user2.setLogin("sameLogin");
        user2.setPassword("passTwo");
        user2.setAge(25);

        registrationServiceImpl.register(user1);

        assertThrows(RegistrationException.class,
                () -> registrationServiceImpl.register(user2));
    }
}
