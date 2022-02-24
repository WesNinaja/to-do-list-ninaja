package com.ninaja.todoapi.model.dto;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ninaja.todoapi.model.Task;

public class CredentialsDTO {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String email;
	private String nome;
	private String senha;
	private String jwt;
	


	@OneToMany(mappedBy = "usuario", cascade = CascadeType.REMOVE)
	@JsonIgnoreProperties("usuario")
	private List<Task> tarefas;
	
	public CredentialsDTO() {
	}
	
	


	public CredentialsDTO(Long id, String nome, String email,  String senha, String jwt) {
		super();
		this.id = id;
		this.email = email;
		this.nome = nome;
		this.senha = senha;
		this.jwt = jwt;
		
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




	public String getJwt() {
		return jwt;
	}




	public void setJwt(String jwt) {
		this.jwt = jwt;
	}

	

}
