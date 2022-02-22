package com.ninaja.todoapi.model;

public enum TaskPriority {
	
	BAIXA("Baixa"),
	MEDIA("Média"),
	ALTA("Alta");
	
	private String task;
	
	private TaskPriority(String task) {

		this.task = task;

}

	public String getTask() {
		return task;
	}

	public void setName(String task) {
		this.task = task;
	}
	
	@Override
	public String toString() {
		return this.name();
	}
}
	