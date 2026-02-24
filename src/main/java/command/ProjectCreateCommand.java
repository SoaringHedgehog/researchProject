package command;

import entity.Project;
import entity.User;
import service.ProjectService;
import service.UserService;

import java.util.Scanner;

@RequiresAuth
public class ProjectCreateCommand implements Command{
    private final Scanner scanner;
    private final ProjectService projectService;
    private final UserService userService;
    public final static String pattern = "create project";

    public ProjectCreateCommand(Scanner scanner, ProjectService projectService, UserService userService){
        this.scanner = scanner;
        this.projectService = projectService;
        this.userService = userService;
    }

    @Override
    public void process() {
        System.out.println("Выбрано создание проекта");
        System.out.print("Введите id, если такой есть. Если нет, просто нажмите Enter: ");
        String id = null;
        try{
            id = scanner.nextLine();
        }
        catch(NumberFormatException e){
            id = null;
        }
        finally{
            System.out.print("Введите имя: ");
            String name = scanner.nextLine();
            System.out.print("Введите описание: ");
            String description = scanner.nextLine();
            System.out.print("Введите дату начала проекта(dd.MM.yyyy): ");
            String dateStart = scanner.nextLine();
            System.out.print("Введите дату окончания проекта(dd.MM.yyyy): ");
            String dateFinish = scanner.nextLine();
            System.out.print("Введите id пользователя проекта: ");
            String userId = scanner.nextLine();
            Project project = projectService.create(id, name, description, dateStart, dateFinish, userId);
            User user = userService.findById(userId);
            user.getProjects().add(project);
        }
    }

    @Override
    public String description() {
        String description = pattern + " - Команда создаёт сущность Проект";
        return description;
    }

    @Override
    public String getName() {
        return pattern;
    }
}
