package service;

public class FileServiceImpl implements FileService{
    ProjectService projectService;
    TaskService taskService;
    UserService userService;

    public FileServiceImpl(ProjectService projectService, TaskService taskService, UserService userService){
        this.projectService = projectService;
        this.taskService = taskService;
        this.userService = userService;
    }

    @Override
    public void initialize() {
        System.out.println("Пользователи:");
        UserFileService userFileService = new UserFileService(userService);
        userFileService.readEntityToMap();
        System.out.println("Ползователи успешно заполнены из файла");
        System.out.println();

        System.out.println("Проекты: ");
        ProjectFileService projectFileService = new ProjectFileService(projectService, userService);
        projectFileService.readEntityToMap();
        System.out.println("Проекты успешно заполнены из файла");
        System.out.println();

        System.out.println("Задачи: ");
        TaskFileService taskFileService = new TaskFileService(taskService, projectService);
        taskFileService.readEntityToMap();
        System.out.println("Задачи успешно заполнены из файла");
        System.out.println();
    }
}
