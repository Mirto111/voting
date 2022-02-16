package ru.projects.voting.service;

import java.time.LocalDate;
import java.util.Collection;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.projects.voting.model.Restaurant;
import ru.projects.voting.repository.datajpa.CrudRestaurantRepository;
import ru.projects.voting.util.NotFoundException;

@Service
public class RestaurantService{

    private static final Sort SORT_NAME = Sort.by("name");

    private final CrudRestaurantRepository crudRestaurantRepository;

    public RestaurantService(CrudRestaurantRepository crudRestaurantRepository) {
        this.crudRestaurantRepository = crudRestaurantRepository;
    }

    public Restaurant save(Restaurant restaurant) {
        return crudRestaurantRepository.save(restaurant);
    }

    public void delete(int restId) {
        get(restId);
        crudRestaurantRepository.delete(restId);
    }

    public Restaurant get(int restId) {
        return crudRestaurantRepository.findById(restId).orElseThrow(() -> new NotFoundException("Restaurant with id=" + restId + " not found"));
    }

    public Collection<Restaurant> getAll() {
        return crudRestaurantRepository.findAll(SORT_NAME);
    }

    public Collection<Restaurant>getAllByDate(LocalDate date) {
        return crudRestaurantRepository.getAllByDate(date);
    }

    public Restaurant getWithDishByDate(int restId, LocalDate date) {
        return crudRestaurantRepository.getRestaurantByIdAndDate(restId, date);
    }



}
