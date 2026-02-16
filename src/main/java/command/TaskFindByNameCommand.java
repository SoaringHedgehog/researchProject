package command;

import service.TaskService;

import java.util.Scanner;

@RequiresAuth
public class TaskFindByNameCommand implements Command{
    private final Scanner scanner;
    private final TaskService taskService;
    String pattern = "find_by_name task";

    public TaskFindByNameCommand(Scanner scanner, TaskService taskService){
        this.scanner = scanner;
        this.taskService = taskService;
    }

    @Override
    public void process() {
        System.out.println("Выбран поиск задачи по имени");
        System.out.println("Введите имя задачи");
        String projectName = scanner.nextLine();
        System.out.println(taskService.findByName(projectName));
    }

    @Override
    public String description() {
        String description = pattern + " - Команда ищет сущность Задача по Имени";
        return description;
    }

    @Override
    public String getName() {
        return pattern;
    }
}
