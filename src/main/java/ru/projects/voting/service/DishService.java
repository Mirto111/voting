package ru.projects.voting.service;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.projects.voting.model.Dish;
import ru.projects.voting.repository.datajpa.CrudDishRepository;
import ru.projects.voting.util.IllegalRequestDataException;
import ru.projects.voting.util.NotFoundException;

@Service
public class DishService {

    private final CrudDishRepository crudDishRepository;
    private final RestaurantService crudRestaurantRepository;

    public DishService(CrudDishRepository crudDishRepository, RestaurantService crudRestaurantRepository) {
        this.crudDishRepository = crudDishRepository;
        this.crudRestaurantRepository = crudRestaurantRepository;
    }

    @Transactional
    public Dish save(Dish dish, int restId) {
        if (!dish.isNew() && get(dish.getId(), restId) == null) {
            return null;
        }
        dish.setRestaurant(crudRestaurantRepository.get(restId));
        return crudDishRepository.save(dish);
    }


    public Dish get(int id, int restId) {
        return Optional.ofNullable(crudDishRepository.getByIdAndRestaurantId(id, restId)).orElseThrow(() -> new NotFoundException("Dish with id=" + id + " not found"));
    }


    public void delete(int id, int restId) {
        get(id, restId);
        if (crudDishRepository.delete(id, restId)  == 0) {
            throw new IllegalRequestDataException("Entity with id=" + id + " not found");
        }
    }


    public Collection<Dish> getAllByRestaurantAndDate(int restId, LocalDate localDate) {
        LocalDate date = localDate != null ? localDate : LocalDate.now();
        return crudDishRepository.getAllByRestaurantIdAndCurrentDate(restId, date);
    }

    public Collection<Dish> getAllByRestaurant(int restId) {
        return crudDishRepository.getAllByRestaurantId(restId);
    }

}
