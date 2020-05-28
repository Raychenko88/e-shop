package info.sjd.service.impl;

import info.sjd.dao.CartDAO;
import info.sjd.dao.OrderDAO;
import info.sjd.model.Cart;
import info.sjd.model.Item;
import info.sjd.model.Order;
import info.sjd.model.User;
import info.sjd.service.CartService;
import info.sjd.service.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Date;

import static java.util.Optional.of;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@SpringBootTest
class OrderServiceImplTest {

    private Long currentTime = new Date().getTime();

    @Autowired
    OrderService orderService;

    @Autowired
    CartService cartService;

    @MockBean
    OrderDAO orderDAO;

    @MockBean
    CartDAO cartDAO;

    @Test
    void saveTest() {
        User user = User.builder().login("test_login111").password("test_pass").firstName("test_fn").lastName("test_ln").build();
        Cart cart = Cart.builder().closed(0).user(user).creationTime(currentTime).build();
        Item item = Item.builder().name("test_name").code("t_code").price(123).availability(1).build();
        Order order = Order.builder().item(item).cart(cart).amount(10).build();
        when(orderDAO.save(any(Order.class))).thenReturn(order);
        Order order1 = orderService.save(order);
        assertNotNull(order1);
        verify(orderDAO,times(1)).save(order);
    }

    @Test
    void updateTest() {
        User user = User.builder().login("test_login111").password("test_pass").firstName("test_fn").lastName("test_ln").build();
        Cart cart = Cart.builder().closed(0).user(user).creationTime(currentTime).build();
        Item item = Item.builder().name("test_name").code("t_code").price(123).availability(1).build();
        Order order = Order.builder().item(item).cart(cart).amount(10).build();
        order.setId(1);
        when(orderDAO.findById(anyInt())).thenReturn(of(order));
        when(orderDAO.save(any(Order.class))).thenReturn(order);
        Order order1 = orderService.update(order);
        assertNotNull(order1);
        verify(orderDAO,times(1)).findById(order.getId());
        verify(orderDAO,times(1)).save(any(Order.class));
    }
}
