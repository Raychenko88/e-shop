package info.sjd.service;

import info.sjd.model.Cart;
import info.sjd.model.Item;

import java.util.List;

public interface ItemService {
    Item save(Item item);
    Item findById(Integer id);
    Item update(Item item);
    void delete(Item item);
    List<Item> getAll();
    List<Item> getByCode(String code);
    List<Item> getAllByCart(Cart cart);
    List<Item> getAllAvailable();
}
