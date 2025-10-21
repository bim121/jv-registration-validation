package core.basesyntax;

import core.basesyntax.db.Storage;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

/**
 * Feel free to remove this class and create your own.
 */
public class HelloWorldTest {
    private RegistrationServiceImpl registrationServiceImpl;

    @BeforeEach
    void setUp() {
        Storage.people.clear();
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
        user.setLogin(null);
        user.setPassword("password123");
        user.setAge(20);

        RegistrationException e = assertThrows(
                RegistrationException.class,
                () -> registrationServiceImpl.register(user)
        );
        assertEquals("Login can't be null", e.getMessage());
    }

    @Test
    void register_shortLogin_notOk() {
        User user = new User();
        user.setLogin("short");
        user.setPassword("password123");
        user.setAge(20);

        RegistrationException e = assertThrows(
                RegistrationException.class,
                () -> registrationServiceImpl.register(user)
        );
        assertEquals("Login must be at least 6 characters long", e.getMessage());
    }

    @Test
    void register_loginLengthSix_ok() {
        User user = new User();
        user.setLogin("length");
        user.setPassword("password123");
        user.setAge(20);

        assertDoesNotThrow(() -> registrationServiceImpl.register(user));
    }

    @Test
    void register_nullPassword_notOk() {
        User user = new User();
        user.setLogin("userLogin");
        user.setPassword(null);
        user.setAge(20);

        RegistrationException e = assertThrows(
                RegistrationException.class,
                () -> registrationServiceImpl.register(user)
        );
        assertEquals("Password can't be null", e.getMessage());
    }

    @Test
    void register_shortPassword_notOk() {
        User user = new User();
        user.setLogin("userLogin");
        user.setPassword("short");
        user.setAge(20);

        RegistrationException e = assertThrows(
                RegistrationException.class,
                () -> registrationServiceImpl.register(user)
        );
        assertEquals("Password must be at least 6 characters long", e.getMessage());
    }

    @Test
    void register_passwordLengthSix_ok() {
        User user = new User();
        user.setLogin("userLogin");
        user.setPassword("strong");
        user.setAge(20);

        assertDoesNotThrow(() -> registrationServiceImpl.register(user));
    }

    @Test
    void register_nullAge_notOk() {
        User user = new User();
        user.setLogin("userLogin");
        user.setPassword("password123");
        user.setAge(null);

        RegistrationException e = assertThrows(
                RegistrationException.class,
                () -> registrationServiceImpl.register(user)
        );
        assertEquals("Age can't be null", e.getMessage());
    }

    @Test
    void register_negativeAge_notOk() {
        User user = new User();
        user.setLogin("userLogin");
        user.setPassword("password123");
        user.setAge(-1);

        RegistrationException e = assertThrows(
                RegistrationException.class,
                () -> registrationServiceImpl.register(user)
        );
        assertEquals("Age must be at least 18", e.getMessage());
    }

    @Test
    void register_zeroAge_notOk() {
        User user = new User();
        user.setLogin("userLogin");
        user.setPassword("password123");
        user.setAge(0);

        RegistrationException e = assertThrows(
                RegistrationException.class,
                () -> registrationServiceImpl.register(user)
        );
        assertEquals("Age must be at least 18", e.getMessage());
    }

    @Test
    void register_age18_ok() {
        User user = new User();
        user.setLogin("userLogin");
        user.setPassword("password123");
        user.setAge(18);

        assertDoesNotThrow(() -> registrationServiceImpl.register(user));
    }

    @Test
    void register_duplicateLogin_notOk() {
        User existing = new User();
        existing.setLogin("userLogin");
        existing.setPassword("password123");
        existing.setAge(25);
        Storage.people.add(existing);

        User newUser = new User();
        newUser.setLogin("userLogin");
        newUser.setPassword("anotherPass");
        newUser.setAge(30);

        RegistrationException e = assertThrows(
                RegistrationException.class,
                () -> registrationServiceImpl.register(newUser)
        );
        assertEquals("User with login 'userLogin' already exists", e.getMessage());
    }
}
