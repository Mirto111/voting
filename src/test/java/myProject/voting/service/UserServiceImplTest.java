package myProject.voting.service;

import datajpa.UserTestData;
import myProject.voting.model.Role;
import myProject.voting.model.User;
import myProject.voting.repository.datajpa.CrudUserRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
public class UserServiceImplTest {

    @Autowired
    UserService userService;

    @Test
    public void save() {
        User user = new User(null, "Test@ya.ru", "test", "test", Role.ROLE_USER, Role.ROLE_ADMIN);
        userService.save(user);

    }


    @Test
    @Transactional
    public void get() {
        User user = userService.get(1);

    }

/*    @Test
    public void getAll() {
    }*/

    @Test
    public void getByEmail() {

        User user = userService.getByEmail("user@yandex.ru");
        Assert.assertEquals(UserTestData.USER, user);
    }

    @Test
    public void delete() {

        Assert.assertTrue(userService.delete(2));
    }
}