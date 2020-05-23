package info.sjd.dao;

import info.sjd.EShopRsApplication;
import info.sjd.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
//@ContextConfiguration(classes = application)
//@Import(UserDAOTest.class)
//@SpringJUnitConfig(UserDAOTest.Config.class)
class UserDAOTest {

//    @Configuration
//    static class Config {}

    @Autowired
    private UserDAO userDAO;


    @Test
    void findByLogin() {
        User user = User.builder()
                .login("test_login")
                .password("test_pass")
                .firstName("test_fn")
                .lastName("test_ln")
                .build();
        userDAO.save(user);
        User userFromDB = userDAO.findById(user.getId()).orElse(null);
        assertNotNull(userFromDB);
        assertEquals(user.getLogin(), userFromDB.getLogin());

        userDAO.delete(user);
        User userDeletedFromDB = userDAO.findById(user.getId()).orElse(null);
        assertNull(userDeletedFromDB);
    }

    @Test
    void findByLoginAndPassword() {
    }
}