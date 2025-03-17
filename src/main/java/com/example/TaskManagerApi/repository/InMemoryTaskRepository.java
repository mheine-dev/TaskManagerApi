package com.example.TaskManagerApi.repository;

import com.example.TaskManagerApi.model.Task;
import org.springframework.stereotype.Repository;
import java.util.*;

@Repository
public class InMemoryTaskRepository implements TaskRepository {
    List<Task> tasks = new ArrayList<>();

    public InMemoryTaskRepository() {
        tasks.add(new Task(UUID.randomUUID().toString(), "Create RESTful API"));
    }

    @Override
    public List<Task> findAll() {
        return tasks;
    }

    @Override
    public Task create(Task task) {
        tasks.add(task);
        return task;
    }

    @Override
    public void update(String id, Task task) {
        Task existing = tasks.stream().filter(t -> t.id().equals(id))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Stream not found"));
        int i = tasks.indexOf(existing);
        tasks.set(i, task);
    }

    @Override
    public void delete(String id) {
        tasks.removeIf(task -> task.id().equals(id));
    }
}
