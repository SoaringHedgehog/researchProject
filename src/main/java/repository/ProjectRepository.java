package repository;

import entity.Project;

import java.time.LocalDate;

public interface ProjectRepository {
    void create(int id, String name, String description, LocalDate dateStart, LocalDate dateFinish, int userId);
    Project findByName(String projectName);
    Project findById(int projectId);
    void updateByName(String projectName, String fileForUpdate, String newValue);
    void updateById(int projectId, String fileForUpdate, String newValue);
    void delete(String projectName);
    int getRepositorySize();
    void printTasks(String projectName);
    void printAllProjects();
}