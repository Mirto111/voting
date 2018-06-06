package myProject.voting.service;

import myProject.voting.model.User;

import java.util.Collection;

/**
 * Created by Secret_Hero on 29.03.2018.
 */
public interface UserService {

    User save(User user);

    boolean delete(int id);

    User get(int id);

    User getByEmail(String email);

    Collection<User> getAll();

}
