package service;

import entity.Project;
import entity.Task;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class TaskFileService extends AbstractFileService{
    private final String FILE_PATH_TASKS = "src/main/resources/tasks.xlsx";
    TaskService taskService;
    ProjectService projectService;
    Sheet sheet;
    Map<String, Integer> columnNameIndexMap;

    public TaskFileService(TaskService taskService, ProjectService projectService){
        this.taskService = taskService;
        this.projectService = projectService;
        this.columnNameIndexMap = new HashMap<>();
    }

    @Override
    void readColumnNames(){
        try (FileInputStream fis = new FileInputStream(FILE_PATH_TASKS);
             Workbook workbook = new XSSFWorkbook(fis)) {

            sheet = workbook.getSheetAt(0); // первый лист

            Row headerRow = sheet.getRow(0);
            if (headerRow == null) {
                throw new RuntimeException("Файл не содержит заголовков");
            }

            columnNameIndexMap = new HashMap<>();
            Field[] fields = Task.class.getDeclaredFields();
            for (Cell cell : headerRow) {
                boolean found = false;
                String header = cell.getStringCellValue().trim();
                for(Field field : fields){
                    if (field.getName().equals(header)) {
                        found = true;
                        columnNameIndexMap.put(header, cell.getColumnIndex());
                        break;
                    }
                }
                if(!found) throw new RuntimeException("Поле с названием " + header + " не найдено");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    void createEntity() {
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
    }
}
