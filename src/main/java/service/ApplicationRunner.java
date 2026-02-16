package service;

import command.Command;
import entity.RoleType;
import entity.User;

import java.util.HashMap;


public class ApplicationRunner {
    private final ProjectService projectService;
    private final TaskService taskService;
    private final UserService userService;
    private final TerminalService terminalService;
    private FileServiceImpl fileService;
    private HashMap<String, Command> commandMap;

    public ApplicationRunner(){
        commandMap = new HashMap<>();

        userService = new UserService();
        taskService = new TaskService();
        projectService = new ProjectService(taskService, userService);

        fileService = new FileServiceImpl(projectService, taskService, userService);
        terminalService = new TerminalService(projectService, taskService, userService, commandMap);
    }

    public void run(){
        fileService.initialize();
        terminalService.start();
    }
}