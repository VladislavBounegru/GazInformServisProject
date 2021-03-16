package ru.bounegru.project.service;

import ru.bounegru.project.dao.UserDAO;
import ru.bounegru.project.entity.User;

import java.util.*;
import java.util.stream.Collectors;

public class UserServiceImpl implements UserService {
    private final UserDAO dao;

    public UserServiceImpl(UserDAO dao) {
        this.dao = dao;
    }

    @Override
    public List<User> getUsersList() {
        return dao.getUsers();
    }

    @Override
    public User getUserByLogin(String login) {
        User user = dao.getUserByLogin(login);
        return user;
    }

    // TODO add a check for user existence
    @Override
    public void changeUserLastName(User user, String lastName) {
        user.setLastName(lastName);
        dao.updateUser(user);
    }

    @Override
    public List<User> getUsersSorted() {
        List<User> users = getUsersList();
        users.sort(Comparator.comparing(User::getLastName));
        return users;
    }

    @Override
    public Map<String, String> getPhonebook() {
        return getUsersList().stream()
                .collect(Collectors.toMap(User::getLastName, User::getPhoneNumber));
    }

}
