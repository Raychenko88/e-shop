package info.sjd.dao;

import info.sjd.model.Cart;
import info.sjd.model.Order;
import org.hibernate.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDAO extends JpaRepository<Order, Integer> {
    @Query(value = "SELECT * " +
            "FROM orders " +
            "WHERE cart_id=:id", nativeQuery = true)
    List<Order> getAllByCart(Integer id);

    @Query(value = "UPDATE orders SET " +
            "amount=:amountParam " +
            "WHERE id =:idParam",nativeQuery = true)
    Order updateAmount(Integer idParam, Integer amount);

    @Query(value = "SELECT * FROM orders o " +
            "JOIN carts c ON o.cart_id = c.id " +
            "JOIN items i ON o.item_id = i.id " +
            "WHERE c.closed = '0' " +
            "AND i.id =:itemId", nativeQuery = true)
    Order findOrderByItem(Integer itemId);

    @Query(value = "SELECT * FROM orders WHERE cart_id=:cartIdParam", nativeQuery = true)
    List<Order> findByCart(Integer cartIdParam);

}
