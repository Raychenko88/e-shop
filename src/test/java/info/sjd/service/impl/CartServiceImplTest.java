package info.sjd.service.impl;

import info.sjd.dao.CartDAO;
import info.sjd.model.Cart;
import info.sjd.model.User;
import info.sjd.service.CartService;
import info.sjd.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import javax.xml.crypto.Data;

import java.util.Date;

import static java.util.Optional.of;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@SpringBootTest
class CartServiceImplTest {

    private Long currentTime = new Date().getTime();

    @Autowired
    CartService cartService;

    @Autowired
    UserService userService;

    @MockBean
    CartDAO cartDAO;

    @Test
    void saveTest() {
        User user = User.builder().login("test_login111").password("test_pass").firstName("test_fn").lastName("test_ln").build();
        Cart cart = Cart.builder().closed(0).user(user).creationTime(currentTime).build();
        when(cartDAO.save(any(Cart.class))).thenReturn(cart);
        Cart cart1 = cartService.save(cart);
        assertNotNull(cart1);
        verify(cartDAO, times(1)).save(any(Cart.class));
    }

    @Test
    void updateTest() {
        User user = User.builder().login("test_login111").password("test_pass").firstName("test_fn").lastName("test_ln").build();
        Cart cart = Cart.builder().closed(0).user(user).creationTime(currentTime).build();
        cart.setId(1);
        when(cartDAO.findById(anyInt())).thenReturn(of(cart));
        when(cartDAO.save(any(Cart.class))).thenReturn(cart);
        Cart cart1 = cartService.update(cart);
        assertNotNull(cart1);
        verify(cartDAO, times(1)).findById(anyInt());
        verify(cartDAO, times(1)).save(any(Cart.class));
    }
}
