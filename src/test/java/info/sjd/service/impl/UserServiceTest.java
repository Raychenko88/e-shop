package info.sjd.service.impl;

import info.sjd.dao.UserDAO;
import info.sjd.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static java.util.Optional.of;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class UserServiceTest {

    @Autowired
    UserServiceImpl userService;

    @MockBean
    UserDAO userDAO;

    @Test
    void save() {
        User user = User.builder().login("test_login111").password("test_pass").firstName("test_fn").lastName("test_ln").build();
        when(userDAO.save(any(User.class))).thenReturn(user);
        User user1 = userService.save(user);
        assertNotNull(user1);
        assertEquals(user.getLogin(), user1.getLogin());
        verify(userDAO,times(1)).save(any(User.class));
    }


    @Test
    void update() {
        User user = User.builder().login("test_login111").password("test_pass").firstName("test_fn").lastName("test_ln").build();
        user.setId(1);
        when(userDAO.findById(anyInt())).thenReturn(of(user));
        when(userDAO.save(any(User.class))).thenReturn(user);
        User user1 = userService.update(user);
        assertNotNull(user1);
        verify(userDAO, times(1)).findById(anyInt());
        verify(userDAO, times(1)).save(any(User.class));
    }
}
