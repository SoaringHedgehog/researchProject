package repository;

import entity.Project;
import entity.Task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class TaskRepositoryImpl implements TaskRepository{
    private HashMap<String, Task> taskMap;

    public TaskRepositoryImpl(){
        this.taskMap = new HashMap<>();
    }

    //CREATE
    @Override
    public void create(int id, String name, String description, LocalDate dateStart, LocalDate dateFinish, int projectId){
        if(!taskMap.containsKey(name)){
            Task task = new Task(id, name, description, dateStart, dateFinish, projectId);
            taskMap.put(task.getName(), task);
            System.out.println("Задача успешно добавлена");
        }
        else{
            System.out.println("Задача с таким названием уже существует. Попробуйте другое");
        }
    }

    //READ
    @Override
    public Task findByName(String taskName){
        Task task = taskMap.get(taskName);
        if(task == null) System.out.println("Задача с таким именем не найдена");
        return task;
    }
    @Override
    public Task findById(int projectId){
        for(Map.Entry<String, Task> task : taskMap.entrySet()){
            if(task.getValue().getId() == projectId){
                return taskMap.get(task.getKey());
            }
        }
        System.out.println("Задача с таким id не найдена");
        return null;
    }

    //READ
    @Override
    public void updateByName(String taskName, String fieldForUpdate, String newValue){
        Task task = taskMap.get(taskName);
        switch(fieldForUpdate.toLowerCase()){
            case "id":
                try{
                    int idValue = Integer.parseInt(newValue);
                    if(findById(idValue) != null) System.out.println("Этот id уже занят");
                    else {
                        task.setId(idValue);
                        System.out.println("Id задачи успешно изменён");
                    }
                }
                catch (Exception e){
                    System.out.println(e);
                }
                break;
            case "название":
                try{
                    if(findByName(newValue) != null) System.out.println("Это имя проекта уже занято");
                    else {
                        task.setName(newValue);
                        System.out.println("Имя проекта успешно изменено");
                    }
                }
                catch (Exception e){
                    System.out.println(e);
                }
                break;
            case "описание":
                task.setDescription(newValue);
                System.out.println("Описание успешно изменено");
                break;
            case "дата начала":
                try{
                    LocalDate localDate = LocalDate.parse(newValue, DateTimeFormatter.ofPattern("dd.MM.yyyy"));
                    task.setDateStart(localDate);
                    System.out.println("Дата начала проекта успешно изменена");
                }
                catch(Exception e){
                    System.out.println(e);
                }
                break;
            case "дата окончания":
                try{
                    LocalDate localDate = LocalDate.parse(newValue, DateTimeFormatter.ofPattern("dd.MM.yyyy"));
                    task.setDateFinish(localDate);
                    System.out.println("дата окончания проекта успешно изменена");
                }
                catch(Exception e){
                    System.out.println(e);
                }
                break;
            case "id проекта":
                try{
                    int projectIdValue = Integer.parseInt(newValue);
                    task.setId(projectIdValue);
                    System.out.println("Id проекта успешно изменён");
                }
                catch (Exception e){
                    System.out.println(e);
                }
                break;
            default:
                System.out.println("Такого поля не существует");
                break;
        }
    }
    @Override
    public void updateById(int taskId, String fieldForUpdate, String newValue){
        Task task = null;
        for(Map.Entry<String, Task> elem : taskMap.entrySet()){
            if(elem.getValue().getId() == taskId){
                task = taskMap.get(elem.getKey());
                break;
            }
        }

        if(task == null){
            System.out.println("Задача с таким id не найдена");
            return;
        }
        switch(fieldForUpdate.toLowerCase()){
            case "id":
                try{
                    int idValue = Integer.parseInt(newValue);
                    if(findById(idValue) != null) System.out.println("Этот id уже занят");
                    else {
                        task.setId(idValue);
                        System.out.println("Id задачи успешно изменён");
                    }
                }
                catch (Exception e){
                    System.out.println(e);
                }
                break;
            case "название":
                try{
                    if(findByName(newValue) != null) System.out.println("Это имя проекта уже занято");
                    else {
                        task.setName(newValue);
                        System.out.println("Имя проекта успешно изменено");
                    }
                }
                catch (Exception e){
                    System.out.println(e);
                }
                break;
            case "описание":
                task.setDescription(newValue);
                System.out.println("Описание успешно изменено");
                break;
            case "дата начала":
                try{
                    task.setDateStart(LocalDate.parse(newValue));
                    System.out.println("Дата начала проекта успешно изменена");
                }
                catch(Exception e){
                    System.out.println(e);
                }
                break;
            case "дата окончания":
                try{
                    task.setDateFinish(LocalDate.parse(newValue));
                    System.out.println("дата окончания проекта успешно изменена");
                }
                catch(Exception e){
                    System.out.println(e);
                }
                break;
            case "id проекта":
                try{
                    int projectIdValue = Integer.parseInt(newValue);
                    task.setProjectId(projectIdValue);
                    System.out.println("Id Проекта успешно изменён");
                }
                catch (Exception e){
                    System.out.println(e);
                }
                break;
            default:
                System.out.println("Такого поля не существует");
                break;
        }
    }

    //DELETE
    @Override
    public void deleteByName(String taskName){
        //+Удалить из проекта, которому принадлежит задача
        taskMap.remove(taskName);
    }

    @Override
    public int getRepositorySize(){
        return taskMap.size();
    }
}
