package com.ninaja.todoapi.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/* 
* @author WesNinaja
* @since 1.0
* @see Task
* 
*/

@Entity
@Table(name = "tb_usuarios")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String email;
	private String nome;
	private String senha;
	


	@OneToMany(mappedBy = "usuario", cascade = CascadeType.REMOVE)
	@JsonIgnoreProperties("usuario")
	private List<Task> tarefas;
	
	public User() {
	}


	public User(Long id, String nome, String email,  String senha) {
		super();
		this.id = id;
		this.email = email;
		this.nome = nome;
		this.senha = senha;
		
	}

	
	//Getters and Setters
	public Long getId() {
		return id;
	}



	public void setId(Long id) {
		this.id = id;
	}



	public String getEmail() {
		return email;
	}



	public void setEmail(String email) {
		this.email = email;
	}



	public String getNome() {
		return nome;
	}



	public void setNome(String nome) {
		this.nome = nome;
	}



	public String getSenha() {
		return senha;
	}



	public void setSenha(String senha) {
		this.senha = senha;
	}



	public List<Task> getTarefas() {
		return tarefas;
	}



	public void setTarefas(List<Task> tarefas) {
		this.tarefas = tarefas;
	}
	

	
	
	

}