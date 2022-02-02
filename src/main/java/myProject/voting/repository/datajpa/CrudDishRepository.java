package myProject.voting.repository.datajpa;

import myProject.voting.model.Dish;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Transactional(readOnly = true)
public interface CrudDishRepository extends JpaRepository<Dish, Integer> {

    @Transactional
    @Override
    Dish save(Dish dish);

    Dish getByIdAndRestaurantId(int id, int restId);

    @Modifying
    @Transactional
    @Query("DELETE FROM Dish d where d.id=:id AND d.restaurant.id=:restId")
    int delete(@Param("id") int id, @Param("restId") int restId);

    List<Dish> getAllByRestaurantIdAndCurrentDate(int restId, LocalDate localDate);

    List<Dish> getAllByCurrentDate(LocalDate localDate);

}
