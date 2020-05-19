package info.sjd.controller;

import info.sjd.model.Item;
import info.sjd.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        return new ResponseEntity<>(item, HttpStatus.NOT_ACCEPTABLE);
    }

    @PutMapping
    public ResponseEntity<Item> update (@RequestBody Item item){
        Item itemFromDB = itemService.update(item);
        if (itemFromDB != null){
            return new ResponseEntity<>(item, HttpStatus.OK);
        }
        return  new ResponseEntity<>(item, HttpStatus.NOT_ACCEPTABLE);
    }


}
