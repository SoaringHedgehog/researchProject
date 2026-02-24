package repository;

import entity.Project;

import java.time.LocalDate;

public interface ProjectRepository {
    Project create(int id, String name, String description, LocalDate dateStart, LocalDate dateFinish, int userId);
    Project findByName(String projectName);
    Project findById(int projectId);
    Project updateByName(String projectName, String fileForUpdate, String newValue);
    Project updateById(int projectId, String fileForUpdate, String newValue);
    Project delete(String projectName);
    int getRepositorySize();
    void printTasksByProjectName(String projectName);
    void printAllProjects();
}