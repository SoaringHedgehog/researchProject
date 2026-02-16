package command;

import service.TaskService;

import java.util.Scanner;

@RequiresAuth
public class TaskUpdateByIdCommand implements Command{
    private final Scanner scanner;
    private final TaskService taskService;
    String pattern = "update_by_id task";

    public TaskUpdateByIdCommand(Scanner scanner, TaskService taskService){
        this.scanner = scanner;
        this.taskService = taskService;
    }

    @Override
    public void process() {
        System.out.println("Выбрано обновление задачи по id");
        System.out.print("Введите id: ");
        String id = scanner.nextLine();
        System.out.print("Введите название поле для изменения: ");
        String fieldForUpdate = scanner.nextLine();
        System.out.print("Введите новое значение: ");
        String newValue = scanner.nextLine();
        taskService.updateById(id, fieldForUpdate, newValue);
    }

    @Override
    public String description() {
        String description = pattern + " - Команда обновляет(изменяет) сущность Задача по ID";
        return description;
    }

    @Override
    public String getName() {
        return pattern;
    }
}
