package myProject.voting.service;

import myProject.voting.model.Dish;

import java.time.LocalDate;
import java.util.Collection;

public interface DishService {

    Dish save(Dish dish, int restId);

    Dish get(int id, int restId);

    int delete(int id, int restId);

    Collection<Dish> getAllByRestaurantAndDate(int restId, LocalDate localDate);

    Collection<Dish> getAllByDate(LocalDate localDate);
}
