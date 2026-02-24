package repository;

import entity.Task;

import java.time.LocalDate;

public interface TaskRepository {
    Task create(int id, String name, String description, LocalDate dateStart, LocalDate dateFinish, int projectId);
    Task findByName(String taskName);
    Task findById(int projectId);
    Task updateByName(String taskName, String fieldForUpdate, String newValue);
    Task updateById(int taskId, String fileForUpdate, String newValue);
    Task deleteByName(String taskName);
    int getRepositorySize();
}