package com.teofilus.todoapp.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

import com.teofilus.todoapp.Models.Checklist;
import com.teofilus.todoapp.Models.User;
import com.teofilus.todoapp.Repository.ChecklistRepository;
import com.teofilus.todoapp.Repository.UserRepository;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

@Service
public class ChecklistService {
    @Autowired
    private ChecklistRepository checklistRepository;

    @Autowired
    private UserRepository userRepository;

    private User getCurrentUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found") );
    }

    public List<Checklist> getAllChecklists() {
        return checklistRepository.findByUserId(getCurrentUser().getId());
    }

    public Checklist createChecklist(Checklist checklist) {
        checklist.setUser(getCurrentUser());
        return checklistRepository.save(checklist);
    }

    public Optional<Checklist> getChecklist(Integer id) {
        return checklistRepository.findById(id);
    }

    public void deleteChecklist(Integer id) {
        checklistRepository.deleteById(id);
    }
}
