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

import jakarta.persistence.OneToMany;
import java.util.List;
import jakarta.persistence.CascadeType;

@NoArgsConstructor
@Data
@Entity
@Table(name = "todos")
public class Todo {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(nullable = false)
	private String task;
	private boolean completed;
	@ManyToOne
	@JoinColumn(name = "username", referencedColumnName = "username", nullable = false)
	@JsonProperty(access = Access.WRITE_ONLY)
	private User user;
    @OneToMany(mappedBy = "todo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Subtask> subtasks;

	// Expose only the username in serialization
	public String getUsername() {
		return user != null ? user.getUsername() : null;
	}
}
