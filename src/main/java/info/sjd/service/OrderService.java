package info.sjd.service;

import info.sjd.model.Cart;
import info.sjd.model.Order;

import java.util.List;

public interface OrderService {
    Order save(Order order);
    Order findById(Integer id);
    Order update(Order order);
    void delete(Order order);
    List<Order> findAll();

    List<Order> findAllByCart(Integer id);
    Order updateAmount(Integer id, Integer amount);
    Order findOrderByItem(Integer itemId);
}
