package myProject.voting.service;

import myProject.voting.model.Dish;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

/**
 * Created by Secret_Hero on 14.02.2018.
 */
public interface DishService {


    Dish save(Dish dish, int restId);

    Dish get(int id, int restId);

    int delete(int id, int restId);

    Collection<Dish> getAllByRestaurantAndDate(int restId, LocalDate localDate);

    List<Dish> getAllByDate(LocalDate localDate);


}
