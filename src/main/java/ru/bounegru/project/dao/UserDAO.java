package ru.bounegru.project.dao;

import ru.bounegru.project.entity.User;

import java.util.List;

public interface UserDAO {

    /* not needed for this task
    public void saveUser(User user);
    public User getUser(int id);
    public void deleteUser(int id);
    */

    public User getUserByLogin(String login);

    public List<User> getUsers();

    public void updateUser(User user);

}
