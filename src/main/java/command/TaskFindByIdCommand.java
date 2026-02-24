package command;

import service.TaskService;

import java.util.Scanner;

@RequiresAuth
public class TaskFindByIdCommand implements Command{
    private final Scanner scanner;
    private final TaskService taskService;
    String pattern = "find_by_id task";

    public TaskFindByIdCommand(Scanner scanner, TaskService taskService){
        this.scanner = scanner;
        this.taskService = taskService;
    }

    @Override
    public void process() {
        System.out.println("Выбран поиск задачи по id");
        System.out.println("Введите id проекта");
        String taskId = scanner.nextLine();
        System.out.println(taskService.findById(taskId));
    }

    @Override
    public String description() {
        String description = pattern + " - Команда ищет сущность Задача по ID";
        return description;
    }

    @Override
    public String getName() {
        return pattern;
    }
}
