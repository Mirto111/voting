package myProject.voting.service;

import myProject.voting.model.Dish;
import myProject.voting.repository.datajpa.CrudDishRepository;
import myProject.voting.util.NotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Optional;

@Service
public class DishServiceImpl implements DishService {

    private final CrudDishRepository crudDishRepository;/**/
    private final RestaurantService crudRestaurantRepository;

    public DishServiceImpl(CrudDishRepository crudDishRepository, RestaurantService crudRestaurantRepository) {
        this.crudDishRepository = crudDishRepository;
        this.crudRestaurantRepository = crudRestaurantRepository;
    }

    @Override
    public Dish save(Dish dish, int restId) {
        if (!dish.isNew() && get(dish.getId(), restId) == null) {
            return null;
        }
        dish.setRestaurant(crudRestaurantRepository.get(restId));
        return crudDishRepository.save(dish);
    }

    @Override
    public Dish get(int id, int restId) {
        return Optional.ofNullable(crudDishRepository.getByIdAndRestaurantId(id, restId)).orElseThrow(() -> new NotFoundException("Dish with id=" + id + " not found"));
    }

    @Override
    public int delete(int id, int restId) {
        get(id, restId);
        return crudDishRepository.delete(id, restId);
    }

    @Override
    public Collection<Dish> getAllByRestaurantAndDate(int restId, LocalDate localDate) {
        LocalDate date = localDate != null ? localDate : LocalDate.now();
        return crudDishRepository.getAllByRestaurantIdAndCurrentDate(restId, date);
    }

    @Override
    public Collection<Dish> getAllByDate(LocalDate localDate) {
        LocalDate date = localDate != null ? localDate : LocalDate.now();
        return crudDishRepository.getAllByCurrentDate(date);
    }

}
