package info.sjd.service.impl;

import info.sjd.dao.ItemDAO;
import info.sjd.model.Cart;
import info.sjd.model.Item;
import info.sjd.service.ItemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@Slf4j
public class ItemServiceImpl implements ItemService {

    @Autowired
    ItemDAO itemDAO;


    @Override
    public Item save(Item item) {
        if (item.getId() == null && item.getCode() != null){
            log.info("Item was created");
            return itemDAO.save(item);
        }
        return null;
    }

    @Override
    public Item findById(Integer id) {
        return itemDAO.findById(id).orElse(null);
    }

    @Override
    public Item update(Item item) {
        if (item != null && item.getId() != null){
            log.info("Item was updated");
            return itemDAO.save(item);
        }
        return null;
    }

    @Override
    public void delete(Item item) {
    itemDAO.delete(item);
    }

    @Override
    public List<Item> getAll() {
        return itemDAO.findAll();
    }

    @Override
    public List<Item> getByCode(String code) {
        return itemDAO.getByCode(code);
    }

    @Override
    public List<Item> getAllByCart(Integer cartId) {
        return itemDAO.getAllByCart(cartId);
    }

    @Override
    public List<Item> getAllAvailable() {
        return itemDAO.getAllAvailable();
    }
}
