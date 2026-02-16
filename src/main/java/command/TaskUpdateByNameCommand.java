package command;

import service.TaskService;

import java.util.Scanner;

@RequiresAuth
public class TaskUpdateByNameCommand implements Command{
    private final Scanner scanner;
    private final TaskService taskService;
    String pattern = "update_by_name task";

    public TaskUpdateByNameCommand(Scanner scanner, TaskService taskService){
        this.scanner = scanner;
        this.taskService = taskService;
    }

    @Override
    public void process() {
        System.out.println("Выбрано обновление задачи по имени");
        System.out.print("Введите имя: ");
        String name = scanner.nextLine();
        System.out.print("Введите название поле для изменения: ");
        String fieldForUpdate = scanner.nextLine();
        System.out.print("Введите новое значение: ");
        String newValue = scanner.nextLine();
        taskService.updateByName(name, fieldForUpdate, newValue);
    }

    @Override
    public String description() {
        String description = pattern + " - Команда обновляет(изменяет) сущность Проект по Имени";
        return description;
    }

    @Override
    public String getName() {
        return pattern;
    }
}
