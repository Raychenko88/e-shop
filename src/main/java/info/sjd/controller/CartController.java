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
        Cart cartFromDB = cartService.save(cart);
        if (cartFromDB == null) {
            return new ResponseEntity<>(cart, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
    }

    @PostMapping
    public ResponseEntity<Cart> update(@RequestBody Cart cart) {
        Cart cartFromDB = cartService.update(cart);
        if (cartFromDB != null) {
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
        Cart cartFromDB = cartService.findById(id);
        if (cartFromDB != null){
            cartService.delete(cartFromDB);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(path = "get_all_by_user_and_period")
    public ResponseEntity<List> getAllByUserAndPeriod(@RequestParam Integer userId, Long timeDown, Long timUp){
        return new ResponseEntity<>(cartService.getAllByUserAndPeriod(userId, timeDown, timUp), HttpStatus.OK);
    }

    @GetMapping(path = "open_status")
    public ResponseEntity<Cart> getByUserAndOpenStatus(@RequestParam Integer id, Integer status){
        return new ResponseEntity<>(cartService.getByUserAndOpenStatus(id,status), HttpStatus.OK);
    }

    @GetMapping(path = "update_status")
    public ResponseEntity<Cart> updateStatus(@RequestParam Integer idParam, Integer closedParam){
        Cart cart = cartService.updateStatus(idParam, closedParam);
        if (cart != null){
            return new ResponseEntity<>(cartService.updateStatus(idParam,closedParam), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
    }
}
