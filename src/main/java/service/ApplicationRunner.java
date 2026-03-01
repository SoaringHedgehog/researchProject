package service;

import command.Command;

import java.util.HashMap;


public class ApplicationRunner {
    private final UserService userService;
    private final ProjectService projectService;
    private final TaskService taskService;
    private final TerminalService terminalService;

    private FileServiceImpl fileService;

    private HashMap<String, Command> commandMap;

    public ApplicationRunner(){
        commandMap = new HashMap<>();

        userService = new UserService();
        taskService = new TaskService();
        projectService = new ProjectService(taskService, userService);
        terminalService = new TerminalService(projectService, taskService, userService, commandMap);

        fileService = new FileServiceImpl(projectService, taskService, userService);
    }

    public void run(){
        fileService.initialize();
        terminalService.start();
    }
}