package com.revature.todo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

@NoArgsConstructor
@Data
@Entity
@Table(name = "subtasks")
public class Subtask {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(nullable = false)
	private String task;
	private boolean completed;
	@ManyToOne
	@JoinColumn(name = "todo_id", referencedColumnName = "id", nullable = false)
	@JsonProperty(access = Access.WRITE_ONLY)
	private Todo todo;

	// Expose only the todo id in serialization
	public Long getTodoId() {
		return todo != null ? todo.getId() : null;
	}
}
