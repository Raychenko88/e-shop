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
class CartDAOTest {

    @Autowired
    CartDAO cartDAO;
    @Autowired
    UserDAO userDAO;
    @Autowired
    OrderDAO orderDAO;
    @Autowired
    private ItemDAO itemDAO;
    private long currentTime = new Date().getTime();
    private List<Cart> carts;
    private  List<User> users;
    private  List<Item> items;
    private  List<Order> orders;

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
    void getAllByUserAndPeriod() {
        User userOk = User.builder().login("test_login").password("test_pass").firstName("test_fn").lastName("test_ln").build();
        User userNotOk = User.builder().login("login2").password("pass2").firstName("first_name2").lastName("last_name2").build();
        userDAO.save(userOk);
        userDAO.save(userNotOk);
        users.add(userOk);
        users.add(userNotOk);
        assertNotNull(userOk.getId());
        assertNotNull(userNotOk.getId());

        Long periodFrom = currentTime - 100;
        Long periodTo = currentTime;
        Long timeOk = currentTime - 50;
        Long timeNotOk = currentTime - 200;

        Cart cartOk = Cart.builder().closed(0).user(userOk).creationTime(timeOk).build();
        Cart cartNotOk1 = Cart.builder().closed(0).user(userOk).creationTime(timeNotOk).build();
        Cart cartNotOk2 = Cart.builder().closed(0).user(userNotOk).creationTime(timeOk).build();

        cartDAO.save(cartOk);
        cartDAO.save(cartNotOk1);
        cartDAO.save(cartNotOk2);
        carts.add(cartOk);
        carts.add(cartNotOk1);
        carts.add(cartNotOk2);
        assertNotNull(cartOk.getId());
        assertNotNull(cartNotOk1.getId());
        assertNotNull(cartNotOk2.getId());

        List<Cart> targetCarts = cartDAO.getAllByUserAndPeriod(userOk.getId(), periodFrom, periodTo);
        assertTrue(targetCarts.size() >= 1);

        boolean isInCollectionCartOk = false;
        boolean isInCollectionCartNotOk1 = false;
        boolean isInCollectionCartNotOk2 = false;

        for (Cart each : targetCarts) {
            if ((cartOk.getId()).equals(each.getId())) {
                isInCollectionCartOk = true;
            }
            if ((cartNotOk1.getId()).equals(each.getId())) {
                isInCollectionCartNotOk1 = true;
            }
            if ((cartNotOk2.getId()).equals(each.getId())) {
                isInCollectionCartNotOk2 = true;
            }
        }
        assertTrue(isInCollectionCartOk);
        assertTrue(!isInCollectionCartNotOk1);
        assertTrue(!isInCollectionCartNotOk2);
    }

    @Test
    void getByUserAndOpenStatus() {
        User userOk = User.builder().login("test_login").password("test_pass").firstName("test_fn").lastName("test_ln").build();
        User userNotOk = User.builder().login("login2").password("pass2").firstName("first_name2").lastName("last_name2").build();
        userDAO.save(userOk);
        userDAO.save(userNotOk);
        users.add(userOk);
        users.add(userNotOk);
        assertNotNull(userOk.getId());
        assertNotNull(userNotOk.getId());

        Cart cartOk = Cart.builder().closed(0).user(userOk).creationTime(currentTime).build();
        Cart cartNotOk1 = Cart.builder().closed(1).user(userOk).creationTime(currentTime).build();
        Cart cartNotOk2 = Cart.builder().closed(0).user(userNotOk).creationTime(currentTime).build();

        cartDAO.save(cartOk);
        cartDAO.save(cartNotOk1);
        cartDAO.save(cartNotOk2);
        carts.add(cartOk);
        carts.add(cartNotOk1);
        carts.add(cartNotOk2);
        assertNotNull(cartOk.getId());
        assertNotNull(cartNotOk1.getId());
        assertNotNull(cartNotOk2.getId());

        Cart targetCart = cartDAO.getByUserAndOpenStatus(userOk.getId());
        assertNotNull(targetCart);
        assertNotNull(targetCart.getUser().getId());
        assertEquals(targetCart.getUser().getId(), userOk.getId());
        assertEquals(targetCart.getId(), cartOk.getId());
    }

    @Test
    void updateStatus() {
        User user = User.builder().login("test_login").password("test_pass").firstName("test_fn").lastName("test_ln").build();
        userDAO.save(user);
        users.add(user);
        assertNotNull(user.getId());

        Cart cart = Cart.builder().closed(0).user(user).creationTime(currentTime).build();
        Cart saveCart =  cartDAO.save(cart);
        carts.add(cart);
        assertNotNull(saveCart);
        assertEquals(0, saveCart.getClosed());

        cartDAO.updateStatus(cart.getId(), 1);
        Cart cartUpdate = cartDAO.findById(cart.getId()).orElse(null);
        assertNotNull(cartUpdate);
        assertEquals(1, cartUpdate.getClosed());
    }
}
