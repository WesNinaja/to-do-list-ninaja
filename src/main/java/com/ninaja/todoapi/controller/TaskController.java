package com.ninaja.todoapi.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.ninaja.todoapi.model.Task;
import com.ninaja.todoapi.model.TaskPriority;
import com.ninaja.todoapi.model.User;
import com.ninaja.todoapi.repository.TaskRepository;
import com.ninaja.todoapi.repository.UserRepository;
import com.ninaja.todoapi.service.TaskService;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/v1/tasks")
public class TaskController {

	@Autowired
	TaskService taskService;
        
        private @Autowired UserRepository userRepository;  
        private @Autowired TaskRepository taskRepository; 
	
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public List<Task>getAllTasks(){
		return taskService.listAllTasks();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Task> GetById(@PathVariable Long id) {
		return taskRepository.findById(id)
				.map(resp -> ResponseEntity.ok(resp))
				.orElse(ResponseEntity.notFound().build());
	}
	
	@GetMapping("/priority/{priority}")
	public ResponseEntity<List<Task>> findByPriority (@PathVariable TaskPriority priority){
		return ResponseEntity.ok(taskRepository.findByPrioridade(priority));
	}
	
	@PostMapping("/save")
	@ResponseStatus(HttpStatus.CREATED)
        
	public Task createTask(@RequestBody Task task, Authentication authentication) {            
            Optional<User> user = this.getUser(authentication);
            task.setUsuario(user.get());
	    return taskService.createTask(task);
	}
        
        private Optional<User>getUser(Authentication authentication) {
            Object principal = authentication.getPrincipal();
            String email = principal.toString();
            Optional<User> user = userRepository.findByEmail(email);
            return user;    
        }
	
	@PutMapping("/update/{id}")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Task> updateTask(@PathVariable (value = "id") Long id, @RequestBody Task task){
		return taskService.updateTaskById(task, id);
	}
	
	@PutMapping("/conclude/{id}")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Task> concludeTask(@PathVariable (value = "id") Long id, @RequestBody Task task){
		return taskService.concludeTaskById(task, id);
	}
	
	@DeleteMapping("/delete/{id}")
	public void delete(@PathVariable(value = "id") Long id) {
		taskRepository.deleteById(id);
	}
}