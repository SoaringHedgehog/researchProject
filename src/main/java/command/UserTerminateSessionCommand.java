package command;

import entity.Session;
import service.UserService;

@RequiresAuth
public class UserTerminateSessionCommand implements Command{
    private final UserService userService;
    Session session;
    String pattern = "terminate_current_session";

    public UserTerminateSessionCommand(UserService userService, Session session){
        this.userService = userService;
        this.session = session;
    }

    @Override
    public void process() {
        System.out.println("Выбрано окончание сессии текущего пользователя");
        userService.terminateSession(session);
    }

    @Override
    public String description() {
        String description = pattern + " - Команда позволяет завершить пользователя в системе";
        return description;
    }

    @Override
    public String getName() {
        return pattern;
    }
}
