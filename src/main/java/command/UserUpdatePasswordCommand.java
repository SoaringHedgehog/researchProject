package command;

import entity.Session;
import service.UserService;

import java.util.Scanner;

@RequiresAuth
public class UserUpdatePasswordCommand implements Command{
    private final Scanner scanner;
    private final UserService userService;
    Session session;
    String pattern = "update_password";

    public UserUpdatePasswordCommand(Scanner scanner, UserService userService, Session session){
        this.scanner = scanner;
        this.userService = userService;
        this.session = session;
    }

    @Override
    public void process() {
        System.out.println("Выбрано обновление пароля пользователя");
        System.out.print("Введите старый пароль: ");
        String oldPassword = scanner.nextLine();
        System.out.print("Введите новый пароль: ");
        String newPassword = scanner.nextLine();
        System.out.println("Повторно введите новый пароль для подтверждения: ");
        String repeatNewPassword = scanner.nextLine();
        userService.updatePassword(oldPassword, newPassword, repeatNewPassword, session);
    }

    @Override
    public String description() {
        String description = pattern + " - Команда позволяет поменять пароль текущему пользователю";
        return description;
    }

    @Override
    public String getName() {
        return pattern;
    }
}
