package command;

import entity.Session;
import service.ProjectService;
import service.TaskService;
import service.UserService;

import java.util.HashMap;
import java.util.Scanner;

public class CommandRegistry {
    Scanner scanner;
    ProjectService projectService;
    TaskService taskService;
    UserService userService;
    Session session;
    HashMap<String, Command> commandMap;

    public CommandRegistry(Scanner scanner, ProjectService projectService, TaskService taskService, UserService userService, Session session){
        this.scanner = scanner;
        this.projectService = projectService;
        this.taskService = taskService;
        this.userService = userService;
        this.session = session;
        commandMap = new HashMap<>();
        init();
    }

    public void init(){
        Command command = new ExitCommand();
        commandMap.put(command.getName(), command);
        
        command = new ProjectCreateCommand(scanner, projectService, userService);
        commandMap.put(command.getName(), command);
        command = new ProjectDeleteCommand(scanner, projectService, userService);
        commandMap.put(command.getName(), command);
        command = new ProjectFindByIdCommand(scanner, projectService);
        commandMap.put(command.getName(), command);
        command = new ProjectFindByNameCommand(scanner, projectService);
        commandMap.put(command.getName(), command);
        command = new ProjectUpdateByIdCommand(scanner, projectService);
        commandMap.put(command.getName(), command);
        command = new ProjectUpdateByNameCommand(scanner, projectService);
        commandMap.put(command.getName(), command);

        command = new TaskCreateCommand(scanner, taskService, projectService);
        commandMap.put(command.getName(), command);
        command = new TaskDeleteCommand(scanner, taskService, projectService);
        commandMap.put(command.getName(), command);
        command = new TaskFindByIdCommand(scanner, taskService);
        commandMap.put(command.getName(), command);
        command = new TaskFindByNameCommand(scanner, taskService);
        commandMap.put(command.getName(), command);
        command = new TaskUpdateByIdCommand(scanner, taskService);
        commandMap.put(command.getName(), command);
        command = new TaskUpdateByNameCommand(scanner, taskService);
        commandMap.put(command.getName(), command);

        command = new UserAuthorizeCommand(scanner, userService, session);
        commandMap.put(command.getName(), command);
        command = new UserPrintCurrentProfileInfoCommand(userService, session);
        commandMap.put(command.getName(), command);
        command = new UserRegisterCommand(scanner, userService);
        commandMap.put(command.getName(), command);
        command = new UserTerminateSessionCommand(userService, session);
        commandMap.put(command.getName(), command);
        command = new UserUpdatePasswordCommand(scanner, userService, session);
        commandMap.put(command.getName(), command);

        command = new HelpCommand(commandMap);
        commandMap.put(command.getName(), command);
    }

    public Command getByName(String command){
        return commandMap.get(command);
    }
}
