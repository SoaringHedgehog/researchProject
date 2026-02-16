package repository;

import entity.RoleType;
import entity.Session;
import entity.User;

public interface UserRepository {
    void authorizeUser(String login, String passwordHash, Session session);
    void registerUser(int userId, String login, String passwordHash, RoleType role);
    void terminateSession(Session session);
    void updatePassword(String oldPasswordHash, String newPasswordHash, Session session);
    void printCurrentProfileInfo(Session session);
    int getRepositorySize();
}
