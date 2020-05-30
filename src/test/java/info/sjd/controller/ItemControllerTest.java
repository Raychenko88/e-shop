package info.sjd.controller;

import info.sjd.model.Item;
import info.sjd.model.User;
import info.sjd.service.ItemService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ItemControllerTest {

    @MockBean
    ItemService itemService;

    @Autowired
    TestRestTemplate testRestTemplate;


    @Test
    void saveSuccessfulTest() throws URISyntaxException {
        Item item = Item.builder().
                name("test_name").
                code("t_code").
                price(123).
                availability(1).
                build();
        when(itemService.save(any(Item.class))).thenReturn(item);
        RequestEntity<Item> requestEntity = new RequestEntity<>(item, HttpMethod.PUT, new URI("/item"));
        ResponseEntity<Item> responseEntity = testRestTemplate.exchange(requestEntity, Item.class);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
        verify(itemService, times(1)).save(any(Item.class));
    }

    @Test
    void saveUnsuccessfulTest() throws URISyntaxException {
        when(itemService.save(any(Item.class))).thenReturn(null);
        RequestEntity<Item> requestEntity = new RequestEntity<>(new Item(), HttpMethod.PUT, new URI("/item"));
        ResponseEntity<Item> responseEntity = testRestTemplate.exchange(requestEntity, Item.class);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.NOT_ACCEPTABLE);
        verify(itemService, times(1)).save(any(Item.class));
    }

    @Test
    void updateSuccessfulTest() throws URISyntaxException {
        Item item = Item.builder().
                name("test_name").
                code("t_code").
                price(123).
                availability(1).
                build();
        when(itemService.update(any(Item.class))).thenReturn(item);
        RequestEntity<Item> requestEntity = new RequestEntity<>(item, HttpMethod.POST, new URI("/item"));
        ResponseEntity<Item> responseEntity = testRestTemplate.exchange(requestEntity, Item.class);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
        verify(itemService, times(1)).update(any(Item.class));
    }

    @Test
    void updateUnsuccessfulTest() throws URISyntaxException {
        when(itemService.update(any(Item.class))).thenReturn(null);
        RequestEntity<Item> requestEntity = new RequestEntity<>(new Item(), HttpMethod.POST, new URI("/item"));
        ResponseEntity<Item> responseEntity = testRestTemplate.exchange(requestEntity, Item.class);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.NOT_ACCEPTABLE);
        verify(itemService, times(1)).update(any(Item.class));
    }

    @Test
    void getByIdTest() throws URISyntaxException {
        Item item = Item.builder().
                name("test_name").
                code("t_code").
                price(123).
                availability(1).
                build();
        when(itemService.findById(anyInt())).thenReturn(item);
        RequestEntity<Item> requestEntity = new RequestEntity<>(item, HttpMethod.GET, new URI("/item/3"));
        ResponseEntity<Item> responseEntity = testRestTemplate.exchange(requestEntity, Item.class);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
        verify(itemService, times(1)).findById(anyInt());
    }

    @Test
    void getAllTest() throws URISyntaxException {
        Item item = Item.builder().
                name("test_name").
                code("t_code").
                price(123).
                availability(1).
                build();
        when(itemService.getAll()).thenReturn(Collections.singletonList(item));
        RequestEntity<Item> requestEntity = new RequestEntity<>(new Item(), HttpMethod.GET, new URI("/item"));
        ResponseEntity<List> responseEntity = testRestTemplate.exchange(requestEntity, List.class);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
        verify(itemService, times(1)).getAll();
    }

    @Test
    void getByCodeTest() throws URISyntaxException {
        Item item = Item.builder().
                name("test_name").
                code("t_code").
                price(123).
                availability(1).
                build();
        when(itemService.getByCode(anyString())).thenReturn(Collections.singletonList(item));
        UriComponents uriComponents = UriComponentsBuilder.newInstance()
                .host("/item")
                .path("by-code").query("code={keyword}").buildAndExpand("code");
        RequestEntity<Item> requestEntity = new RequestEntity<>(HttpMethod.GET, new URI(uriComponents.toString()));
        ResponseEntity<List> responseEntity = testRestTemplate.exchange(requestEntity, List.class);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
        verify(itemService, times(1)).getByCode(anyString());
    }

    @Test
    void getAllByCartTest() throws URISyntaxException {
        Item item = Item.builder().
                name("test_name").
                code("t_code").
                price(123).
                availability(1).
                build();
        when(itemService.getAllByCart(anyInt())).thenReturn(Collections.singletonList(item));
        UriComponents uriComponents = UriComponentsBuilder.newInstance()
                .host("/item")
                .path("cart_id").query("id={keyword}").buildAndExpand("23");
        RequestEntity<Item> requestEntity = new RequestEntity<>(HttpMethod.GET, new URI(uriComponents.toString()));
        ResponseEntity<List> responseEntity = testRestTemplate.exchange(requestEntity, List.class);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
        verify(itemService, times(1)).getAllByCart(anyInt());
    }

    @Test
    void getAllAvailableTest() throws URISyntaxException {
        Item item = Item.builder().
                name("test_name").
                code("t_code").
                price(123).
                availability(1).
                build();
        when(itemService.getAllAvailable()).thenReturn(Collections.singletonList(item));
        UriComponents uriComponents = UriComponentsBuilder.newInstance()
                .host("/item")
                .path("all-available").build();
        RequestEntity<Item> requestEntity = new RequestEntity<>(HttpMethod.GET, new URI(uriComponents.toString()));
        ResponseEntity<List> responseEntity = testRestTemplate.exchange(requestEntity, List.class);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
        verify(itemService, times(1)).getAllAvailable();
    }

    @Test
    void deleteTest() throws URISyntaxException {
        Item item = Item.builder().
                name("test_name").
                code("t_code").
                price(123).
                availability(1).
                build();
        when(itemService.findById(anyInt())).thenReturn(item);
        doNothing().when(itemService).delete(isA(Item.class));
        RequestEntity<Item> requestEntity = new RequestEntity<>(item, HttpMethod.DELETE, new URI("/item/3"));
        ResponseEntity<Item> responseEntity = testRestTemplate.exchange(requestEntity, Item.class);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
        verify(itemService, times(1)).findById(anyInt());
        verify(itemService, times(1)).delete(any(Item.class));
    }
}
