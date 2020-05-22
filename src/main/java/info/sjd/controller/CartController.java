package info.sjd.controller;

import info.sjd.model.Cart;
import info.sjd.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("cart")
public class CartController {

    @Autowired
    CartService cartService;

    @PutMapping
    public ResponseEntity<Cart> save(@RequestBody Cart cart) {
        Cart cartDAO = cartService.save(cart);
        if (cartDAO == null) {
            return new ResponseEntity<>(cart, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
    }

    @PutMapping
    public ResponseEntity<Cart> update(@RequestBody Cart cart) {
        Cart cartDAO = cartService.update(cart);
        if (cartDAO != null) {
            return new ResponseEntity<>(cart, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Cart> getById(@PathVariable Integer id) {
        return new ResponseEntity<>(cartService.findById(id), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List> getAll(){
        return new ResponseEntity<>(cartService.findAll(), HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Cart> delete(@PathVariable Integer id){
        Cart cartDAO = cartService.findById(id);
        if (cartDAO != null){
            cartService.delete(cartDAO);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
