package command;

import entity.Project;
import entity.Task;
import service.ProjectService;
import service.TaskService;

import java.util.Scanner;

@RequiresAuth
public class TaskDeleteCommand implements Command{
    private final Scanner scanner;
    private final TaskService taskService;
    private final ProjectService projectService;
    String pattern = "delete_by_name task";

    public TaskDeleteCommand(Scanner scanner, TaskService taskService, ProjectService projectService){
        this.scanner = scanner;
        this.taskService = taskService;
        this.projectService = projectService;
    }

    @Override
    public void process() {
        System.out.println("Выбрано удаление задачи");
        System.out.println("Введите имя задачи");
        String taskName = scanner.nextLine();
        Task task = taskService.deleteByName(taskName);
        Project project = projectService.findById(String.valueOf(task.getProjectId()));
        project.getTasks().remove(task);
    }

    @Override
    public String description() {
        String description = pattern +" - Команда удаляет сущность Задача по Имени";
        return description;
    }

    @Override
    public String getName() {
        return pattern;
    }
}
