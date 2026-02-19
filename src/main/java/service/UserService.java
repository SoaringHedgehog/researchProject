package service;

import entity.RoleType;
import entity.Session;
import entity.User;
import repository.UserRepository;
import repository.UserRepositoryImpl;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class UserService {
    private final UserRepository userRepository;
    Session session;

    public UserService(){
        this.userRepository = new UserRepositoryImpl();
    }

    public void authorizeUser(String login, String password, Session session) {
        if(session.getCurrentUser() != null){
            throw new RuntimeException("Вход уже выполнен");
        }

        if(login == null || login.isEmpty()){
            throw new RuntimeException("Логин не может быть пустым");
        }

        if(password == null || password.isEmpty()){
            throw new RuntimeException("Пароль не может быть пустым");
        }

        try{
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] inputBytes = password.getBytes("UTF-8");
            md5.update(inputBytes);
            byte[] hashBytes = md5.digest();
            // Преобразование массива байтов в шестнадцатеричную строку
            StringBuilder checkedPassword = new StringBuilder();
            for (byte b : hashBytes) {
                // Преобразование каждого байта в две шестнадцатеричные цифры
                checkedPassword.append(String.format("%02x", b & 0xff));
            }

            userRepository.authorizeUser(login, checkedPassword.toString(), session);
        }
        catch(NoSuchAlgorithmException | UnsupportedEncodingException e) {
            throw new RuntimeException("Ошибка при генерации MD5 хеша", e);
        }
    }

    public void registerUser(String id, String login, String password, RoleType role) {
        int idChecked;
        if(id == null) idChecked = userRepository.getRepositorySize();
        else idChecked = Integer.parseInt(id);

        if(login == null || login.isEmpty()){
            throw new RuntimeException("Логин не может быть пустым");
        }

        if(password == null || password.isEmpty()){
            throw new RuntimeException("Пароль не может быть пустым");
        }

        try{
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] inputBytes = password.getBytes("UTF-8");
            md5.update(inputBytes);
            byte[] hashBytes = md5.digest();
            // Преобразование массива байтов в шестнадцатеричную строку
            StringBuilder checkedPassword = new StringBuilder();
            for (byte b : hashBytes) {
                // Преобразование каждого байта в две шестнадцатеричные цифры
                checkedPassword.append(String.format("%02x", b & 0xff));
            }

            userRepository.registerUser(idChecked, login, checkedPassword.toString(), role);
        }
        catch(NoSuchAlgorithmException | UnsupportedEncodingException e) {
            throw new RuntimeException("Ошибка при генерации MD5 хеша", e);
        }
    }

    public void terminateSession(Session session){
        if(session.getCurrentUser() == null){
            throw new RuntimeException("Текущий пользователь отсутствует");
        }
        userRepository.terminateSession(session);
    }

    public void updatePassword(String oldPassword, String newPassword, String repeatNewPassword, Session session){
        if(oldPassword == null || oldPassword.isEmpty()){
            throw new RuntimeException("Старый пароль не может быть пустым");
        }

        if(newPassword == null || newPassword.isEmpty()){
            throw new RuntimeException("Нвоый пароль не может быть пустым");
        }
        
        if(!newPassword.equals(repeatNewPassword)){
            throw new RuntimeException("Пароль не совпадают. Операция отменена");
        }

        try{
            MessageDigest md5_1 = MessageDigest.getInstance("MD5");
            byte[] inputBytes = oldPassword.getBytes("UTF-8");
            md5_1.update(inputBytes);
            byte[] hashBytes = md5_1.digest();
            // Преобразование массива байтов в шестнадцатеричную строку
            StringBuilder checkedOldPassword = new StringBuilder();
            for (byte b : hashBytes) {
                // Преобразование каждого байта в две шестнадцатеричные цифры
                checkedOldPassword.append(String.format("%02x", b & 0xff));
            }

            MessageDigest md5_2 = MessageDigest.getInstance("MD5");
            inputBytes = oldPassword.getBytes("UTF-8");
            md5_2.update(inputBytes);
            hashBytes = md5_2.digest();
            // Преобразование массива байтов в шестнадцатеричную строку
            StringBuilder checkedNewPassword = new StringBuilder();
            for (byte b : hashBytes) {
                // Преобразование каждого байта в две шестнадцатеричные цифры
                checkedNewPassword.append(String.format("%02x", b & 0xff));
            }

            userRepository.updatePassword(checkedOldPassword.toString(), checkedNewPassword.toString(), session);
        }
        catch(NoSuchAlgorithmException | UnsupportedEncodingException e) {
            throw new RuntimeException("Ошибка при генерации MD5 хеша", e);
        }
    }

    public User findById(String userId){
        int userIdChecked;
        if(userId == null || userId.isEmpty()){
            throw new RuntimeException("Id проекта не может быть пустым");
        }
        else{
            userIdChecked = Integer.parseInt(userId);
        }
        return userRepository.findById(userIdChecked);
    }

    public void printCurrentProfileInfo(Session session){
        userRepository.printCurrentProfileInfo(session);
    }
}
