package ru.bounegru.project.dao;

import ru.bounegru.project.entity.User;
import ru.bounegru.project.executor.Executor;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;


// TODO use Hibernate!!!
public class UserDAOImpl implements UserDAO {

    private final Executor executor;

    public UserDAOImpl(Connection connection) {
        executor = new Executor(connection);
    }


    // TODO protection from sql injection
    @Override
    public User getUserByLogin(String login) {
        String query = "SELECT * FROM users WHERE login = '" + login + "'";
        return executor.execQuery(query, result -> {
            if (!result.next()) {
                return null;
            }
            return new User(
                    result.getInt(1),
                    result.getString(2),
                    result.getString(3),
                    result.getString(4),
                    result.getInt(5),
                    result.getString(6)
            );
        });
    }

    @Override
    public List<User> getUsers() {
        String query = "SELECT * FROM users";
        return executor.execQuery(query, result -> {
            List<User> users = new ArrayList<>();
            while (result.next()) {
                users.add(new User(
                        result.getInt(1),
                        result.getString(2),
                        result.getString(3),
                        result.getString(4),
                        result.getInt(5),
                        result.getString(6)
                ));
            }
            return users;
        });
    }


    @Override
    public void updateUser(User user) {
        StringBuilder query = new StringBuilder();
        query.append("UPDATE users SET ")
                .append("login = '").append(user.getLogin())
                .append("', first_name = '").append(user.getFirstName())
                .append("', last_name = '").append(user.getLastName())
                .append("', age = ").append(user.getAge())
                .append(", phone_number = '").append(user.getPhoneNumber())
                .append("' WHERE id = ").append(user.getId());
        executor.execUpdate(query.toString());
    }

}
