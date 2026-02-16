package command;

import entity.Project;
import service.ProjectService;

import java.util.Scanner;

@RequiresAuth
public class ProjectFindByIdCommand implements Command{
    private final Scanner scanner;
    private final ProjectService projectService;
    String pattern = "find_by_id project";

    public ProjectFindByIdCommand(Scanner scanner, ProjectService projectService){
        this.scanner = scanner;
        this.projectService = projectService;
    }

    @Override
    public void process() {
        System.out.println("Выбран поиск проекта по id");
        System.out.println("Введите id проекта");
        int projectId = Integer.parseInt(scanner.nextLine());
        System.out.println(projectService.findById(projectId));
    }

    @Override
    public String description() {
        String description = pattern + " - Команда ищет сущность Проект по ID";
        return description;
    }

    @Override
    public String getName() {
        return pattern;
    }
}
