package com.example.auction_app.persistence;

import com.example.auction_app.models.User;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.sql.Connection;
import java.util.Date;

public class UserRepositoryTest {
    private static Connection connection;
    private static UserRepository userRepository;
    private final int userIdToDelete = 99;
    @BeforeAll
    public static void setup() throws Exception {
        connection = DatabaseConnection.getConnection();
        userRepository = new UserRepository(connection);
    }

    @AfterAll
    public static void tearDown() throws Exception {
        connection.close();
    }
    @Test
    public void testDeleteUserById() {
        // Verifică dacă utilizatorul există
        User user = userRepository.get(userIdToDelete);
        assertNotNull(user, "utilizator exista");
        userRepository.delete(user);
        User deletedUser = userRepository.get(userIdToDelete);
        assertNull(deletedUser, "utilizator șters.");
    }
    @Test
    public void testAddAndGetUser() {
        User newUser = new User(12, "test@examplle.com", "password123", "Test User", "123 Test St", "1234567890", new Date());
        userRepository.add(newUser);

        User fetchedUser = userRepository.get(newUser.getUserID());
        assertNotNull(fetchedUser);
        assertEquals(newUser.getEmail(), fetchedUser.getEmail());

    }

    @Test
    public void testUpdateUser() {
        User user = new User(0, "update@example.com", "password123", "Update User", "321 Test St", "0987654321", new Date());
        userRepository.add(user);

        user.setEmail("updated@example.com");
        userRepository.update(user);

        User updatedUser = userRepository.get(user.getUserID());
        assertEquals("uuuuu@example.com", updatedUser.getEmail());

        userRepository.delete(updatedUser);
    }

    @Test
    public void testDeleteUser() {
        User user = new User(2, "bbb@example.com", "password123", "D", "999", "123", new Date());
        userRepository.add(user);

        userRepository.delete(user);
        assertNull(userRepository.get(user.getUserID()));
    }

    @Test
    public void testGetSize() {
        int initialSize = userRepository.getSize();
        User user = new User(0, "aaaa@example.com", "password123", "Sizer", "1121 Te", "111", new Date());
        userRepository.add(user);

        assertEquals(initialSize + 1, userRepository.getSize());

        userRepository.delete(user);
    }
}
