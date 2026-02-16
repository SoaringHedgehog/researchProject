package command;

import service.TaskService;

import java.util.Scanner;

@RequiresAuth
public class TaskCreateCommand implements Command{
    private final Scanner scanner;
    private final TaskService taskService;
    String pattern = "create task";

    public TaskCreateCommand(Scanner scanner, TaskService taskService){
        this.scanner = scanner;
        this.taskService = taskService;
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
            System.out.print("Введите дату начала проекта: ");
            String dateStart = scanner.nextLine();
            System.out.print("Введите дату окончания проекта: ");
            String dateFinish = scanner.nextLine();
            System.out.print("Введите проект, которому принадлежит задача: ");
            String projectId = scanner.nextLine();
            taskService.create(id, name, description, dateStart, dateFinish, projectId);
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
