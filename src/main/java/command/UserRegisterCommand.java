package command;

import entity.RoleType;
import entity.Session;
import entity.User;
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
            RoleType[] roleTypes = RoleType.values();
            System.out.println("Введите номер роли из перечисленного: ");
            for (int i = 0; i < roleTypes.length; i++) {
                System.out.println(i + " " + roleTypes[i]);
            }
            int roleType = Integer.parseInt(scanner.nextLine());
            if(roleType < 0 || roleType > roleTypes.length - 1){
                System.out.println("Такой роли не существует");
                return;
            }
            userService.registerUser(id, login, password, roleTypes[roleType]);
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
