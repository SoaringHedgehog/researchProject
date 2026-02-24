package service;

import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import entity.Project;
import entity.Task;
import entity.User;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class FileServiceImpl implements FileService{
    private final String FILE_PATH_PROJECTS = "src/main/resources/projects.xlsx";
    private final String FILE_PATH_TASKS = "src/main/resources/tasks.xlsx";
    private final String FILE_PATH_USERS = "src/main/resources/users.xlsx";

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

            Map<String, Integer> columnNameIndexMap = new HashMap<>();

            // TODO абстрактный файлСервис
            // TODO реализовать switch через Map + вынести в отдельные методы реализацию повторяющейся логики
            for (Cell cell : headerRow) {
                String header = cell.getStringCellValue().trim();
                columnNameIndexMap.put(header, cell.getColumnIndex());
            }

            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row == null) continue;

                userService.registerUser(
                        getCellValueAccordingType(row.getCell(columnNameIndexMap.get("id"))),
                        getCellValueAccordingType(row.getCell(columnNameIndexMap.get("login"))),
                        getCellValueAccordingType(row.getCell(columnNameIndexMap.get("passwordHash"))),
                        getCellValueAccordingType(row.getCell(columnNameIndexMap.get("roleType")))
                );
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void readProjectsToMap(String filePath) {
        try (FileInputStream fis = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheetAt(0); // первый лист

            Row headerRow = sheet.getRow(0);
            if (headerRow == null) {
                throw new RuntimeException("Файл не содержит заголовков");
            }

            Map<String, Integer> columnNameIndexMap = new HashMap<>();
            for (Cell cell : headerRow) {
                String header = cell.getStringCellValue().trim();
                columnNameIndexMap.put(header, cell.getColumnIndex());
            }

            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row == null) continue;

                String projectId = getCellValueAccordingType(row.getCell(columnNameIndexMap.get("id")));
                String userId = getCellValueAccordingType(row.getCell(columnNameIndexMap.get("userId")));

                projectService.create(
                        projectId,
                        getCellValueAccordingType(row.getCell(columnNameIndexMap.get("name"))),
                        getCellValueAccordingType(row.getCell(columnNameIndexMap.get("description"))),
                        getCellValueAccordingType(row.getCell(columnNameIndexMap.get("dateStart"))),
                        getCellValueAccordingType(row.getCell(columnNameIndexMap.get("dateFinish"))),
                        userId
                );

                Project project = projectService.findById(projectId);
                User user = userService.findById(userId);
                user.getProjects().add(project);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void readTasksToMap(String filePath) {
        try (FileInputStream fis = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheetAt(0); // первый лист

            Row headerRow = sheet.getRow(0);
            if (headerRow == null) {
                throw new RuntimeException("Файл не содержит заголовков");
            }

            Map<String, Integer> columnNameIndexMap = new HashMap<>();
            for (Cell cell : headerRow) {
                String header = cell.getStringCellValue().trim();
                columnNameIndexMap.put(header, cell.getColumnIndex());
            }

            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row == null) continue;

                String taskId = getCellValueAccordingType(row.getCell(columnNameIndexMap.get("id")));
                String projectId = getCellValueAccordingType(row.getCell(columnNameIndexMap.get("projectId")));

                taskService.create(
                        taskId,
                        getCellValueAccordingType(row.getCell(columnNameIndexMap.get("name"))),
                        getCellValueAccordingType(row.getCell(columnNameIndexMap.get("description"))),
                        getCellValueAccordingType(row.getCell(columnNameIndexMap.get("dateStart"))),
                        getCellValueAccordingType(row.getCell(columnNameIndexMap.get("dateFinish"))),
                        projectId
                );

                Task task = taskService.findById(taskId);
                Project project = projectService.findById(projectId);
                project.getTasks().add(task);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getCellValueAccordingType(Cell cell) {
        if (cell == null) {
            return "";
        }
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
                    Date date = cell.getDateCellValue();
                    return sdf.format(date);
                }
                else {
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
}
