package com.teofilus.todoapp.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.teofilus.todoapp.Service.ItemService;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;



import com.teofilus.todoapp.Models.Item;

@RestController
@RequestMapping("/api/checklist/{checklistId}/item")
public class ItemController {

    @Autowired
    private ItemService itemService;

    @GetMapping
    public List<Item> getItems(@PathVariable Integer checklistId) {
        return itemService.getItemsByChecklistId(checklistId.longValue());
    }

    @PostMapping
    public Item create(@PathVariable Integer checklistId, @RequestBody Map<String, String> body) {
        Item item = new Item();
        item.setName(body.get("itemName"));
        item.setCompleted(false);
        return itemService.createItem(checklistId, item);
    }

    @GetMapping("/{itemId}")
    public ResponseEntity<Item> getOne(@PathVariable Integer itemId) {
        return itemService.getItem(itemId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{itemId}")
    public ResponseEntity<Item> toggleStatus(@PathVariable Integer itemId) {
        return itemService.toggleStatus(itemId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{itemId}")
    public ResponseEntity<?> delete(@PathVariable Integer itemId) {
        itemService.deleteItem(itemId);
        return ResponseEntity.ok("Item deleted");
    }

    @PutMapping("/rename/{itemId}")
    public ResponseEntity<Item> rename(@PathVariable Integer itemId, @RequestBody Map<String, String> body) {
        String newName = body.get("itemName");
        return itemService.getItem(itemId).map(item -> {
            item.setName(newName);
            return ResponseEntity.ok(itemService.updateItem(itemId, item).orElse(item));
        }).orElse(ResponseEntity.notFound().build());
    }
}
