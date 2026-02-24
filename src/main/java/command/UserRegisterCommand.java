package command;

import entity.RoleType;
import service.UserService;

import java.util.Scanner;

public class UserRegisterCommand implements Command{
    private final Scanner scanner;
    private final UserService userService;
    String pattern = "register user";

    public UserRegisterCommand(Scanner scanner, UserService userService){
        this.scanner = scanner;
        this.userService = userService;
    }

    @Override
    public void process() {
        System.out.println("Выбрана регистрация пользователя");
        System.out.print("Введите id, если такой есть. Если нет, просто нажмите Enter: ");
        String id = null;
        try{
            id = scanner.nextLine();
        }
        catch(NumberFormatException e){
            id = null;
        }
        finally{
            System.out.print("Введите логин: ");
            String login = scanner.nextLine();
            System.out.print("Введите пароль: ");
            String password = scanner.nextLine();
            System.out.println("Введите номер роли из перечисленного: ");
            userService.printRoleTypes();
            int roleTypeIndex = Integer.parseInt(scanner.nextLine());
            String roleType = userService.chooseRoleType(roleTypeIndex).toString();
            userService.registerUser(id, login, password, roleType);
        }
    }

    @Override
    public String description() {
        String description = pattern + " - Команда позволяет зарегистрировать пользователя в системе";
        return description;
    }

    @Override
    public String getName() {
        return pattern;
    }
}
