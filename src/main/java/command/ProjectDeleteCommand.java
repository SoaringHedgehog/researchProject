package command;

import entity.Project;
import entity.User;
import service.ProjectService;
import service.UserService;

import java.util.Scanner;

@RequiresAuth
public class ProjectDeleteCommand implements Command{
    private final Scanner scanner;
    private final ProjectService projectService;
    private final UserService userService;
    String pattern = "delete_by_name project";

    public ProjectDeleteCommand(Scanner scanner, ProjectService projectService, UserService userService){
        this.scanner = scanner;
        this.projectService = projectService;
        this.userService = userService;
    }

    @Override
    public void process() {
        System.out.println("Выбрано удаление проекта");
        System.out.println("Введите имя проекта");
        String projectName = scanner.nextLine();
        Project project = projectService.deleteByName(projectName);
        User user = userService.findById(String.valueOf(project.getUserId()));
        user.getProjects().remove(project);
    }

    @Override
    public String description() {
        String description = pattern + " - Команда удаляет сущность Проект по Имени";
        return description;
    }

    @Override
    public String getName() {
        return pattern;
    }
}
