package com.ninaja.todoapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ninaja.todoapi.model.Task;
import com.ninaja.todoapi.model.TaskPriority;
import com.ninaja.todoapi.model.User;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long>{

	List<Task> findByUsuario(User usuario);

	List<Task> findByPrioridade (TaskPriority prioridade);

}
