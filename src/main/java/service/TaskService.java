package service;

import entity.Task;
import repository.TaskRepository;
import repository.TaskRepositoryImpl;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class TaskService {
    private final TaskRepository taskRepository;

    public TaskService(){
        this.taskRepository = new TaskRepositoryImpl();
    }

    //CREATE
    public Task create(String id, String name, String description, String dateStart, String dateFinish, String projectId){
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

        int idChecked;
        if(id == null || id.isEmpty()) idChecked = taskRepository.getRepositorySize();
        else idChecked = Integer.parseInt(id);

        if(name == null || name.isEmpty()){
            throw new RuntimeException("Имя задачи не может быть пустым");
        }

        if(dateStart == null || dateStart.isEmpty()){
            throw new RuntimeException("Дата начала задачи не может быть пустой");
        }
        LocalDate localDateStart = LocalDate.parse(dateStart, dateTimeFormatter);

        if(dateFinish == null || dateFinish.isEmpty()){
            throw new RuntimeException("Дата окончания задачи не может быть пустой");
        }
        LocalDate localDateFinish = LocalDate.parse(dateFinish, dateTimeFormatter);

        int projectIdChecked;
        if(projectId == null || projectId.isEmpty()){
            throw new RuntimeException("Id проекта не может быть пустым");
        }
        else{
            projectIdChecked = Integer.parseInt(projectId);
        }

        return taskRepository.create(idChecked, name, description, localDateStart, localDateFinish, projectIdChecked);
    }

    //READ
    public Task findByName(String taskName){
        if(taskName == null || taskName.isEmpty()){
            throw new RuntimeException("Имя задачи не может быть пустым");
        }
        return taskRepository.findByName(taskName);
    }
    public Task findById(String taskId){
        int taskIdChecked;
        if(taskId == null || taskId.isEmpty()){
            throw new RuntimeException("Id проекта не может быть пустым");
        }
        else{
            taskIdChecked = Integer.parseInt(taskId);
        }
        return taskRepository.findById(taskIdChecked);
    }

    //UPDATE
    public void updateByName(String taskName, String fieldForUpdate, String newValue){
        if(taskName == null || taskName.isEmpty()){
            throw new RuntimeException("Имя задачи не может быть пустым");
        }
        if(fieldForUpdate == null || fieldForUpdate.isEmpty()){
            throw new RuntimeException("Поле для обновления не может быть пустым");
        }
        if((newValue == null || newValue.isEmpty()) && !fieldForUpdate.equalsIgnoreCase("описание")){
            throw new RuntimeException("Новое значение не может быть пустым");
        }
        taskRepository.updateByName(taskName, fieldForUpdate, newValue);
    }
    public void updateById(String taskId, String fieldForUpdate, String newValue){
        int taskIdChecked;
        if(taskId == null || taskId.isEmpty()){
            throw new RuntimeException("ID проекта не может быть пустым");
        }
        else{
            taskIdChecked = Integer.parseInt(taskId);
        }
        if(fieldForUpdate == null || fieldForUpdate.isEmpty()){
            throw new RuntimeException("Поле для обновления не может быть пустым");
        }
        if((newValue == null || newValue.isEmpty()) && !fieldForUpdate.equalsIgnoreCase("описание")){
            throw new RuntimeException("Новое значение не может быть пустым");
        }
        taskRepository.updateById(taskIdChecked, fieldForUpdate, newValue);
    }

    //DELETE
    public Task deleteByName(String taskName){
        if(taskName == null || taskName.isEmpty()){
            throw new RuntimeException("Имя задачи не может быть пустым");
        }
        return taskRepository.deleteByName(taskName);
    }
}
