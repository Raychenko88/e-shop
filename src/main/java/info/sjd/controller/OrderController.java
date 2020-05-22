package info.sjd.controller;

import info.sjd.model.Order;
import info.sjd.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("order")
public class OrderController {

    @Autowired
    OrderService orderService;

    @PutMapping
    public ResponseEntity<Order> save(@RequestBody Order order){
        Order orderFromDB = orderService.save(order);
        if (orderFromDB == null){
            return new ResponseEntity<>(order, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
    }

    @PostMapping
    public ResponseEntity<Order> update(@RequestBody Order order){
        Order orderFromDB = orderService.update(order);
        if (orderFromDB != null){
            return new ResponseEntity<>(order, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Order> findById(@PathVariable Integer id){
        return new ResponseEntity<>(orderService.findById(id), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List> findAll(){
        return new ResponseEntity<>(orderService.findAll(), HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Order> delete(@PathVariable Integer id){
        Order orderFromDB = orderService.findById(id);
        if (orderFromDB != null){
            orderService.delete(orderFromDB);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
