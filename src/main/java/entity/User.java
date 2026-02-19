package entity;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class User {
    private int id;
    private String login;
    private String passwordHash;
    private RoleType roleType;
    private List<Project> projects;

    public User(int id, String login, String passwordHash, RoleType roleType){
        this.id = id;
        this.login = login;
        this.passwordHash = passwordHash;
        this.roleType = roleType;
        this.projects = new ArrayList<>();
    }

    @Override
    public String toString(){
        return "id: " + id + "\n\tЛогин: " + login + "\n\tХэш пароля: " + passwordHash
                + "\n\tРоль: " + roleType.displayName();
    }
}