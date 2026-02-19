package service;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDate;

import entity.Project;
import entity.RoleType;
import entity.Task;
import entity.User;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class FileServiceImpl implements FileService{
    private final String FILE_PATH_PROJECTS = "src/main/java/resources/projects.xlsx";
    private final String FILE_PATH_TASKS = "src/main/java/resources/tasks.xlsx";
    private final String FILE_PATH_USERS = "src/main/java/resources/users.xlsx";

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
        readUsersToMap(FILE_PATH_USERS);
        System.out.println("Ползователи успешно заполнены из файла");
        System.out.println();

        System.out.println("Проекты: ");
        readProjectsToMap(FILE_PATH_PROJECTS);
        System.out.println("Проекты успешно заполнены из файла");
        System.out.println();

        System.out.println("Задачи: ");
        readTasksToMap(FILE_PATH_TASKS);
        System.out.println("Задачи успешно заполнены из файла");
        System.out.println();
    }

    private void readUsersToMap(String filePath) {
        try (FileInputStream fis = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheetAt(0); // первый лист

            Row headerRow = sheet.getRow(0);
            if (headerRow == null) {
                throw new RuntimeException("Файл не содержит заголовков");
            }

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
                    default:
                        System.out.println("Такого поля для чтения не существует");
                        break;
                }
            }

            // Итерация по строкам данных (начиная со второй)
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row == null) continue;

                // Создаём объект User
                User user = new User(
                        Integer.parseInt(getCellValueAccordingType(row.getCell(idCol))),
                        getCellValueAccordingType(row.getCell(loginCol)),
                        getCellValueAccordingType(row.getCell(passwordCol)),
                        getCellValueAsRoleType(row.getCell(roleTypeCol))
                );

                userService.registerUser(String.valueOf(user.getId()), user.getLogin(), user.getPasswordHash(), user.getRoleType());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void readProjectsToMap(String filePath) {
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
                    default:
                        System.out.println("Такого поля для чтения не существует");
                        break;
                }
            }

            // Итерация по строкам данных (начиная со второй)
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row == null) continue;

                Project project = new Project(
                        Integer.parseInt(getCellValueAccordingType(row.getCell(idCol))),
                        getCellValueAccordingType(row.getCell(nameCol)),
                        getCellValueAccordingType(row.getCell(descriptionCol)),
                        getCellValueAsLocalDate(row.getCell(dateStartCol)),
                        getCellValueAsLocalDate(row.getCell(dateFinishCol)),
                        Integer.parseInt(getCellValueAccordingType(row.getCell(userIdCol)))
                );

                projectService.create(String.valueOf(project.getId()), project.getName(), project.getDescription(), String.valueOf(project.getDateStart()), String.valueOf(project.getDateFinish()), String.valueOf(project.getUserId()));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void readTasksToMap(String filePath) {
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

            // Итерация по строкам данных (начиная со второй)
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row == null) continue;

                // Создаём объект User
                Task task = new Task(
                        Integer.parseInt(getCellValueAccordingType(row.getCell(idCol))),
                        getCellValueAccordingType(row.getCell(nameCol)),
                        getCellValueAccordingType(row.getCell(descriptionCol)),
                        getCellValueAsLocalDate(row.getCell(dateStartCol)),
                        getCellValueAsLocalDate(row.getCell(dateFinishCol)),
                        Integer.parseInt(getCellValueAccordingType(row.getCell(projectIdCol)))
                );
                taskService.create(String.valueOf(task.getId()), task.getName(), task.getDescription(), String.valueOf(task.getDateStart()), String.valueOf(task.getDateFinish()), String.valueOf(task.getProjectId()));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
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
