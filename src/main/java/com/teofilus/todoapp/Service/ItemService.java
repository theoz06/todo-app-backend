package com.teofilus.todoapp.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.teofilus.todoapp.Models.Checklist;
import com.teofilus.todoapp.Repository.ChecklistRepository;
import com.teofilus.todoapp.Repository.ItemRepository;
import com.teofilus.todoapp.Models.Item;

import java.util.List;
import java.util.Optional;


@Service
public class ItemService {
    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private ChecklistRepository checklistRepository;

    public List<Item> getItemsByChecklistId(Integer checklistId) {
        return itemRepository.findByChecklistId(checklistId);
    }

    public Optional<Item> getItem(Integer id) {
        return itemRepository.findById(id);
    }

    public Item createItem(Integer checklistId, Item item) {
        Checklist checklist = checklistRepository.findById(checklistId).orElseThrow(()-> new RuntimeException("Checklist not found"));
        item.setChecklist(checklist);
        return itemRepository.save(item);
    }

    public Optional<Item> updateItem(Integer id, Item updatedItem) {
        return itemRepository.findById(id).map(item -> {
            item.setName(updatedItem.getName());
            item.setCompleted(updatedItem.isCompleted());
            return itemRepository.save(item);
        });
    }

    public Optional<Item> toggleStatus(Integer id) {
        return itemRepository.findById(id).map(item -> {
            item.setCompleted(!item.isCompleted());
            return itemRepository.save(item);
        });
    }

    public void deleteItem(Integer id) {
        itemRepository.deleteById(id);
    }

    public List<Item> getItemsByChecklistId(Long checklistId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getItemsByChecklistId'");
    }
}
