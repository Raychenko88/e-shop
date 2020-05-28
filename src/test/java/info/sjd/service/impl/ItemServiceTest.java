package info.sjd.service.impl;

import info.sjd.dao.ItemDAO;
import info.sjd.model.Item;
import info.sjd.service.ItemService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static java.util.Optional.of;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class ItemServiceTest {

    @Autowired
    ItemServiceImpl itemService;

    @MockBean
    ItemDAO itemDAO;

    @Test
    void saveTest() {
        Item item = Item.builder().name("test_name").code("t_code").price(123).availability(1).build();
        when(itemDAO.save(any(Item.class))).thenReturn(item);
        Item item1 = itemService.save(item);
        assertNotNull(item1);
        assertEquals(item.getName(), item1.getName());
        verify(itemDAO,times(1)).save(any(Item.class));
    }

    @Test
    void updateTest() {
        Item item = Item.builder().name("test_name").code("t_code").price(123).availability(1).build();
        item.setId(1);
        when(itemDAO.findById(anyInt())).thenReturn(of(item));
        when(itemDAO.save(any(Item.class))).thenReturn(item);
        Item item1 = itemService.update(item);
        assertNotNull(item1);
        verify(itemDAO,times(1)).findById(anyInt());
        verify(itemDAO,times(1)).save(any(Item.class));
    }

}
