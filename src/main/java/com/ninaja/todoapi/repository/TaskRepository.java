package com.ninaja.todoapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ninaja.todoapi.model.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long>{

}
