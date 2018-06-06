package myProject.voting.repository.datajpa;

import myProject.voting.model.Dish;
import myProject.voting.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@Transactional(readOnly = true)
public interface CrudDishRepository extends JpaRepository<Dish, Integer> {

    @Transactional
    @Override
    Dish save(Dish dish);

    @Query("SELECT d FROM Dish d WHERE d.id=:id AND d.restaurant.id=:restId")
    Dish get(@Param("id")int id, @Param("restId") int restId);

    @Modifying
    @Transactional
    @Query("DELETE FROM Dish d where d.id=:id AND d.restaurant.id=:restId")
    int delete(@Param("id")int id, @Param("restId") int restId);

    @Query("SELECT d FROM Dish d WHERE d.restaurant.id=:restId AND d.currentDate=:localDate")
    List<Dish> getAllForDay(@Param("restId") int restId, @Param("localDate") LocalDate localDate);



}
