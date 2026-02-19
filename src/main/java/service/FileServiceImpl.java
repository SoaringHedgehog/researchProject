package service;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.Map;

import entity.Project;
import entity.RoleType;
import entity.Task;
import entity.User;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class FileServiceImpl implements FileService{
    String filePathProjects = "src/main/java/resources/projects.xlsx";
    String filePathTasks = "src/main/java/resources/tasks.xlsx";
    String filePathUsers = "src/main/java/resources/users.xlsx";

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
        // TODO считывание сразу в БД
        System.out.println("Пользователи:");
        Map<String, User> userMap = readUsersToMap(filePathUsers);
        userMap.forEach((key, user) -> System.out.println(key + " -> " + user));
        userMap.forEach((key, value) -> {
            User user = userMap.get(key);
            userService.registerUser(String.valueOf(user.getId()), user.getLogin(), user.getPasswordHash(), user.getRoleType());
        });
        System.out.println();

        System.out.println("Проекты: ");
        Map<String, Project> projectMap = readProjectsToMap(filePathProjects);
        projectMap.forEach((key, project) -> System.out.println(key + " -> " + project));
        projectMap.forEach((key, value) -> {
            Project project = projectMap.get(key);
            projectService.create(String.valueOf(project.getId()), project.getName(), project.getDescription(), String.valueOf(project.getDateStart()), String.valueOf(project.getDateFinish()), String.valueOf(project.getUserId()));
        });
        System.out.println();

        System.out.println("Задачи: ");
        Map<String, Task> taskMap = readTasksToMap(filePathTasks);
        taskMap.forEach((key, task) -> System.out.println(key + " -> " + task));
        taskMap.forEach((key, value) -> {
            Task task = taskMap.get(key);
            taskService.create(String.valueOf(task.getId()), task.getName(), task.getDescription(), String.valueOf(task.getDateStart()), String.valueOf(task.getDateFinish()), String.valueOf(task.getProjectId()));
        });
        System.out.println();
    }

    public Map<String, User> readUsersToMap(String filePath) {
        Map<String, User> userMap = new LinkedHashMap<>(); // сохраняет порядок вставки

        try (FileInputStream fis = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheetAt(0); // первый лист

            // Предполагаем, что первая строка (индекс 0) — заголовки
            Row headerRow = sheet.getRow(0);
            if (headerRow == null) {
                throw new RuntimeException("Файл не содержит заголовков");
            }

            // Определяем индексы колонок (можно хардкодить, но так надёжнее)
            int idCol = -1;
            int loginCol = -1;
            int passwordCol = -1;
            int roleTypeCol = -1;

            for (Cell cell : headerRow) {
                String header = cell.getStringCellValue().toLowerCase().trim();
                switch (header) {
                    case "id":
                        idCol = cell.getColumnIndex();
                        break;
                    case "login":
                        loginCol = cell.getColumnIndex();
                        break;
                    case "passwordhash":
                        passwordCol = cell.getColumnIndex();
                        break;
                    case "roletype":
                        roleTypeCol = cell.getColumnIndex();
                        break;
                }
            }

            if (loginCol == -1) {
                throw new RuntimeException("Колонка 'user_id' не найдена");
            }

            // Итерация по строкам данных (начиная со второй)
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row == null) continue;

                int id = Integer.parseInt(getCellValueAccordingType(row.getCell(idCol)));
                // Получаем значение ключа (user_id) как строку
                String login = getCellValueAccordingType(row.getCell(loginCol));

                // Создаём объект User
                User user = new User(
                        id,
                        getCellValueAccordingType(row.getCell(loginCol)),
                        getCellValueAccordingType(row.getCell(passwordCol)),
                        getCellValueAsRoleType(row.getCell(roleTypeCol))
                );

                userMap.put(login, user);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return userMap;
    }

    // TODO not static (Поч не рекомендуется использовать static)
    public Map<String, Project> readProjectsToMap(String filePath) {
        Map<String, Project> projectMap = new LinkedHashMap<>(); // сохраняет порядок вставки

        try (FileInputStream fis = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheetAt(0); // первый лист

            // Предполагаем, что первая строка (индекс 0) — заголовки
            Row headerRow = sheet.getRow(0);
            if (headerRow == null) {
                throw new RuntimeException("Файл не содержит заголовков");
            }

            // Определяем индексы колонок (можно хардкодить, но так надёжнее)
            int idCol = -1;
            int nameCol = -1;
            int descriptionCol = -1;
            int dateStartCol = -1;
            int dateFinishCol = -1;
            int userIdCol = -1;

            for (Cell cell : headerRow) {
                String header = cell.getStringCellValue().toLowerCase().trim();
                switch (header) {
                    case "id":
                        idCol = cell.getColumnIndex();
                        break;
                    case "name":
                        nameCol = cell.getColumnIndex();
                        break;
                    case "description":
                        descriptionCol = cell.getColumnIndex();
                        break;
                    case "datestart":
                        dateStartCol = cell.getColumnIndex();
                        break;
                    case "datefinish":
                        dateFinishCol = cell.getColumnIndex();
                        break;
                    case "userid":
                        userIdCol = cell.getColumnIndex();
                        break;
                }
            }

            if (nameCol == -1) {
                throw new RuntimeException("Колонка 'user_id' не найдена");
            }

            // Итерация по строкам данных (начиная со второй)
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row == null) continue;

                // Получаем значение ключа (user_id) как строку
                String name = getCellValueAccordingType(row.getCell(nameCol));

                Project project = new Project(
                        Integer.parseInt(getCellValueAccordingType(row.getCell(idCol))),
                        name,
                        getCellValueAccordingType(row.getCell(descriptionCol)),
                        getCellValueAsLocalDate(row.getCell(dateStartCol)),
                        getCellValueAsLocalDate(row.getCell(dateFinishCol)),
                        Integer.parseInt(getCellValueAccordingType(row.getCell(userIdCol)))
                );

                projectMap.put(name, project);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return projectMap;
    }

    public Map<String, Task> readTasksToMap(String filePath) {
        Map<String, Task> taskMap = new LinkedHashMap<>(); // сохраняет порядок вставки

        try (FileInputStream fis = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheetAt(0); // первый лист

            // Предполагаем, что первая строка (индекс 0) — заголовки
            Row headerRow = sheet.getRow(0);
            if (headerRow == null) {
                throw new RuntimeException("Файл не содержит заголовков");
            }

            // Определяем индексы колонок (можно хардкодить, но так надёжнее)
            int idCol = -1;
            int nameCol = -1;
            int descriptionCol = -1;
            int dateStartCol = -1;
            int dateFinishCol = -1;
            int projectIdCol = -1;

            for (Cell cell : headerRow) {
                String header = cell.getStringCellValue().toLowerCase().trim();
                switch (header) {
                    case "id":
                        idCol = cell.getColumnIndex();
                        break;
                    case "name":
                        nameCol = cell.getColumnIndex();
                        break;
                    case "description":
                        descriptionCol = cell.getColumnIndex();
                        break;
                    case "datestart":
                        dateStartCol = cell.getColumnIndex();
                        break;
                    case "datefinish":
                        dateFinishCol = cell.getColumnIndex();
                        break;
                    case "projectid":
                        projectIdCol = cell.getColumnIndex();
                        break;
                    default:
                        System.out.println("Такого поля для чтения не существует");
                        break;
                }
            }

            if (nameCol == -1) {
                throw new RuntimeException("Колонка 'user_id' не найдена");
            }

            // Итерация по строкам данных (начиная со второй)
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row == null) continue;

                // Получаем значение ключа (user_id) как строку
                String login = getCellValueAccordingType(row.getCell(nameCol));

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

                // Создаём объект User
                Task task = new Task(
                        Integer.parseInt(getCellValueAccordingType(row.getCell(idCol))),
                        login,
                        getCellValueAccordingType(row.getCell(descriptionCol)),
                        getCellValueAsLocalDate(row.getCell(dateStartCol)),
                        getCellValueAsLocalDate(row.getCell(dateFinishCol)),
                        Integer.parseInt(getCellValueAccordingType(row.getCell(projectIdCol)))
                );
                taskMap.put(login, task);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return taskMap;
    }

    /**
     * Безопасное получение строкового значения из ячейки.
     */
    private String getCellValueAccordingType(Cell cell) {
        if (cell == null) {
            return "";
        }
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return cell.getDateCellValue().toString();
                } else {
                    // Чтобы избежать научной нотации для длинных чисел
                    return String.valueOf((long) cell.getNumericCellValue());
                }
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case FORMULA:
                return cell.getCellFormula();
            case BLANK:
                return "";
            default:
                return "";
        }
    }

    private LocalDate getCellValueAsLocalDate(Cell cell) {
        if (cell == null) {
            throw new RuntimeException("Пустая ячейка. Невозможно привести к типу LocalDate");
        }
        return cell.getLocalDateTimeCellValue().toLocalDate();
    }

    private RoleType getCellValueAsRoleType(Cell cell) {
        if (cell == null) {
            throw new RuntimeException("Пустая ячейка. Невозможно привести к типу RoleType");
        }
        return RoleType.valueOf(cell.getStringCellValue());
    }
}
