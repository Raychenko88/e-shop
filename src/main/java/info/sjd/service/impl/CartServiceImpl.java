package info.sjd.service.impl;


import info.sjd.dao.CartDAO;
import info.sjd.model.Cart;
import info.sjd.model.User;
import info.sjd.service.CartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class CartServiceImpl implements CartService {

    @Autowired
    CartDAO cartDAO;

    @Override
    public Cart save(Cart cart) {
        if (cart.getId() == null){
            log.info("Cart was created");
            return cartDAO.save(cart);
        }
        return null;
    }

    @Override
    public Cart findById(Integer id) {
        return cartDAO.findById(id).orElse(null);
    }

    @Override
    public Cart update(Cart cart) {
        if (cart.getId() != null){
            log.info("Cart was updated");
            return cartDAO.save(cart);
        }
        return null;
    }

    @Override
    public void delete(Cart cart) {
        cartDAO.delete(cart);

    }

    public List<Cart> findAll() {
        return cartDAO.findAll();
    }

    @Override
    public List<Cart> getAllByUserAndPeriod(User user, Long timeFrom, Long timeTo) {
        return cartDAO.getAllByUserAndPeriod(user, timeFrom, timeTo);
    }

    @Override
    public Cart getByUserAndOpenStatus(User user) {
        return cartDAO.getByUserAndOpenStatus(user);
    }

    @Override
    public Cart updateStatus(Cart cart, Integer closed) {
        if (cart.getId() != null){
            return cartDAO.updateStatus(cart, closed);
        }
        return null;
    }
}
