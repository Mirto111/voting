package ru.projects.voting.service;

import java.util.Collection;
import java.util.Optional;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.projects.voting.AuthorizedUser;
import ru.projects.voting.model.User;
import ru.projects.voting.repository.datajpa.CrudUserRepository;
import ru.projects.voting.util.IllegalRequestDataException;
import ru.projects.voting.util.NotFoundException;

@Service("userService")
public class UserService implements UserDetailsService {

    private static final Sort SORT_NAME_EMAIL = Sort.by("name", "email");

    private final CrudUserRepository crudRepository;

    public UserService(CrudUserRepository crudRepository) {
        this.crudRepository = crudRepository;
    }

    public User save(User user) {
        return crudRepository.save(user);
    }

    public void delete(int id) {
        if (crudRepository.delete(id)  == 0) {
            throw new IllegalRequestDataException("Entity with id=" + id + " not found");
        }
    }

    public User get(int id) {
        return crudRepository.findById(id).orElseThrow(() -> new NotFoundException("User with id=" + id + " not found"));
    }

    public Collection<User> getAll() {
        return crudRepository.findAll(SORT_NAME_EMAIL);
    }

    public User getByEmail(String email) {
        return Optional.ofNullable(crudRepository.getByEmail(email)).orElseThrow(() -> new NotFoundException("User with email=" + email + " not found"));
    }

    @Override
    public AuthorizedUser loadUserByUsername(String s) throws UsernameNotFoundException {
        User u = crudRepository.getByEmail(s.toLowerCase());
        if (u == null) {
            throw new UsernameNotFoundException("User " + s + " is not found");
        }
        return new AuthorizedUser(u);
    }
}
