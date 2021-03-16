package ru.bounegru.project.dao;

import org.junit.jupiter.api.*;
import ru.bounegru.project.connection.ConnectToDB;
import ru.bounegru.project.entity.User;

import java.sql.SQLException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class UserDAOTest {

    private static ConnectToDB connectToDB;
    private UserDAO dao;

    @BeforeAll
    public static void openConnection() throws SQLException {
        // conn to test DB
        connectToDB = new ConnectToDB();
    }

    @AfterAll
    public static void closeConnection() throws SQLException {
        connectToDB.getConnection().close();
    }

    @BeforeEach
    void setUp() {
        dao = new UserDAOImpl(connectToDB.getConnection());
    }

    @Test
    void getUserByLogin_Shouldnt_Be_Null() {
        String login = "exists";
        assertThat(dao.getUserByLogin(login)).isNotNull();
    }

    @Test
    void getUserByLogin_Should_Be_Null() {
        String login = "thislogindoesntexist";
        assertThat(dao.getUserByLogin(login)).isNull();
    }


    @Test
    void getUsers_Size_Should_Be_Greater_Than_Zero() {
        List<User> users = dao.getUsers();
        // because we know that table isn't empty
        assertThat(users.size()).isGreaterThan(0);
    }

    @Test
    void updateUser_Age_Should_Be_Equals() {
        User user = dao.getUserByLogin("exists");
        user.setAge((user.getAge() + 30) % 95);
        dao.updateUser(user);
        User updatedUser = dao.getUserByLogin("exists");
        assertThat(updatedUser.getAge()).isEqualTo(user.getAge());
    }
}