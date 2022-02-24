package com.ninaja.todoapi.model;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/* 
* @author WesNinaja
* @since 1.0
* @see User
* 
*/

@Entity
@Table(name = "tb_tarefas")
public class Task {

	 

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	private String titulo;

	@NotNull
	private String descricao;

	@NotNull
	private Date dataFinal;
	
	@Enumerated(EnumType.STRING)
	private TaskPriority prioridade;
	
	
	private Boolean concluido = false;

	@CreationTimestamp
	@Column(name = "criado_em", nullable = false, updatable = false)
	private Date criadoEm;

	@Column(name = "atualizado_em")
	private Date atualizadoEm;
	
	@ManyToOne
	@JoinColumn(name = "fk_usuario")
	@JsonIgnoreProperties("tarefas")
	private User usuario;
	
	
	//Constructors
	public Task() {
	}
	
	public Task(Long id, String titulo, String descricao, TaskPriority prioridade, Date dataFinal, Boolean concluido, Date criadoEm,
			Date atualizadoEm, User usuario) {
		this.id = id;
		this.titulo = titulo;
		this.descricao = descricao;
		this.prioridade = prioridade;
		this.dataFinal = dataFinal;
		this.concluido = concluido;
		this.criadoEm = criadoEm;
		this.atualizadoEm = atualizadoEm;
		this.usuario = usuario;
		
	}
	

	//Getters and Setters

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Date getDataFinal() {
		return dataFinal;
	}

	public void setDataFinal(Date dataFinal) {
		this.dataFinal = dataFinal;
	}

	public TaskPriority getPrioridade() {
		return prioridade;
	}

	public void setPrioridade(TaskPriority prioridade) {
		this.prioridade = prioridade;
	}

	public Boolean getConcluido() {
		return concluido;
	}

	public void setConcluido(Boolean concluido) {
		this.concluido = concluido;
	}

	public Date getCriadoEm() {
		return criadoEm;
	}

	public void setCreatedAt(Date criadoEm) {
		this.criadoEm = criadoEm;
	}

	public Date getUpdatedAt() {
		return atualizadoEm;
	}

	public void setUpdatedAt(Date atualizadoEm) {
		this.atualizadoEm = atualizadoEm;
	}

	public User getUsuario() {
		return usuario;
	}

	public void setUsuario(User usuario) {
		this.usuario = usuario;
	}


	
	
}