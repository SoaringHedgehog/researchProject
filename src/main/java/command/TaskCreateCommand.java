package command;

import entity.Project;
import entity.Task;
import service.ProjectService;
import service.TaskService;

import java.util.Scanner;

@RequiresAuth
public class TaskCreateCommand implements Command{
    private final Scanner scanner;
    private final TaskService taskService;
    private final ProjectService projectService;
    String pattern = "create task";

    public TaskCreateCommand(Scanner scanner, TaskService taskService, ProjectService projectService){
        this.scanner = scanner;
        this.taskService = taskService;
        this.projectService = projectService;
    }

    @Override
    public void process() {
        System.out.println("Выбрано создание задачи");
        System.out.print("Введите id, если такой есть. Если нет, просто нажмите Enter: ");
        String id = null;
        try{
            id = scanner.nextLine();
        }
        catch(NumberFormatException e){
            id = null;
        }
        finally{
            System.out.print("Введите имя: ");
            String name = scanner.nextLine();
            System.out.print("Введите описание: ");
            String description = scanner.nextLine();
            System.out.print("Введите дату начала проекта(dd.MM.yyyy): ");
            String dateStart = scanner.nextLine();
            System.out.print("Введите дату окончания проекта(dd.MM.yyyy): ");
            String dateFinish = scanner.nextLine();
            System.out.print("Введите id проекта, которому принадлежит задача: ");
            String projectId = scanner.nextLine();
            Task task = taskService.create(id, name, description, dateStart, dateFinish, projectId);
            Project project = projectService.findById(projectId);
            project.getTasks().add(task);
        }
    }

    @Override
    public String description() {
        String description = pattern + " - Команда создаёт сущность Задача";
        return description;
    }

    @Override
    public String getName() {
        return pattern;
    }
}
