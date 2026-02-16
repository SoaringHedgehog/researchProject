package command;

import service.ProjectService;

import java.util.Scanner;

@RequiresAuth
public class ProjectUpdateByNameCommand implements Command{
    private final Scanner scanner;
    private final ProjectService projectService;
    String pattern = "update_by_name project";

    public ProjectUpdateByNameCommand(Scanner scanner, ProjectService projectService){
        this.scanner = scanner;
        this.projectService = projectService;
    }

    @Override
    public void process() {
        System.out.println("Выбрано обновление проекта по имени");
        System.out.print("Введите имя: ");
        String name = scanner.nextLine();
        System.out.print("Введите название поле для изменения: ");
        String fieldForUpdate = scanner.nextLine();
        System.out.print("Введите новое значение: ");
        String newValue = scanner.nextLine();
        projectService.updateByName(name, fieldForUpdate, newValue);
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
