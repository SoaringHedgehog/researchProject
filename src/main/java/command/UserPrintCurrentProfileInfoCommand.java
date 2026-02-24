package command;

import entity.Session;
import service.UserService;

@RequiresAuth
public class UserPrintCurrentProfileInfoCommand implements Command{
    private final UserService userService;
    Session session;
    String pattern = "print_current_profile_info";

    public UserPrintCurrentProfileInfoCommand(UserService userService, Session session){
        this.userService = userService;
        this.session = session;
    }

    @Override
    public void process() {
        System.out.println("Выбран вывод информации профиля текущего пользователя");
        userService.printCurrentProfileInfo(session);
    }

    @Override
    public String description() {
        String description = pattern + " - Команда позволяет вывести информацию о профиле текущего пользователя";
        return description;
    }

    @Override
    public String getName() {
        return pattern;
    }
}
