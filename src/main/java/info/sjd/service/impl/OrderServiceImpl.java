package info.sjd.service.impl;

import info.sjd.dao.CartDAO;
import info.sjd.dao.OrderDAO;
import info.sjd.model.Item;
import info.sjd.model.Order;
import info.sjd.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderDAO orderDAO;

    @Override
    public Order save(Order order) {
        if (order.getId() == null){
            log.info("Order was created");
            return orderDAO.save(order);
        }
        return null;
    }

    @Override
    public Order findById(Integer id) {
        return orderDAO.findById(id).orElse(null);
    }

    @Override
    public Order update(Order order) {
        if (order.getId() != null){
            log.info("Order was updated");
            return orderDAO.save(order);
        }
        return null;
    }

    @Override
    public void delete(Order order) {
        orderDAO.delete(order);
    }

    @Override
    public List<Order> findAll() {
        return orderDAO.findAll();
    }

    @Override
    public List<Order> findAllByCart(Integer id) {
        return orderDAO.getAllByCart(id);
    }

    @Override
    public void updateAmount(Integer id, Integer amount) {
        orderDAO.updateAmount(id,amount);
    }

    @Override
    public Order findOrderByItem(Integer itemId) {
        return orderDAO.findOrderByItem(itemId);
    }
}
