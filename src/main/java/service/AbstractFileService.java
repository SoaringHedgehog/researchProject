package service;

import org.apache.poi.ss.usermodel.*;

import java.text.SimpleDateFormat;
import java.util.Date;

public abstract class AbstractFileService {
    abstract void readColumnNames(); // TODO в этом методе у наследников разные только названия файлов, но есть поля, кот. исп-я в др методе
    abstract void createEntity();

    void readEntityToMap(){
        readColumnNames();
        createEntity();
    }

    protected String getCellValueAccordingType(Cell cell) {
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
