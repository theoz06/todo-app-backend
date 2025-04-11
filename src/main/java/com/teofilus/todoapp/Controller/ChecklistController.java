package com.teofilus.todoapp.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import com.teofilus.todoapp.Models.Checklist;
import com.teofilus.todoapp.Service.ChecklistService;

@RestController
@RequestMapping("/api/checklist")
public class ChecklistController {
     @Autowired
    private ChecklistService checklistService;

    @GetMapping
    public List<Checklist> getAll() {
        return checklistService.getAllChecklists();
    }

    @PostMapping
    public Checklist create(@RequestBody Checklist checklist) {
        return checklistService.createChecklist(checklist);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Checklist> getOne(@PathVariable Integer id) {
        return checklistService.getChecklist(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        checklistService.deleteChecklist(id);
        return ResponseEntity.ok("Checklist deleted");
    }
}
