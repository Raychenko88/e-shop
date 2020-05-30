package info.sjd.controller;

import info.sjd.model.User;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.UriBuilder;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserControllerTest {

    @MockBean
    UserService userService;

    @Autowired
    TestRestTemplate testRestTemplate; // тестовый http клиент


    @Test
    void saveSuccessful() throws URISyntaxException {
        User user = User.builder().
                login("test_login111").
                password("test_pass").
                firstName("test_fn").
                lastName("test_ln").
                build();
        when(userService.save(any(User.class))).thenReturn(user);
        RequestEntity<User> requestEntity = new RequestEntity<>(user, HttpMethod.PUT, new URI("/user"));
        ResponseEntity<User> responseEntity = testRestTemplate.exchange(requestEntity, User.class);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
        verify(userService,times(1)).save(any(User.class));
    }

    @Test
    void updateSuccessful() throws URISyntaxException {
        User user = User.builder().
                login("test_login111").
                password("test_pass").
                firstName("test_fn").
                lastName("test_ln").
                build();
        when(userService.update(any(User.class))).thenReturn(user);
        RequestEntity<User> requestEntity = new RequestEntity<>(user, HttpMethod.POST, new URI("/user"));
        ResponseEntity<User> responseEntity = testRestTemplate.exchange(requestEntity, User.class);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
        verify(userService,times(1)).update(any(User.class));
    }

    @Test
    void updateUnsuccessful() throws URISyntaxException {
        when(userService.update(any(User.class))).thenReturn(null);
        RequestEntity<User> requestEntity = new RequestEntity<>(new User(), HttpMethod.POST, new URI("/user"));
        ResponseEntity<User> responseEntity = testRestTemplate.exchange(requestEntity, User.class);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.NOT_ACCEPTABLE);
        verify(userService,times(1)).update(any(User.class));
    }

    @Test
    void getByIdSuccessful() throws URISyntaxException {
        User user = User.builder().
                login("test_login111").
                password("test_pass").
                firstName("test_fn").
                lastName("test_ln").
                build();
        when(userService.findById(anyInt())).thenReturn(user);
        RequestEntity<User> requestEntity = new RequestEntity<>(new User(), HttpMethod.GET, new URI("/user/3"));
        ResponseEntity<User> responseEntity = testRestTemplate.exchange(requestEntity, User.class);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
        verify(userService,times(1)).findById(anyInt());
    }

    @Test
    void getAll() throws URISyntaxException {
        User user = User.builder().
                login("test_login111").
                password("test_pass").
                firstName("test_fn").
                lastName("test_ln").
                build();
        when(userService.findAll()).thenReturn(Collections.singletonList(user));
        RequestEntity<User> requestEntity = new RequestEntity<>(new User(), HttpMethod.GET, new URI("/user"));
        ResponseEntity<List> responseEntity = testRestTemplate.exchange(requestEntity, List.class);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
        verify(userService,times(1)).findAll();
    }

    @Test
    void getByLogin() throws URISyntaxException {
        User user = User.builder().
                login("test_login111").
                password("test_pass").
                firstName("test_fn").
                lastName("test_ln").
                build();
        when(userService.findByLogin(anyString())).thenReturn(user);
        UriComponents uriComponents = UriComponentsBuilder.newInstance()
                .host("/user")
                .path("/by-login").query("login={keyword}").buildAndExpand("log");
        RequestEntity<User> requestEntity = new RequestEntity<>(HttpMethod.GET, new URI(uriComponents.toString()));
        ResponseEntity<User> responseEntity = testRestTemplate.exchange(requestEntity, User.class);
        assertEquals(responseEntity.getStatusCode(),HttpStatus.OK);
        verify(userService,times(1)).findByLogin(anyString());
    }

    @Test
    void getByLoginAndPassword() throws URISyntaxException {
        User user = User.builder().
                login("test_login111").
                password("test_pass").
                firstName("test_fn").
                lastName("test_ln").
                build();
        when(userService.findByLoginAndPassword(anyString(), anyString())).thenReturn(user);
        UriComponents uriComponents = UriComponentsBuilder.newInstance()
                .host("/user")
                .path("by-login-and-password").query("login={keyword}").query("password={keyword}").buildAndExpand("log", "pass");
        RequestEntity<User> requestEntity = new RequestEntity<>(HttpMethod.GET, new URI(uriComponents.toString()));
        ResponseEntity<User> responseEntity = testRestTemplate.exchange(requestEntity, User.class);
        assertEquals(responseEntity.getStatusCode(),HttpStatus.OK);
        verify(userService,times(1)).findByLoginAndPassword(anyString(), anyString());
    }

    @Test
    void delete() throws URISyntaxException {
        User user = User.builder().
                login("test_login111").
                password("test_pass").
                firstName("test_fn").
                lastName("test_ln").
                build();
        when(userService.findById(anyInt())).thenReturn(user);
        RequestEntity<User> requestEntity = new RequestEntity<>(new User(), HttpMethod.DELETE, new URI("/user/3"));
        ResponseEntity<User> responseEntity = testRestTemplate.exchange(requestEntity, User.class);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
        verify(userService,times(1)).findById(anyInt());
    }
}