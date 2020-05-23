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
    List<Cart> getAllByUserAndPeriod(Integer userId, Long timeDown, Long timUp);
    Cart getByUserAndOpenStatus(Integer id, Integer status);
    Cart updateStatus(Integer idParam, Integer closedParam);
}
