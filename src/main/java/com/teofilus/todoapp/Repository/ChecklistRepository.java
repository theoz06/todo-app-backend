package com.teofilus.todoapp.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.teofilus.todoapp.Models.Checklist;

@Repository
public interface ChecklistRepository extends JpaRepository<Checklist, Integer> {

    List<Checklist> findByUserId(Integer id);
    
}
