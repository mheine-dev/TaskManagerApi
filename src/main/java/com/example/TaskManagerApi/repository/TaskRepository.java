package com.example.TaskManagerApi.repository;

import com.example.TaskManagerApi.model.Task;

import java.util.List;

public interface TaskRepository {
    List<Task> findAll();

    Task create(Task task);

    void update(String id, Task task);

    void delete(String id);
}
