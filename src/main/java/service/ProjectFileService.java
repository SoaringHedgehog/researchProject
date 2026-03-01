package service;

import entity.Project;
import entity.User;
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

public class ProjectFileService extends AbstractFileService{
    private final String FILE_PATH_PROJECTS = "src/main/resources/projects.xlsx";
    ProjectService projectService;
    UserService userService;
    Sheet sheet;
    Map<String, Integer> columnNameIndexMap;

    public ProjectFileService(ProjectService projectService, UserService userService){
        this.projectService = projectService;
        this.userService = userService;
        this.columnNameIndexMap = new HashMap<>();
    }

    @Override
    void readColumnNames(){
        try (FileInputStream fis = new FileInputStream(FILE_PATH_PROJECTS);
             Workbook workbook = new XSSFWorkbook(fis)) {

            sheet = workbook.getSheetAt(0); // первый лист

            Row headerRow = sheet.getRow(0);
            if (headerRow == null) {
                throw new RuntimeException("Файл не содержит заголовков");
            }

            columnNameIndexMap = new HashMap<>();

            columnNameIndexMap = new HashMap<>();
            Field[] fields = Project.class.getDeclaredFields();
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
    }
}
