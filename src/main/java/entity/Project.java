package entity;

import lombok.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@NoArgsConstructor
@ToString
public class Project {
    private int id; //Потом замена типа на UUID
    private String name;
    private String description;
    private LocalDate dateStart;
    private LocalDate dateFinish;
    private int userId;
    private List<Task> tasks;

    public Project(int id, String name, String description, LocalDate dateStart, LocalDate dateFinish, int userId){
        this.id = id;
        this.name = name;
        this.description = description;
        this.dateStart = dateStart;
        this.dateFinish = dateFinish;
        this.userId = userId;
        this.tasks = new ArrayList<>();
    }

    public String toString(){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        return "Id: " + id + "\n\tНазвание: " + name + "\n\tОписание: " + description
                + "\n\tДата начала: " + dtf.format(dateStart) + "\n\tДата окончания: " + dtf.format(dateFinish)
                + "\n\tid Пользователя: " + userId;
    }
}
