package command;

import entity.Session;
import service.UserService;

import java.util.Scanner;

public class UserAuthorizeCommand implements Command{
    private final Scanner scanner;
    private final UserService userService;
    Session session;

    String pattern = "authorize user";

    public UserAuthorizeCommand(Scanner scanner, UserService userService, Session session){
        this.scanner = scanner;
        this.userService = userService;
        this.session = session;
    }

    @Override
    public void process() {
        System.out.println("Выбрана авторизация пользователя");
        System.out.print("Введите логин: ");
        String login = scanner.nextLine();
        System.out.print("Введите пароль: ");
        String password = scanner.nextLine();
        userService.authorizeUser(login, password, session);
    }

    @Override
    public String description() {
        String description = pattern + " - Команда позволяет войти в систему при верных введённых данных логина и пароля";
        return description;
    }

    @Override
    public String getName() {
        return pattern;
    }
}
