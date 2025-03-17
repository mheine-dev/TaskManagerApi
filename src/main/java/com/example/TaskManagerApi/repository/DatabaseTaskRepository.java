package com.example.TaskManagerApi.repository;

import com.example.TaskManagerApi.model.Task;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class DatabaseTaskRepository implements TaskRepository {

    private static final String URL = "jdbc:mariadb://localhost:3306/task_manager";
    private static final String USER = "";
    private static final String PASSWORD = "";

    // Methode, um eine Verbindung zur Datenbank herzustellen
    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    @Override
    public List<Task> findAll() {
        List<Task> tasks = new ArrayList<>();
        String query = "SELECT * FROM tasks"; 

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet results = stmt.executeQuery(query)) {

            while (results.next()) {
                String id = results.getString("id");
                String task = results.getString("task");
                tasks.add(new Task(id, task));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tasks;
    }

    @Override
    public Task create(Task task) {
        String query = "INSERT INTO tasks (id, task) VALUES (?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, task.id());   // Aufruf des "id"-Feldes des Records
            stmt.setString(2, task.task()); // Aufruf des "task"-Feldes des Records
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return task;
    }

    @Override
    public void update(String id, Task task) {
        String query = "UPDATE tasks SET task = ? WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, task.task());
            stmt.setString(2, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(String id) {
        String query = "DELETE FROM tasks WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
