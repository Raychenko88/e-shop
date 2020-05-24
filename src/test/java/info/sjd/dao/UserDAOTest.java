package info.sjd.dao;

import info.sjd.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class UserDAOTest {

    @Autowired
    private UserDAO userDAO;


    @Test
    void findByLoginTest() {
        User user = User.builder().login("test_login").password("test_pass").firstName("test_fn").lastName("test_ln").build();
        userDAO.save(user);
        User userFromDB = userDAO.findById(user.getId()).orElse(null);
        assertNotNull(userFromDB);
        assertEquals(user.getLogin(), userFromDB.getLogin());

        userDAO.delete(user);
        User userDeletedFromDB = userDAO.findById(user.getId()).orElse(null);
        assertNull(userDeletedFromDB);
    }

    @Test
    void findByLoginAndPasswordTest() {
        User user1 = User.builder().login("test_login1").password("test_pass1").firstName("test_fn").lastName("test_ln").build();
        userDAO.save(user1);
        User user = userDAO.findByLoginAndPassword(user1.getLogin(), user1.getPassword());
        assertNotNull(user);
        assertEquals(user1.getLogin(),user.getLogin());
        userDAO.delete(user1);
    }
}