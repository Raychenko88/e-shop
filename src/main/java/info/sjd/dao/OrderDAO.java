package info.sjd.dao;

import info.sjd.model.Cart;
import info.sjd.model.Order;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDAO extends JpaRepository<Order, Integer> {
//    List<Order> getAllByCart(Cart cart);

//    Order updateAmount(Order order, Integer amount);

//    Order findOrderByItem(Integer itemId);

//    List<Order> findByCart(Integer cartId);

}
