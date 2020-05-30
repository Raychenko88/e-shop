package info.sjd.controller;

import info.sjd.model.Cart;
import info.sjd.model.User;
import info.sjd.service.CartService;
import info.sjd.service.UserService;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CartControllerTest {

    private Long currentTime = new Date().getTime();

    @MockBean
    CartService cartService;

    @MockBean
    UserService userService;

    @Autowired
    TestRestTemplate testRestTemplate;

    @Test
    void saveSuccessful() throws URISyntaxException {
        User user = User.builder().
                login("test_login111").
                password("test_pass").
                firstName("test_fn").
                lastName("test_ln").
                build();
        Cart cart = Cart.builder().
                closed(0).user(user).
                creationTime(currentTime).
                build();
        when(cartService.save(any(Cart.class))).thenReturn(cart);
        RequestEntity<Cart> requestEntity = new RequestEntity<>(cart, HttpMethod.PUT, new URI("/cart"));
        ResponseEntity<Cart> responseEntity = testRestTemplate.exchange(requestEntity, Cart.class);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
        verify(cartService,times(1)).save(any(Cart.class));
    }

    @Test
    void saveUnsuccessful() throws URISyntaxException {
        when(cartService.save(any(Cart.class))).thenReturn(null);
        RequestEntity<Cart> requestEntity = new RequestEntity<>(new Cart(), HttpMethod.PUT, new URI("/cart"));
        ResponseEntity<Cart> responseEntity = testRestTemplate.exchange(requestEntity, Cart.class);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.NOT_ACCEPTABLE);
        verify(cartService,times(1)).save(any(Cart.class));
    }

    @Test
    void updateSuccessful() throws URISyntaxException {
        User user = User.builder().
                login("test_login111").
                password("test_pass").
                firstName("test_fn").
                lastName("test_ln").
                build();
        Cart cart = Cart.builder().
                closed(0).user(user).
                creationTime(currentTime).
                build();
        when(cartService.update(any(Cart.class))).thenReturn(cart);
        RequestEntity<Cart> requestEntity = new RequestEntity<>(cart, HttpMethod.POST, new URI("/cart"));
        ResponseEntity<Cart> responseEntity = testRestTemplate.exchange(requestEntity, Cart.class);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
        verify(cartService,times(1)).update(any(Cart.class));
    }

    @Test
    void updateUnsuccessful() throws URISyntaxException {
        when(cartService.update(any(Cart.class))).thenReturn(null);
        RequestEntity<Cart> requestEntity = new RequestEntity<>(new Cart(), HttpMethod.POST, new URI("/cart"));
        ResponseEntity<Cart> responseEntity = testRestTemplate.exchange(requestEntity, Cart.class);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.NOT_ACCEPTABLE);
        verify(cartService,times(1)).update(any(Cart.class));
    }

    @Test
    void getById() throws URISyntaxException {
        User user = User.builder().
                login("test_login111").
                password("test_pass").
                firstName("test_fn").
                lastName("test_ln").
                build();
        Cart cart = Cart.builder().
                closed(0).user(user).
                creationTime(currentTime).
                build();
        when(cartService.findById(anyInt())).thenReturn(cart);
        RequestEntity<Cart> requestEntity = new RequestEntity<>(cart, HttpMethod.GET, new URI("/cart/3"));
        ResponseEntity<Cart> responseEntity = testRestTemplate.exchange(requestEntity, Cart.class);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
        verify(cartService,times(1)).findById(anyInt());
    }

    @Test
    void getAll() throws URISyntaxException {
        User user = User.builder().
                login("test_login111").
                password("test_pass").
                firstName("test_fn").
                lastName("test_ln").
                build();
        Cart cart = Cart.builder().
                closed(0).user(user).
                creationTime(currentTime).
                build();
        when(cartService.findAll()).thenReturn(Collections.singletonList(cart));
        RequestEntity<Cart> requestEntity = new RequestEntity<>(new Cart(), HttpMethod.GET, new URI("/cart"));
        ResponseEntity<List> responseEntity = testRestTemplate.exchange(requestEntity, List.class);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
        verify(cartService,times(1)).findAll();
    }

    @Test
    void delete() throws URISyntaxException {
        User user = User.builder().
                login("test_login111").
                password("test_pass").
                firstName("test_fn").
                lastName("test_ln").
                build();
        Cart cart = Cart.builder().
                closed(0).user(user).
                creationTime(currentTime).
                build();
        when(cartService.findById(anyInt())).thenReturn(cart);
        RequestEntity<Cart> requestEntity = new RequestEntity<>(cart, HttpMethod.DELETE, new URI("/cart/3"));
        ResponseEntity<Cart> responseEntity = testRestTemplate.exchange(requestEntity, Cart.class);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
        verify(cartService,times(1)).findById(anyInt());
    }

    @Test
    void getAllByUserAndPeriod() throws URISyntaxException {
        User user = User.builder().
                login("test_login111").
                password("test_pass").
                firstName("test_fn").
                lastName("test_ln").
                build();
        Cart cart = Cart.builder().
                closed(0).user(user).
                creationTime(currentTime).
                build();
        when(cartService.getAllByUserAndPeriod(anyInt(), anyLong(), anyLong())).thenReturn(Collections.singletonList(cart));
        UriComponents uriComponents = UriComponentsBuilder.newInstance()
                .host("/cart")
                .path("get_all_by_user_and_period").query("userId={keyword}").query("timeDown={keyword}").
                        query("timeUp={keyword}").buildAndExpand("3", "111111111", "333333333");
        RequestEntity<Cart> requestEntity = new RequestEntity<>(HttpMethod.GET, new URI(uriComponents.toString()));
        ResponseEntity<List> responseEntity = testRestTemplate.exchange(requestEntity, List.class);
        assertEquals(responseEntity.getStatusCode(),HttpStatus.OK);
        verify(cartService,times(1)).getAllByUserAndPeriod(anyInt(), anyLong(), anyLong());
    }

    @Test
    void getByUserAndOpenStatus() throws URISyntaxException {
        User user = User.builder().
                login("test_login111").
                password("test_pass").
                firstName("test_fn").
                lastName("test_ln").
                build();
        Cart cart = Cart.builder().
                closed(0).user(user).
                creationTime(currentTime).
                build();
        when(cartService.getByUserAndOpenStatus(anyInt())).thenReturn(cart);
        UriComponents uriComponents = UriComponentsBuilder.newInstance()
                .host("/cart")
                .path("open_status").query("id={keyword}").buildAndExpand("3");
        RequestEntity<Cart> requestEntity = new RequestEntity<>(HttpMethod.GET, new URI(uriComponents.toString()));
        ResponseEntity<Cart> responseEntity = testRestTemplate.exchange(requestEntity, Cart.class);
        assertEquals(responseEntity.getStatusCode(),HttpStatus.OK);
        verify(cartService,times(1)).getByUserAndOpenStatus(anyInt());
    }

    @Test
    void updateStatusSuccessful() throws URISyntaxException {
        User user = User.builder().
                login("test_login111").
                password("test_pass").
                firstName("test_fn").
                lastName("test_ln").
                build();
        Cart cart = Cart.builder().
                closed(0).user(user).
                creationTime(currentTime).
                build();
        when(cartService.findById(anyInt())).thenReturn(cart);
        doNothing().when(cartService).updateStatus(isA(Integer.class), isA(Integer.class));
        UriComponents uriComponents = UriComponentsBuilder.newInstance()
                .host("/cart")
                .path("update_status").query("idParam={keyword}").
                        query("closedParam={keyword}").buildAndExpand("3", "1");
        RequestEntity<Cart> requestEntity = new RequestEntity<>(HttpMethod.POST, new URI(uriComponents.toString()));
        ResponseEntity<Cart> responseEntity = testRestTemplate.exchange(requestEntity, Cart.class);
        assertEquals(responseEntity.getStatusCode(),HttpStatus.OK);
        verify(cartService,times(1)).findById(anyInt());
        verify(cartService,times(1)).updateStatus(anyInt(), anyInt());
    }

    @Test
    void updateStatusUnsuccessful() throws URISyntaxException {
        when(cartService.findById(anyInt())).thenReturn(null);
        UriComponents uriComponents = UriComponentsBuilder.newInstance()
                .host("/cart")
                .path("update_status").query("idParam={keyword}").
                        query("closedParam={keyword}").buildAndExpand("3", "1");
        RequestEntity<Cart> requestEntity = new RequestEntity<>(HttpMethod.POST, new URI(uriComponents.toString()));
        ResponseEntity<Cart> responseEntity = testRestTemplate.exchange(requestEntity, Cart.class);
        assertEquals(responseEntity.getStatusCode(),HttpStatus.NOT_ACCEPTABLE);
        verify(cartService,times(1)).findById(anyInt());
    }
}