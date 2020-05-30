package info.sjd.controller;

import info.sjd.model.Cart;
import info.sjd.model.Item;
import info.sjd.model.Order;
import info.sjd.model.User;
import info.sjd.service.OrderService;
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
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class OrderControllerTest {

    @MockBean
    OrderService orderService;

    @Autowired
    TestRestTemplate testRestTemplate;

    @Test
    void saveSuccessfulTest() throws URISyntaxException {
        User user = new User();
        Cart cart = new Cart();
        Item item = new Item();
        Order order = Order.builder().
                item(item).
                cart(cart).
                amount(10).
                build();
        when(orderService.save(any(Order.class))).thenReturn(order);
        RequestEntity<Order> requestEntity = new RequestEntity<>(order, HttpMethod.PUT, new URI("/order"));
        ResponseEntity<Order> responseEntity = testRestTemplate.exchange(requestEntity, Order.class);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
        verify(orderService, times(1)).save(any(Order.class));
    }

    @Test
    void saveUnsuccessfulTest() throws URISyntaxException {
        when(orderService.save(any(Order.class))).thenReturn(null);
        RequestEntity<Order> requestEntity = new RequestEntity<>(new Order(), HttpMethod.PUT, new URI("/order"));
        ResponseEntity<Order> responseEntity = testRestTemplate.exchange(requestEntity, Order.class);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.NOT_ACCEPTABLE);
        verify(orderService, times(1)).save(any(Order.class));
    }

    @Test
    void updateSuccessfulTest() throws URISyntaxException {
        User user = new User();
        Cart cart = new Cart();
        Item item = new Item();
        Order order = Order.builder().
                item(item).
                cart(cart).
                amount(10).
                build();
        when(orderService.update(any(Order.class))).thenReturn(order);
        RequestEntity<Order> requestEntity = new RequestEntity<>(order, HttpMethod.POST, new URI("/order"));
        ResponseEntity<Order> responseEntity = testRestTemplate.exchange(requestEntity, Order.class);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
        verify(orderService, times(1)).update(any(Order.class));
    }

    @Test
    void updateUnsuccessfulTest() throws URISyntaxException {
        when(orderService.update(any(Order.class))).thenReturn(null);
        RequestEntity<Order> requestEntity = new RequestEntity<>(new Order(), HttpMethod.POST, new URI("/order"));
        ResponseEntity<Order> responseEntity = testRestTemplate.exchange(requestEntity, Order.class);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.NOT_ACCEPTABLE);
        verify(orderService, times(1)).update(any(Order.class));
    }

    @Test
    void findByIdTest() throws URISyntaxException {
        User user = new User();
        Cart cart = new Cart();
        Item item = new Item();
        Order order = Order.builder().
                item(item).
                cart(cart).
                amount(10).
                build();
        when(orderService.findById(anyInt())).thenReturn(order);
        RequestEntity<Order> requestEntity = new RequestEntity<>(order, HttpMethod.GET, new URI("/order/3"));
        ResponseEntity<Order> responseEntity = testRestTemplate.exchange(requestEntity, Order.class);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
        verify(orderService, times(1)).findById(anyInt());
    }

    @Test
    void findAllTest() throws URISyntaxException {
        User user = new User();
        Cart cart = new Cart();
        Item item = new Item();
        Order order = Order.builder().
                item(item).
                cart(cart).
                amount(10).
                build();
        when(orderService.findAll()).thenReturn(Collections.singletonList(order));
        RequestEntity<Order> requestEntity = new RequestEntity<>(order, HttpMethod.GET, new URI("/order"));
        ResponseEntity<List> responseEntity = testRestTemplate.exchange(requestEntity, List.class);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
        verify(orderService, times(1)).findAll();
    }

    @Test
    void deleteTest() throws URISyntaxException {
        User user = new User();
        Cart cart = new Cart();
        Item item = new Item();
        Order order = Order.builder().
                item(item).
                cart(cart).
                amount(10).
                build();
        when(orderService.findById(anyInt())).thenReturn(order);
        doNothing().when(orderService).delete(isA(Order.class));
        RequestEntity<Order> requestEntity = new RequestEntity<>(order, HttpMethod.DELETE, new URI("/order/3"));
        ResponseEntity<Order> responseEntity = testRestTemplate.exchange(requestEntity, Order.class);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
        verify(orderService, times(1)).findById(anyInt());
        verify(orderService, times(1)).delete(isA(Order.class));
    }

    @Test
    void findAllByCartTest() throws URISyntaxException {
        User user = new User();
        Cart cart = new Cart();
        Item item = new Item();
        Order order = Order.builder().
                item(item).
                cart(cart).
                amount(10).
                build();
        when(orderService.findAllByCart(anyInt())).thenReturn(Collections.singletonList(order));
        UriComponents uriComponents = UriComponentsBuilder.newInstance()
                .host("/order")
                .path("find_all_by_cart").query("id={keyword}").buildAndExpand("3");
        RequestEntity<Order> requestEntity = new RequestEntity<>(order, HttpMethod.GET, new URI(uriComponents.toString()));
        ResponseEntity<List> responseEntity = testRestTemplate.exchange(requestEntity, List.class);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
        verify(orderService, times(1)).findAllByCart(anyInt());
    }

    @Test
    void updateAmountSuccessful() throws URISyntaxException {
        User user = new User();
        Cart cart = new Cart();
        Item item = new Item();
        Order order = Order.builder().
                item(item).
                cart(cart).
                amount(10).
                build();
        when(orderService.findById(anyInt())).thenReturn(order);
        doNothing().when(orderService).updateAmount(isA(Integer.class), isA(Integer.class));
        UriComponents uriComponents = UriComponentsBuilder.newInstance()
                .host("/order")
                .path("update_amount").query("id={keyword}").
                        query("amount={keyword}").buildAndExpand("3", "1");
        RequestEntity<Order> requestEntity = new RequestEntity<>(HttpMethod.POST, new URI(uriComponents.toString()));
        ResponseEntity<Order> responseEntity = testRestTemplate.exchange(requestEntity, Order.class);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
        verify(orderService, times(1)).findById(anyInt());
        verify(orderService, times(1)).updateAmount(anyInt(), anyInt());
    }

    @Test
    void updateAmountUnsuccessful() throws URISyntaxException {
        when(orderService.findById(anyInt())).thenReturn(null);
        UriComponents uriComponents = UriComponentsBuilder.newInstance()
                .host("/order")
                .path("update_amount").query("id={keyword}").
                        query("amount={keyword}").buildAndExpand("3", "1");
        RequestEntity<Order> requestEntity = new RequestEntity<>(HttpMethod.POST, new URI(uriComponents.toString()));
        ResponseEntity<Order> responseEntity = testRestTemplate.exchange(requestEntity, Order.class);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.NOT_ACCEPTABLE);
        verify(orderService, times(1)).findById(anyInt());
    }

    @Test
    void findOrderByItem() throws URISyntaxException {
        User user = new User();
        Cart cart = new Cart();
        Item item = new Item();
        Order order = Order.builder().
                item(item).
                cart(cart).
                amount(10).
                build();
        when(orderService.findOrderByItem(anyInt())).thenReturn(order);
        UriComponents uriComponents = UriComponentsBuilder.newInstance()
                .host("/order")
                .path("find_order_by_item").query("itemId={keyword}").buildAndExpand("3");
        RequestEntity<Order> requestEntity = new RequestEntity<>(HttpMethod.GET, new URI(uriComponents.toString()));
        ResponseEntity<Order> responseEntity = testRestTemplate.exchange(requestEntity, Order.class);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
        verify(orderService, times(1)).findOrderByItem(anyInt());
    }
}
