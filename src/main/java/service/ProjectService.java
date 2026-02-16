package service;

import entity.Project;
import entity.Task;
import repository.ProjectRepository;
import repository.ProjectRepositoryImpl;

import java.time.LocalDate;

public class ProjectService {
    private ProjectRepository projectRepository;
    private TaskService taskService;
    private UserService userService;

    public ProjectService(TaskService taskService, UserService userService){
        this.projectRepository = new ProjectRepositoryImpl();
        this.taskService = taskService;
        this.userService = userService;
    }

    //создание project в ProjectRepositoryImpl
    public void create(String id, String name, String description, String dateStart, String dateFinish, String userId){
        int idChecked;
        if(id == null) idChecked = projectRepository.getRepositorySize();
        else idChecked = Integer.parseInt(id);

        if(name == null || name.isEmpty()){
            System.out.println("Имя проекта не может быть пустым");
            return;
        }
        if(dateStart == null || dateStart.isEmpty()){
            System.out.println("Дата начала не может быть пустой");
            return;
        }
        if(dateFinish == null || dateFinish.isEmpty()){
            System.out.println("Дата окончания не может быть пустой");
            return;
        }
        int userIdChecked;
        if(userId == null || userId.isEmpty()){
            System.out.println("Пользователь проекта не может быть пустой");
            return;
        }
        else{
            userIdChecked = Integer.parseInt(userId);
        }

        projectRepository.create(idChecked, name, description, LocalDate.parse(dateStart), LocalDate.parse(dateFinish), userIdChecked);
    }


    //READ
    public Project findByName(String projectName){
        if(projectName == null || projectName.isEmpty()){
            System.out.println("ID проекта не может быть пустым");
            return null;
        }
        return projectRepository.findByName(projectName);
    }
    public Project findById(int projectName){
        return projectRepository.findById(projectName);
    }

    //UPDATE
    public void updateByName(String projectName, String fieldForUpdate, String newValue){
        if(projectName == null || projectName.isEmpty()){
            System.out.println("ID проекта не может быть пустым");
            return;
        }
        if(fieldForUpdate == null || fieldForUpdate.isEmpty()){
            System.out.println("Поле для обновления не может быть пустой");
            return;
        }
        if((newValue == null || newValue.isEmpty()) && !fieldForUpdate.equalsIgnoreCase("описание")){
            System.out.println("Новое значение не может быть пустым");
            return;
        }
        projectRepository.updateByName(projectName, fieldForUpdate, newValue);
    }

    public void updateById(String projectId, String fieldForUpdate, String newValue){
        int projectIdChecked;
        if(projectId == null || projectId.isEmpty()){
            System.out.println("ID проекта не может быть пустым");
            return;
        }
        else{
            projectIdChecked = Integer.parseInt(projectId);
        }
        if(fieldForUpdate == null || fieldForUpdate.isEmpty()){
            System.out.println("Поле для обновления не может быть пустой");
            return;
        }
        if((newValue == null || newValue.isEmpty()) && !fieldForUpdate.equalsIgnoreCase("описание")){
            System.out.println("Новое значение не может быть пустым");
            return;
        }

        projectRepository.updateById(projectIdChecked, fieldForUpdate, newValue);
    }

    //DELETE
    public void deleteByName(String projectName){
        if(projectName == null || projectName.isEmpty()){
            System.out.println("Имя проекта не может быть пустым");
            return;
        }
        Project project = projectRepository.findByName(projectName);
        for(Task task : project.getTasks()){
            taskService.deleteByName(task.getName());
        }
        projectRepository.delete(projectName);
    }
}
