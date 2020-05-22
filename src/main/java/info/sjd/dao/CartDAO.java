package info.sjd.dao;

import info.sjd.model.Cart;
import info.sjd.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartDAO extends JpaRepository<Cart, Integer> {
//    List<Cart> getAllByUserAndPeriod(User user, Long timeFrom, Long timeTo);

//    Cart getByUserAndOpenStatus(User user, Integer status);

//    Cart updateStatus(Cart cart, Integer closed);
}
