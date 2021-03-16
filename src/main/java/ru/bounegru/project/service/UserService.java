package ru.bounegru.project.service;

import ru.bounegru.project.entity.User;

import java.util.List;
import java.util.Map;

public interface UserService {


    List<User> getUsersList();

    User getUserByLogin(String login);

    void changeUserLastName(User user, String lastName);

    List<User> getUsersSorted();

    Map<String, String> getPhonebook();
}
