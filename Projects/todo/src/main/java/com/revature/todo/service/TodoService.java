package com.revature.todo.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.revature.todo.entity.Todo;
import com.revature.todo.repo.TodoRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class TodoService {
    
    private final TodoRepository todoRepository;

    public Todo createTodo(Todo todo){
        // Ensure required fields are set (task, user)
        if (todo.getTask() == null || todo.getTask().trim().isEmpty()) {
            throw new IllegalArgumentException("Task cannot be null or empty");
        }
        if (todo.getUser() == null) {
            throw new IllegalArgumentException("User cannot be null");
        }
        // ID should not be set for new entity
        todo.setId(null);
        // Save and return the persisted entity
        return todoRepository.save(todo);
    }

    public List<Todo> getTodos(){
        // Return all todos
        return todoRepository.findAll();
    }

    public Todo updateTodo(Todo todo){
        // Only update if the todo exists
        if (todo.getId() == null || !todoRepository.existsById(todo.getId())) {
            throw new IllegalArgumentException("Todo with given ID does not exist");
        }
        // Ensure required fields are set
        if (todo.getTask() == null || todo.getTask().trim().isEmpty()) {
            throw new IllegalArgumentException("Task cannot be null or empty");
        }
        if (todo.getUser() == null) {
            throw new IllegalArgumentException("User cannot be null");
        }
        // Save and return the updated entity
        return todoRepository.save(todo);
    }

    public boolean deleteTodo(long id){
        if (!todoRepository.existsById(id)) {
            return false;
        }
        todoRepository.deleteById(id);
        return true;
    }

}
