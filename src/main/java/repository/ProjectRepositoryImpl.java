package repository;

import entity.Project;
import entity.Task;
import entity.User;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class ProjectRepositoryImpl implements ProjectRepository{
    //<name, project>
    private HashMap<String, Project> projectMap;

    public ProjectRepositoryImpl(){
        this.projectMap = new HashMap<>();
    }

    //CREATE
    @Override
    public void create(int id, String name, String description, LocalDate dateStart, LocalDate dateFinish, int userId){
        if(!projectMap.containsKey(name)){
            Project project = new Project(id, name, description, dateStart, dateFinish, userId);
            projectMap.put(project.getName(), project);
            System.out.println("Проект успешно добавлен");
        }
        else{
            System.out.println("Проекта с таким названием уже существует. Попробуйте другое");
        }
    }

    //READ
    @Override
    public Project findByName(String projectName){
        Project project = projectMap.get(projectName);
        if(project == null) System.out.println("Проект не найден");
        return project;
    }
    @Override
    public Project findById(int projectId){
        for(Map.Entry<String, Project> project : projectMap.entrySet()){
            if(project.getValue().getId() == projectId){
                return projectMap.get(project.getKey());
            }
        }
        System.out.println("Проект не найден");
        return null;
    }

    //UPDATE
    @Override
    public void updateByName(String projectName, String fieldForUpdate, String newValue){
        Project project = projectMap.get(projectName);
        switch(fieldForUpdate.toLowerCase()){
            case "id":
                try{
                    int idValue = Integer.parseInt(newValue);
                    if(findById(idValue) != null) System.out.println("Этот id уже занят");
                    else {
                        project.setId(idValue);
                        System.out.println("id Проекта успешно изменён");
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
                        project.setName(newValue);
                        System.out.println("Имя проекта успешно изменено");
                    }
                }
                catch (Exception e){
                    System.out.println(e);
                }
                break;
            case "описание":
                project.setDescription(newValue);
                System.out.println("Описание успешно изменено");
                break;
            case "дата начала":
                try{
                    LocalDate localDate = LocalDate.parse(newValue, DateTimeFormatter.ofPattern("dd.MM.yyyy"));
                    project.setDateStart(localDate);
                    System.out.println("Дата начала проекта успешно изменена");
                }
                catch(Exception e){
                    System.out.println(e);
                }
                break;
            case "дата окончания":
                try{
                    LocalDate localDate = LocalDate.parse(newValue, DateTimeFormatter.ofPattern("dd.MM.yyyy"));
                    project.setDateFinish(localDate);
                    System.out.println("дата окончания проекта успешно изменена");
                }
                catch(Exception e){
                    System.out.println(e);
                }
                break;
            case "id пользователя":
                try{
                    int userIdValue = Integer.parseInt(newValue);
                    project.setId(userIdValue);
                    System.out.println("Пользователь проекта успешно изменён");
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
    public void updateById(int projectId, String fieldForUpdate, String newValue){
        Project project = null;
        for(Map.Entry<String, Project> elem : projectMap.entrySet()){
            if(elem.getValue().getId() == projectId){
                project = projectMap.get(elem.getKey());
                break;
            }
        }

        if(project == null){
            System.out.println("Проект с таким id не найден");
            return;
        }
        switch(fieldForUpdate.toLowerCase()){
            case "id":
                try{
                    int idValue = Integer.parseInt(newValue);
                    if(findById(idValue) != null) System.out.println("Этот id уже занят");
                    else {
                        project.setId(idValue);
                        System.out.println("id Проекта успешно изменён");
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
                        project.setName(newValue);
                        System.out.println("Имя проекта успешно изменено");
                    }
                }
                catch (Exception e){
                    System.out.println(e);
                }
                break;
            case "описание":
                project.setDescription(newValue);
                System.out.println("Описание успешно изменено");
                break;
            case "дата начала":
                try{
                    project.setDateStart(LocalDate.parse(newValue));
                    System.out.println("Дата начала проекта успешно изменена");
                }
                catch(Exception e){
                    System.out.println(e);
                }
                break;
            case "дата окончания":
                try{
                    project.setDateFinish(LocalDate.parse(newValue));
                    System.out.println("дата окончания проекта успешно изменена");
                }
                catch(Exception e){
                    System.out.println(e);
                }
                break;
            case "id пользователя":
                try{
                    int userIdValue = Integer.parseInt(newValue);
                    project.setUserId(userIdValue);
                    System.out.println("Пользователь проекта успешно изменён");
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
    public void delete(String projectName){
        if(projectMap.containsKey(projectName)){
            projectMap.remove(projectName);
            System.out.println("Проект успешно удалён");
        }
        else{
            System.out.println("Проект с таким названием не найден.");
        }
    }

    @Override
    public int getRepositorySize(){
        return projectMap.size();
    }

    @Override
    public void printTasks(String projectName){
        if(projectMap.containsKey(projectName)){
            Project project = projectMap.get(projectName);
            for(Task task : project.getTasks()){
                System.out.println(task.toString());
            }
        }
        else{
            System.out.println("Проект с таким названием не найден");
        }
    }

    @Override
    public void printAllProjects(){
        for(Map.Entry<String, Project> entry : projectMap.entrySet()){
            String key = entry.getKey();
            Project project = entry.getValue();
            System.out.println("Ключ: " + key + "\nЗначение: " + project);
        }
    }
}