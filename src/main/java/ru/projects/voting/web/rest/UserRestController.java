package ru.projects.voting.web.rest;

import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.projects.voting.model.User;
import ru.projects.voting.service.UserService;

/**
 * Контроллер для работы с пользователями.
 */
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

  /**
   * Получение пользователя по Id.
   *
   * @param id id пользователя
   * @return возвращает пользователя
   */
  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/{id}")
  public User get(@PathVariable("id") int id) {
    return userService.get(id);
  }

  /**
   * Удаление пользователя по Id.
   *
   * @param id id пользователя
   */
  @DeleteMapping(value = "/{id}")
  public void delete(@PathVariable("id") int id) {
    userService.delete(id);
  }

  /**
   * Создание пользователя.
   *
   * @param user пользователь
   * @return возвращает сохраненного пользователя
   */
  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public User create(@RequestBody User user) {
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    return userService.create(user);
  }

  /**
   * Обновление данных пользователя.
   *
   * @param user пользователь
   * @param id   идентификатор пользователя
   */
  @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, value = "/{id}")
  public void update(@RequestBody User user, @PathVariable("id") int id) {
    userService.update(user, id);
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
