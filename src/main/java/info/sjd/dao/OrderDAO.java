package info.sjd.dao;

import info.sjd.model.Cart;
import info.sjd.model.Order;
import org.hibernate.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface OrderDAO extends JpaRepository<Order, Integer> {
    @Query(value = "SELECT * " +
            "FROM orders " +
            "WHERE cart_id=:id", nativeQuery = true)
    List<Order> getAllByCart(Integer id);

    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE orders SET " +
            "amount=:amountParam " +
            "WHERE id =:idParam",nativeQuery = true)
    void updateAmount(Integer idParam, Integer amountParam);

    @Query(value = "SELECT * FROM orders o " +
            "JOIN carts c ON o.cart_id = c.id " +
            "JOIN items i ON o.item_id = i.id " +
            "WHERE c.closed = '0' " +
            "AND i.id =:itemId", nativeQuery = true)
    Order findOrderByItem(Integer itemId);
}
