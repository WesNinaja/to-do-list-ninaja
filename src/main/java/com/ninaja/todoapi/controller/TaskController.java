package com.ninaja.todoapi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.ninaja.todoapi.model.Task;
import com.ninaja.todoapi.service.TaskService;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/v1")
public class TaskController {

	@Autowired
	TaskService taskService;
	
	@PostMapping("/tasks")
	@ResponseStatus(HttpStatus.CREATED)
	public Task createTask(@RequestBody Task task) {
		return taskService.createTask(task);
	}
	
	@GetMapping("/tasks")
	@ResponseStatus(HttpStatus.OK)
	public List<Task>getAllTasks(){
		return taskService.listAllTasks();
	}
	
	@GetMapping("/tasks/{id}")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Task> getTaskById(@PathVariable (value = "id") Long id){
		return taskService.findTaskById(id);
	}
	
	@PutMapping("/tasks/{id}")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Task> getTaskById(@PathVariable (value = "id") Long id, @RequestBody Task task){
		return taskService.updateTaskById(task, id);
	}
}
