package ru.projects.voting.repository.datajpa;

import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.projects.voting.model.Restaurant;

/**
 * Репозиторий для сохранения и получения сущностей ресторана.
 */

@Transactional(readOnly = true)
public interface CrudRestaurantRepository extends JpaRepository<Restaurant, Integer> {

  @Transactional
  @Modifying
  @Query("DELETE FROM Restaurant r WHERE r.id=:id")
  int delete(@Param("id") Integer id);

  @Query("SELECT DISTINCT r FROM Restaurant r JOIN FETCH r.dishes d WHERE d.currentDate=:date")
  List<Restaurant> getRestaurantsWithDishesByDate(LocalDate date);

  @Query("SELECT r FROM Restaurant r JOIN FETCH r.dishes d WHERE d.currentDate=:date AND r.id=:restId")
  Restaurant getRestaurantByIdAndDate(int restId, LocalDate date);
}
