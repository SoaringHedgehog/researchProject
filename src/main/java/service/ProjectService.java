package service;

import entity.Project;
import entity.Task;
import repository.ProjectRepository;
import repository.ProjectRepositoryImpl;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

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
    public Project create(String id, String name, String description, String dateStart, String dateFinish, String userId){
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

        int idChecked;
        if(id == null || id.isEmpty()) idChecked = projectRepository.getRepositorySize();
        else idChecked = Integer.parseInt(id);

        if(name == null || name.isEmpty()){
            throw new RuntimeException("Имя проекта не может быть пустым");
        }


        if(dateStart == null || dateStart.isEmpty()){
            throw new RuntimeException("Дата начала проекта не может быть пустой");
        }
        LocalDate localDateStart = LocalDate.parse(dateStart, dateTimeFormatter);

        if(dateFinish == null || dateFinish.isEmpty()){
            throw new RuntimeException("Дата окончания проекта не может быть пустой");
        }
        LocalDate localDateFinish = LocalDate.parse(dateFinish, dateTimeFormatter);

        int userIdChecked;
        if(userId == null || userId.isEmpty()){
            throw new RuntimeException("Пользователь проекта не может быть пустой");
        }
        userIdChecked = Integer.parseInt(userId);


        return projectRepository.create(idChecked, name, description, localDateStart, localDateFinish, userIdChecked);
    }


    //READ
    public Project findByName(String projectName){
        if(projectName == null || projectName.isEmpty()){
            throw new RuntimeException("Имя проекта не может быть пустым");
        }
        return projectRepository.findByName(projectName);
    }
    public Project findById(String projectId){
        int projectIdChecked;
        if(projectId == null || projectId.isEmpty()){
            throw new RuntimeException("Id проекта не может быть пустым");
        }
        else{
            projectIdChecked = Integer.parseInt(projectId);
        }
        return projectRepository.findById(projectIdChecked);
    }

    //UPDATE
    public Project updateByName(String projectName, String fieldForUpdate, String newValue){
        if(projectName == null || projectName.isEmpty()){
            throw new RuntimeException("Имя проекта не может быть пустым");
        }
        if(fieldForUpdate == null || fieldForUpdate.isEmpty()){
            throw new RuntimeException("Поле для обновления не может быть пустым");
        }
        if((newValue == null || newValue.isEmpty()) && !fieldForUpdate.equalsIgnoreCase("описание")){
            throw new RuntimeException("Новое значение поля не может быть пустым");
        }
        Project project = projectRepository.updateByName(projectName, fieldForUpdate, newValue);
        return project;
    }

    public Project updateById(String projectId, String fieldForUpdate, String newValue){
        int projectIdChecked;
        if(projectId == null || projectId.isEmpty()){
            throw new RuntimeException("ID проекта не может быть пустым");
        }
        else{
            projectIdChecked = Integer.parseInt(projectId);
        }
        if(fieldForUpdate == null || fieldForUpdate.isEmpty()){
            throw new RuntimeException("Поле для обновления не может быть пустым");
        }
        if((newValue == null || newValue.isEmpty()) && !fieldForUpdate.equalsIgnoreCase("описание")){
            throw new RuntimeException("Новое значение не может быть пустым");
        }

        return projectRepository.updateById(projectIdChecked, fieldForUpdate, newValue);
    }

    //DELETE
    public Project deleteByName(String projectName){
        if(projectName == null || projectName.isEmpty()){
            throw new RuntimeException("Имя проекта не может быть пустым");
        }

        Project project = projectRepository.findByName(projectName);
        for(Task task : project.getTasks()){
            taskService.deleteByName(task.getName());
        }
        return projectRepository.delete(projectName);
    }
}
