package info.sjd.dao;

import info.sjd.model.Cart;
import info.sjd.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemDAO extends JpaRepository<Item, Integer> {
    List<Item> getByCode(String code);

//    List<Item> getAllByCart(Cart cart);

//    List<Item> getAllAvailable();
}
