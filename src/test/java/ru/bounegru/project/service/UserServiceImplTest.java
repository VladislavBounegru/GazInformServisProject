package ru.bounegru.project.service;

import org.junit.jupiter.api.*;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import ru.bounegru.project.dao.UserDAO;
import ru.bounegru.project.entity.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;


// come up with normal names!!
@RunWith(MockitoJUnitRunner.class)
class UserServiceImplTest {

    @Mock
    private UserDAO dao;

    private UserService userService;

    private AutoCloseable closeable;

    @BeforeEach
    public void openMocks() {
        closeable = MockitoAnnotations.openMocks(this);
        userService = new UserServiceImpl(dao);
    }

    @AfterEach
    public void releaseMocks() throws Exception {
        closeable.close();
    }


    @Test
    void getUsersList_Should_Be_Equal() {
        List<User> users = new ArrayList<User>() {{
            add(new User(1, "existingLogin", "name", "last name", 23, "8800-555-35-35"));
            add(new User(2, "existingLogin2", "name2", "last name2", 12, "8800-555-35-35"));
            add(new User(3, "existingLogin3", "name3", "last name3", 61, "8800-555-35-35"));
        }};
        given(dao.getUsers()).willReturn(
                users);
        assertThat(userService.getUsersList().size()).isEqualTo(3);
    }


    @Test
    void getUserByLogin_Should_Be_Equal() {
        given(dao.getUserByLogin("existingLogin")).willReturn(
                new User(1, "existingLogin", "name", "last name", 23, "8800-555-35-35")
        );
        User user = userService.getUserByLogin("existingLogin");
        System.out.println(user);
        assertThat(user.getLogin()).isEqualTo("existingLogin");
    }

    @Test
    void changeUserLastName_Should_Return_Updated_User() {
        User user = new User(1, "login", "name", "last name", 23, "8125550550");
        String newLastName = "new Last name";
        User updatedUser = new User(1, "login", "name", newLastName, 23, "8125550550");
        userService.changeUserLastName(user, newLastName);
        verify(dao).updateUser(updatedUser);
    }

    @Test
    void getUsersSorted_Should_Be_Sorted() {
        List<User> users = new ArrayList<User>() {{
            add(new User(1, "existingLogin", "name", "Z lastname", 23, "8800-555-35-35"));
            add(new User(2, "existingLogin2", "name2", "A last name", 12, "8800-555-35-35"));
            add(new User(3, "existingLogin3", "name3", "B last name3", 61, "8800-555-35-35"));
        }};
        given(dao.getUsers()).willReturn(
                users);
        userService.getUsersSorted();
        assertThat(users.get(0).getId()).isEqualTo(2);
        assertThat(users.get(1).getId()).isEqualTo(3);
        assertThat(users.get(2).getId()).isEqualTo(1);
    }

    @Test
    void getPhonebook_Contain() {
        List<User> users = new ArrayList<User>() {{
            add(new User(1, "existingLogin", "name", "first", 23, "1"));
            add(new User(2, "existingLogin2", "name2", "second", 12, "2"));
            add(new User(3, "existingLogin3", "name3", "third", 61, "3"));
        }};
        given(dao.getUsers()).willReturn(
                users);
        Map<String, String> map = userService.getPhonebook();
        assertThat((map.containsKey("first") && map.get("first").equals("1"))).isTrue();
        assertThat((map.containsKey("second") && map.get("second").equals("2"))).isTrue();
        assertThat((map.containsKey("third") && map.get("third").equals("3"))).isTrue();
    }
}