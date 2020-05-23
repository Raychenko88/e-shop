package info.sjd.controller;

import info.sjd.model.Cart;
import info.sjd.model.Item;
import info.sjd.model.User;
import info.sjd.service.ItemService;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("item")
public class ItemController {

    @Autowired
    ItemService itemService;

    @PutMapping
    public ResponseEntity<Item> save(@RequestBody Item item){
        Item itemFromDB = itemService.save(item);
        if (itemFromDB == null){
            return new ResponseEntity<>(item, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
    }

    @PostMapping
    public ResponseEntity<Item> update (@RequestBody Item item){
        Item itemFromDB = itemService.update(item);
        if (itemFromDB != null){
            return new ResponseEntity<>(item, HttpStatus.OK);
        }
        return  new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Item> getById(@PathVariable Integer id){
        return new ResponseEntity<>(itemService.findById(id), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List> getAll(){
        return new ResponseEntity<>(itemService.getAll(), HttpStatus.OK);
    }

    @GetMapping(path = "by-code")
    public ResponseEntity<List> getByCode(@RequestParam String code){
        return new ResponseEntity<>(itemService.getByCode(code), HttpStatus.OK);
    }


    @GetMapping(path = "cart_id")
    public ResponseEntity<List> getAllByCart(@RequestParam Integer id){
        return new ResponseEntity<>(itemService.getAllByCart(id), HttpStatus.OK);
    }

    @GetMapping(path = "all-available")
    public ResponseEntity<List> getAllAvailable(){
        return new ResponseEntity<>(itemService.getAllAvailable(), HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity delete(@PathVariable Integer id){
        Item item = itemService.findById(id);
        if (item != null){
            itemService.delete(item);
        }
        return new ResponseEntity(HttpStatus.OK);
    }
}
