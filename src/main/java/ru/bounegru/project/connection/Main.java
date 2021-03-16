package ru.bounegru.project.connection;

import ru.bounegru.project.dao.UserDAO;
import ru.bounegru.project.dao.UserDAOImpl;
import ru.bounegru.project.entity.User;
import ru.bounegru.project.service.UserServiceImpl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        try (ConnectToDB connectToDB = new ConnectToDB()) {
            Connection connection = connectToDB.getConnection();
            UserDAO dao = new UserDAOImpl(connection);
            UserServiceImpl service = new UserServiceImpl(dao);

            System.out.println(service.getUsersSorted());
            User user = service.getUserByLogin("zzzzz");
            System.out.println(user);

            Map<String, String> mm = service.getPhonebook();
            System.out.println(mm);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
