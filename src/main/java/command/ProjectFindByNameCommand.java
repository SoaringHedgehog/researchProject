package command;

import service.ProjectService;

import java.util.Scanner;

@RequiresAuth
public class ProjectFindByNameCommand implements Command{
    private final Scanner scanner;
    private final ProjectService projectService;
    String pattern = "find_by_name project";

    public ProjectFindByNameCommand(Scanner scanner, ProjectService projectService){
        this.scanner = scanner;
        this.projectService = projectService;
    }

    @Override
    public void process() {
        System.out.println("Выбран поиск проекта по имени");
        System.out.println("Введите имя проекта");
        String projectName = scanner.nextLine();
        System.out.println(projectService.findByName(projectName));
    }

    @Override
    public String description() {
        String description = pattern +" - Команда ищет сущность Проект по Имени";
        return description;
    }

    @Override
    public String getName() {
        return pattern;
    }
}
