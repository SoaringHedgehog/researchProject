package service;

import entity.Task;
import repository.TaskRepository;
import repository.TaskRepositoryImpl;

import java.time.LocalDate;

public class TaskService {
    private final TaskRepository taskRepository;

    public TaskService(){
        this.taskRepository = new TaskRepositoryImpl();
    }

    //CREATE
    public void create(String id, String name, String description, String dateStart, String dateFinish, String projectId){
        int idChecked;
        if(id == null) idChecked = taskRepository.getRepositorySize();
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
        int projectIdChecked;
        if(projectId == null || projectId.isEmpty()){
            System.out.println("Пользователь проекта не может быть пустой");
            return;
        }
        else{
            projectIdChecked = Integer.parseInt(projectId);
        }

        taskRepository.create(idChecked, name, description, LocalDate.parse(dateStart), LocalDate.parse(dateFinish), projectIdChecked);
    }

    //READ
    public Task findByName(String taskName){
        if(taskName == null || taskName.isEmpty()){
            System.out.println("Имя задачи не может быть пустым");
            return null;
        }
        return taskRepository.findByName(taskName);
    }
    public Task findById(int taskId){
        return taskRepository.findById(taskId);
    }

    //UPDATE
    public void updateByName(String taskName, String fieldForUpdate, String newValue){
        if(taskName == null || taskName.isEmpty()){
            System.out.println("Имя задачи не может быть пустым");
            return;
        }
        if(fieldForUpdate == null || fieldForUpdate.isEmpty()){
            System.out.println("Поле для обновления не может быть пустой");
            return;
        }
        if((newValue == null || newValue.isEmpty()) && !fieldForUpdate.equalsIgnoreCase("описание")){
            System.out.println("Новое значение не может быть пустой");
            return;
        }
        taskRepository.updateByName(taskName, fieldForUpdate, newValue);
    }
    public void updateById(String taskId, String fieldForUpdate, String newValue){
        int taskIdChecked;
        if(taskId == null || taskId.isEmpty()){
            System.out.println("ID проекта не может быть пустым");
            return;
        }
        else{
            taskIdChecked = Integer.parseInt(taskId);
        }
        if(fieldForUpdate == null || fieldForUpdate.isEmpty()){
            System.out.println("Поле для обновления не может быть пустой");
            return;
        }
        if((newValue == null || newValue.isEmpty()) && !fieldForUpdate.equalsIgnoreCase("описание")){
            System.out.println("Новое значение не может быть пустой");
            return;
        }
        taskRepository.updateById(taskIdChecked, fieldForUpdate, newValue);
    }

    //DELETE
    public void deleteByName(String taskName){
        taskRepository.deleteByName(taskName);
    }
}
