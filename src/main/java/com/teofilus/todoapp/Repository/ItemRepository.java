package com.teofilus.todoapp.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.teofilus.todoapp.Models.Item;

@Repository
public interface ItemRepository extends JpaRepository<Item, Integer> {

    List<Item> findByChecklistId(Long checklistId);

    List<Item> findByChecklistId(Integer checklistId);
    
}
