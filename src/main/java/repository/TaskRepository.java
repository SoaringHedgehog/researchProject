package repository;

import entity.Task;
import entity.User;

import java.time.LocalDate;

public interface TaskRepository {
    void create(int id, String name, String description, LocalDate dateStart, LocalDate dateFinish, int projectId);
    Task findByName(String taskName);
    Task findById(int projectId);
    void updateByName(String taskName, String fieldForUpdate, String newValue);
    void updateById(int taskId, String fileForUpdate, String newValue);
    void deleteByName(String taskName);
    int getRepositorySize();
}