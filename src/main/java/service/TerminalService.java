package service;

import command.*;
import command.CommandRegistry;
import entity.Session;

import java.util.HashMap;
import java.util.Scanner;

public class TerminalService implements ServiceLocator{
    Scanner scanner = new Scanner(System.in);
    private final ProjectService projectService;
    private final TaskService taskService;
    private final UserService userService;
    private final CommandRegistry commandRegistry;
    private final Session session;

    public TerminalService(ProjectService projectService, TaskService taskService, UserService userService, HashMap<String, Command> map){
        this.taskService = taskService;
        this.projectService = projectService;
        this.userService = userService;
        this.session = new Session();
        this.commandRegistry = new CommandRegistry(scanner, projectService, taskService, userService, session);
    }

    // TODO throw Exception с message(println) - RuntimeException + обернуть TerminalService в try - catch
    public void start(){
        while(true){
            System.out.print("Введите команду: ");
            try{
                String commandString = scanner.nextLine().toLowerCase();
                Command command = commandRegistry.getByName(commandString);

                if (command != null && command.getClass().isAnnotationPresent(RequiresAuth.class) && session.getCurrentUser() == null) {
                    throw new RuntimeException("Для этой команды необходимо войти в систему.");
                }
                else if(commandString.isEmpty()) continue;
                else if(command == null)
                    throw new RuntimeException("Такой команды не существует. Для просмотра существующих команд вызовите команду 'help'");
                else{
                    command.process();
                }
            }
            catch (RuntimeException e){
                System.out.println(e);
            }
            catch(Exception e){
                System.out.println(e);
            }
        }
    }
}
