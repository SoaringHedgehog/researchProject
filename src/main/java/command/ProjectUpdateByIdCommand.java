package command;

import service.ProjectService;

import java.util.Scanner;

@RequiresAuth
public class ProjectUpdateByIdCommand implements Command{
    private final Scanner scanner;
    private final ProjectService projectService;
    String pattern = "update_by_id project";

    public ProjectUpdateByIdCommand(Scanner scanner, ProjectService projectService){
        this.scanner = scanner;
        this.projectService = projectService;
    }

    @Override
    public void process() {
        System.out.println("Выбрано обновление проекта по id");
        System.out.print("Введите id: ");
        String id = scanner.nextLine();
        System.out.print("Введите название поле для изменения: ");
        String fieldForUpdate = scanner.nextLine();
        System.out.print("Введите новое значение: ");
        String newValue = scanner.nextLine();
        projectService.updateById(id, fieldForUpdate, newValue);
    }

    @Override
    public String description() {
        String description = pattern + " - Команда обновляет(изменяет) сущность Проект по ID";
        return description;
    }

    @Override
    public String getName() {
        return pattern;
    }
}
