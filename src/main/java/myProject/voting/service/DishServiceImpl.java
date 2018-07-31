package myProject.voting.service;

import myProject.voting.model.Dish;
import myProject.voting.repository.datajpa.CrudDishRepository;
import myProject.voting.repository.datajpa.CrudRestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@Service
public class DishServiceImpl implements DishService {

    @Autowired
    CrudDishRepository crudDishRepository;

    @Autowired
    CrudRestaurantRepository crudRestaurantRepository;


    @Override
    public Dish save(Dish dish, int restId) {

        if (!dish.isNew() && get(dish.getId(), restId) == null) {
            return null;
        }
        dish.setRestaurant(crudRestaurantRepository.getOne(restId));

        return crudDishRepository.save(dish);
    }

    @Override
    public Dish get(int id, int restId) {

        return crudDishRepository.getByIdAndRestaurantId(id, restId);
    }

    @Override
    public int delete(int id, int restId) {
        return crudDishRepository.delete(id, restId);
    }

    @Override
    public Collection<Dish> getAllByRestaurantAndDate(int restId, LocalDate localDate) {
        return crudDishRepository.getAllByRestaurantIdAndCurrentDate(restId, localDate);
    }

    @Override
    public List<Dish> getAllByDate(LocalDate localDate) {
        return crudDishRepository.getAllByCurrentDate(localDate);
    }

}
