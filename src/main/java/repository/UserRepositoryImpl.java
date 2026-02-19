package repository;

import entity.RoleType;
import entity.Session;
import entity.User;

import java.util.HashMap;
import java.util.Objects;

public class UserRepositoryImpl implements UserRepository{
    // Map<login, user>
    private HashMap<String, User> userMap;

    public UserRepositoryImpl(){
        this.userMap = new HashMap<>();
    }

    @Override
    public void authorizeUser(String login, String passwordHash, Session session){
        User user = userMap.get(login);
        if(user == null){
            throw new RuntimeException("Пользователь с таким логином не найден");
        }

        if(Objects.equals(user.getPasswordHash(), passwordHash)){
            session.setCurrentUser(userMap.get(login));
            System.out.println("Пользователь авторизован");
        }
        else{
            System.out.println("Неверный пароль");
        }
    }

    @Override
    public void registerUser(int userId, String login, String passwordHash, RoleType role){
        if(!userMap.containsKey(login)){
            User user = new User(userId, login, passwordHash, role);
            userMap.put(login, user);
            System.out.println("Пользователь успешно зарегистрирован");
        }
        else{
            System.out.println("Такой логин уже занят. Попробуйте другой");
        }
    }

    @Override
    public void terminateSession(Session session){
        session.setCurrentUser(null);
        System.out.println("Сессия закрыта");
    }

    @Override
    public void updatePassword(String oldPasswordHash, String newPasswordHash, Session session){
        User user = session.getCurrentUser();
        if(user.getPasswordHash().equals(oldPasswordHash)) {
            user.setPasswordHash(newPasswordHash);
            System.out.println("Пароль успешно изменён");
        }
        else System.out.println("Пароль введён неверно. Операция отменена");
    }

    @Override
    public void printCurrentProfileInfo(Session session){
        System.out.println(session.getCurrentUser().toString());
    }

    @Override
    public int getRepositorySize(){
        return userMap.size();
    }
}
