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

/**
 * Сервис для работы с пользователями.
 */

@Service("userService")
public class UserService implements UserDetailsService {

  private static final Sort SORT_NAME_EMAIL = Sort.by("name", "email");

  private final CrudUserRepository crudRepository;

  public UserService(CrudUserRepository crudRepository) {
    this.crudRepository = crudRepository;
  }

  /**
   * Создание нового пользователя.
   *
   * @param user пользователь
   * @return возвращает сохраненного пользователя
   * @throws IllegalRequestDataException если пользователь не новый
   */
  public User create(User user) {
    if (!user.isNew()) {
      throw new IllegalRequestDataException("User must be new (id=null)");
    }
    return crudRepository.save(user);
  }

  /**
   * Обновление пользователя.
   *
   * @param user пользователь
   * @param id   id пользователя
   * @return возвращает сохраненного пользователя
   * @throws IllegalRequestDataException если Id пользователя не совпадают
   */
  public User update(User user, int id) {
    if (user.isNew() || user.getId() != id) {
      throw new IllegalRequestDataException("User must be with id=" + id);
    }
    return crudRepository.save(user);
  }

  /**
   * Удаление пользователя по Id.
   *
   * @param id id пользователя
   * @throws IllegalRequestDataException если пользователь не найден
   */
  public void delete(int id) {
    if (crudRepository.delete(id) == 0) {
      throw new IllegalRequestDataException("Entity with id=" + id + " not found");
    }
  }

  /**
   * Получение пользователя по Id.
   *
   * @param id id пользователя
   * @return возвращает пользователя
   * @throws NotFoundException если пользователь не найден
   */
  public User get(int id) {
    return crudRepository.findById(id)
        .orElseThrow(() -> new NotFoundException("User with id=" + id + " not found"));
  }

  /**
   * Получение всех пользователей.
   *
   * @return возвращает пользователей отсортированных сначала по имени, а потом по емейлу
   */
  public Collection<User> getAll() {
    return crudRepository.findAll(SORT_NAME_EMAIL);
  }

  /**
   * Получение пользователя по емейлу.
   *
   * @param email почта пользователя
   * @return возвращает пользователя
   * @throws NotFoundException если пользователь не найден
   */
  public User getByEmail(String email) {
    return Optional.ofNullable(crudRepository.getByEmail(email))
        .orElseThrow(() -> new NotFoundException("User with email=" + email + " not found"));
  }

  /**
   * Метод необходимый для работы c авторизацией(Spring Security)
   *
   * @param email почта пользователя
   * @return возвращает авторизованного пользователя
   * @throws UsernameNotFoundException если пользователь не найден
   */
  @Override
  public AuthorizedUser loadUserByUsername(String email) throws UsernameNotFoundException {
    User u = crudRepository.getByEmail(email.toLowerCase());
    if (u == null) {
      throw new UsernameNotFoundException("User " + email + " is not found");
    }
    return new AuthorizedUser(u);
  }
}
