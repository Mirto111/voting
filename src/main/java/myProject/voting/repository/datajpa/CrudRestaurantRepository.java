package myProject.voting.repository.datajpa;

import java.time.LocalDate;
import java.util.List;
import myProject.voting.model.Restaurant;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;


@Transactional(readOnly = true)
public interface CrudRestaurantRepository extends JpaRepository<Restaurant, Integer> {

    @Override
    @Transactional
    Restaurant save(Restaurant restaurant);

    @Transactional
    @Modifying
    @Query("DELETE FROM Restaurant r WHERE r.id=:id")
    int delete(@Param("id") Integer id);

    @Override
    List<Restaurant> findAll(Sort sort);

    @Query("SELECT DISTINCT r FROM Restaurant r JOIN FETCH r.dishes d WHERE d.currentDate=:date")
    List<Restaurant> getAllByDate(LocalDate date);

    @Query("SELECT r FROM Restaurant r JOIN FETCH r.dishes d WHERE d.currentDate=:date AND r.id=:restId")
    Restaurant getRestaurantByIdAndDate(int restId, LocalDate date);
}
