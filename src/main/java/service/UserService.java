package service;

import entity.RoleType;
import entity.Session;
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
            System.out.println("Вход уже выполнен");
            return;
        }

        if(login == null || login.isEmpty()){
            System.out.println("Логин не может быть пустым");
            return;
        }

        if(password == null || password.isEmpty()){
            System.out.println("Пароль не может быть пустым");
            return;
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
            System.out.println("Логин не может быть пустым");
            return;
        }

        if(password == null || password.isEmpty()){
            System.out.println("Пароль не может быть пустым");
            return;
        }

        try{
            RoleType.valueOf(role.toString());
        }
        catch (IllegalArgumentException e){
            System.out.println("Такой роли не существует");
            return;
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
            System.out.println("Пользователь и так не в сети");
        }
        userRepository.terminateSession(session);
    }

    public void updatePassword(String oldPassword, String newPassword, String repeatNewPassword, Session session){
        if(oldPassword == null || oldPassword.isEmpty()){
            System.out.println("Старый пароль не может быть пустым");
            return;
        }

        if(newPassword == null || newPassword.isEmpty()){
            System.out.println("Нвоый пароль не может быть пустым");
            return;
        }

        if(!newPassword.equals(repeatNewPassword)){
            System.out.println("Пароль не совпадают. Операция отменена");
            return;
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

    public void printCurrentProfileInfo(Session session){
        userRepository.printCurrentProfileInfo(session);
    }
}
