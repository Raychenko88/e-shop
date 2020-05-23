package info.sjd.dao;

import info.sjd.model.Cart;
import info.sjd.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemDAO extends JpaRepository<Item, Integer> {
    List<Item> getByCode(String code);

    @Query(value = "SELECT ALL FROM items i " +
            "JOIN orders o ON o.item_id = i.id " +
            "JOIN carts c ON c.id = o.cart_id " +
            "WHERE c.id =:cartId ", nativeQuery = true)
    List<Item> getAllByCart(Integer cartId);

    @Query(value = "SELECT * FROM items WHERE availability > 0",nativeQuery = true)
    List<Item> getAllAvailable();
}
