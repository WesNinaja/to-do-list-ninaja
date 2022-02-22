package com.ninaja.todoapi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ninaja.todoapi.model.Task;
import com.ninaja.todoapi.repository.TaskRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TaskService {

	@Autowired
	private TaskRepository taskRepository;

	public Task createTask(Task task) {
		return taskRepository.save(task);
	}

	public List<Task> listAllTasks() {
		return taskRepository.findAll();
	}

	public ResponseEntity<Task> updateTaskById(Task task, Long id) {
		return taskRepository.findById(id).map(taskToUpdate -> {
			taskToUpdate.setTitulo(task.getTitulo());
			taskToUpdate.setDescricao(task.getDescricao());
			taskToUpdate.setDataFinal(task.getDataFinal());
			Task updated = taskRepository.save(taskToUpdate);
			return ResponseEntity.ok().body(updated);
		}).orElse(ResponseEntity.notFound().build());
	}
	
	public ResponseEntity<Task> concludeTaskById(Task task, Long id) {
		return taskRepository.findById(id).map(taskToUpdate -> {
			if( task.getConcluido() == false) {
			taskToUpdate.setConcluido(true);
			}
			Task updated = taskRepository.save(taskToUpdate);
			return ResponseEntity.ok().body(updated);
		}).orElse(ResponseEntity.notFound().build());
	}
		
	
}