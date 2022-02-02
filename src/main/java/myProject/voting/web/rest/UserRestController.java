package myProject.voting.web.rest;

import myProject.voting.model.User;
import myProject.voting.service.UserService;
import myProject.voting.util.IllegalRequestDataException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;


@RestController
@RequestMapping("/rest/users")
public class UserRestController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserRestController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }


    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/{id}")
    public User get(@PathVariable("id") int id) {
        return userService.get(id);
    }

    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable("id") int id) {

        userService.delete(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public User create(@RequestBody User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userService.save(user);
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, value = "/{id}")
    public void update(@RequestBody User user, @PathVariable("id") int id) {

        if (user.getId() != id) {
            throw new IllegalRequestDataException("User must be with id=" + id);
        }
        userService.save(user);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Collection<User> getAll() {
        return userService.getAll();
    }

    @GetMapping(value = "/email/{email}", produces = MediaType.APPLICATION_JSON_VALUE)
    public User getByEmail(@PathVariable String email) {
        return userService.getByEmail(email);
    }

}
