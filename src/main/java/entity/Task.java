package entity;

import lombok.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Getter @Setter
@NoArgsConstructor
public class Task {
    private int id; //Потом замена на UUID
    private String name;
    private String description;
    private LocalDate dateStart;
    private LocalDate dateFinish;
    private int projectId;

    public Task(int id, String name, String description, LocalDate dateStart, LocalDate dateFinish, int projectId){
        this.id = id;
        this.name = name;
        this.description = description;
        this.dateStart = dateStart;
        this.dateFinish = dateFinish;
        this.projectId = projectId;
    }

    public String toString(){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        return "Id: " + id + "\n\tНазвание: " + name + "\n\tОписание: " + description
                + "\n\tДата начала:\t" + dtf.format(dateStart) + "\n\tДата окончания:\t" + dtf.format(dateFinish)
                + "\n\tId проекта\t" + projectId;
    }
}
