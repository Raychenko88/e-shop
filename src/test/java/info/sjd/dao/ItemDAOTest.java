package info.sjd.dao;

import info.sjd.model.Cart;
import info.sjd.model.Item;
import info.sjd.model.Order;
import info.sjd.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ItemDAOTest {

    @Autowired
    ItemDAO itemDAO;
    @Autowired
    OrderDAO orderDAO;
    @Autowired
    CartDAO cartDAO;
    @Autowired
    UserDAO userDAO;
    private long current_time = new Date().getTime();

    @Test
    void getByCodeTest() {
        Item item = Item.builder().name("test_name").code("t_code").price(123).availability(1).build();
        itemDAO.save(item);
        List<Item> list = itemDAO.getByCode(item.getCode());
        assertNotNull(list);
        assertFalse(list.isEmpty());
        assertNotNull(list.get(0));
        assertEquals(list.get(0).getCode(), item.getCode());
        itemDAO.delete(item);
    }

    @Test
    void getAllByCartTest() {
        Item item1 = Item.builder().name("test_name").code("t_code").price(123).availability(1).build();
        itemDAO.save(item1);
        User user = User.builder().login("test_login").password("test_pass").firstName("test_fn").lastName("test_ln").build();
        userDAO.save(user);
        Cart cart = Cart.builder().closed(0).user(user).creationTime(current_time).build();
        cartDAO.save(cart);
        Order order = Order.builder().item(item1).cart(cart).amount(5).build();
        orderDAO.save(order);
        List<Item> list = itemDAO.getAllByCart(cart.getId());
        assertNotNull(list);
        assertFalse(list.isEmpty());
        assertNotNull(list.get(0));
        assertEquals(list.get(0).getName(), item1.getName());
        orderDAO.delete(order);
        cartDAO.delete(cart);
        itemDAO.delete(item1);
        userDAO.delete(user);
    }

    @Test
    void getAllAvailableTest() {
        Item item1 = Item.builder().name("test_name").code("test_code").price(123).availability(1).build();
        Item item2 = Item.builder().name("t_name").code("t_code").price(321).availability(2).build();
        itemDAO.save(item1);
        itemDAO.save(item2);
        List<Item> list = itemDAO.getAllAvailable();
        assertNotNull(list);
        assertFalse(list.isEmpty());
        itemDAO.delete(item1);
        itemDAO.delete(item2);
    }
}
