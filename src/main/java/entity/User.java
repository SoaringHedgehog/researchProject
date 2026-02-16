package entity;

import lombok.*;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class User {
    private int id;
    private String login;
    private String passwordHash;
    private RoleType roleType;

    @Override
    public String toString(){
        return "id: " + id + "\n\tЛогин: " + login + "\n\tХэш пароля: " + passwordHash + "\n\tРоль: " + roleType.displayName();
    }
}