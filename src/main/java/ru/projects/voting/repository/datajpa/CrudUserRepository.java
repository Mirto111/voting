package ru.projects.voting.repository.datajpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.projects.voting.model.User;

/**
 * Репозиторий для сохранения и получения пользователей.
 */

@Transactional(readOnly = true)
public interface CrudUserRepository extends JpaRepository<User, Integer> {

  @Transactional
  @Modifying
  @Query("DELETE FROM User u WHERE u.id=:id")
  int delete(@Param("id") int id);

  User getByEmail(String email);
}
