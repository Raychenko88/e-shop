package info.sjd.service;

import info.sjd.model.Cart;
import info.sjd.model.User;

import java.util.List;

public interface CartService {

    Cart save(Cart cart);
    Cart findById(Integer id);
    Cart update(Cart cart);
    void delete(Cart cart);
    List<Cart> findAll();
//    List<Cart> getAllByUserAndPeriod(User user, Long timeFrom, Long timeTo);
//    Cart getByUserAndOpenStatus(User user);
//    Cart updateStatus(Cart cart, Integer closed);
}
