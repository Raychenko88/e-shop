package info.sjd.dao;

import info.sjd.model.Cart;
import info.sjd.model.Item;
import info.sjd.model.Order;
import info.sjd.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class OrderDAOTest {

    @Autowired
    OrderDAO orderDAO;
    @Autowired
    UserDAO userDAO;
    @Autowired
    CartDAO cartDAO;
    @Autowired
    ItemDAO itemDAO;

    private  Long currentTime = new Date().getTime();
    private List<Cart> carts;
    private List<User> users;
    private List<Item> items;
    private List<Order> orders;

    @BeforeEach
    void setUp() {
        carts = new ArrayList<>();
        users = new ArrayList<>();
        items = new ArrayList<>();
        orders = new ArrayList<>();
    }

    @AfterEach
    void tearDown() {
        orders.forEach(it -> orderDAO.delete(it));
        carts.forEach(it -> cartDAO.delete(it));
        users.forEach(it -> userDAO.delete(it));
        items.forEach(it -> itemDAO.delete(it));
    }

    @Test
    void getAllByCartTest() {
        User user = User.builder().login("test_login").password("test_pass").firstName("test_fn").lastName("test_ln").build();
        userDAO.save(user);
        users.add(user);
        assertNotNull(user.getId());

        Cart cart = Cart.builder().closed(0).user(user).creationTime(currentTime).build();
        Cart cartNotOk = Cart.builder().closed(0).user(user).creationTime(currentTime).build();
        cartDAO.save(cart);
        cartDAO.save(cartNotOk);
        carts.add(cart);
        carts.add(cartNotOk);
        assertNotNull(cart.getId());
        assertNotNull(cartNotOk.getId());

        Item item = Item.builder().name("test_name").code("t_code").price(123).availability(1).build();
        itemDAO.save(item);
        items.add(item);
        assertNotNull(item.getId());

        Order order1 = Order.builder().item(item).cart(cart).amount(50).build();
        Order order2 = Order.builder().item(item).cart(cart).amount(50).build();
        Order order3 = Order.builder().item(item).cart(cartNotOk).amount(50).build();
        orderDAO.save(order1);
        orderDAO.save(order2);
        orderDAO.save(order3);
        orders.add(order1);
        orders.add(order2);
        orders.add(order3);
        assertNotNull(order1.getId());
        assertNotNull(order2.getId());
        assertNotNull(order3.getId());

        List<Order> targetOrders = orderDAO.getAllByCart(cart.getId());
        assertTrue(targetOrders.size() >= 2);

        int count = 0;
        for (Order each:targetOrders){
            if ((order1.getId()).equals(each.getId())) {count++;}
            if ((order2.getId()).equals(each.getId())) {count++;}
            if ((order3.getId()).equals(each.getId())) {count++;}
        }
        assertEquals(2,count);

    }

    @Test
    void updateAmountTest() {
        User user = User.builder().login("test_login").password("test_pass").firstName("test_fn").lastName("test_ln").build();
        userDAO.save(user);
        users.add(user);
        assertNotNull(user.getId());

        Cart cart = Cart.builder().closed(0).user(user).creationTime(currentTime).build();
        cartDAO.save(cart);
        carts.add(cart);
        assertNotNull(cart.getId());

        Item item = Item.builder().name("test_name").code("t_code").price(50).availability(500).build();
        itemDAO.save(item);
        itemDAO.save(item);
        items.add(item);
        assertNotNull(item.getId());

        Order order =  Order.builder().item(item).cart(cart).amount(50).build();
        Order orderSave = orderDAO.save(order);
        orders.add(order);
        assertNotNull(orderSave.getId());

        Integer amount=100;
        orderDAO.updateAmount(order.getId(), amount);
        Order targetOrder = orderDAO.findById(orderSave.getId()).orElse(null);
        assertNotNull(targetOrder);
        int a = targetOrder.getAmount();
        assertEquals(100, targetOrder.getAmount());
    }

    @Test
    void findOrderByItemTest() {
        User user = User.builder().login("test_login").password("test_pass").firstName("test_fn").lastName("test_ln").build();
        userDAO.save(user);
        users.add(user);
        assertNotNull(user.getId());

        Cart cart = Cart.builder().closed(0).user(user).creationTime(currentTime).build();
        cartDAO.save(cart);
        carts.add(cart);
        assertNotNull(cart.getId());

        Item item = Item.builder().name("test_name").code("t_code").price(50).availability(500).build();
        itemDAO.save(item);
        items.add(item);
        assertNotNull(item.getId());

        Order order =  Order.builder().item(item).cart(cart).amount(50).build();
        orderDAO.save(order);
        orders.add(order);
        assertNotNull(order.getId());

        Order targetOrder = orderDAO.findOrderByItem(item.getId());
        assertNotNull(targetOrder);
        assertEquals(item.getId(), targetOrder.getItem().getId());
    }
}
