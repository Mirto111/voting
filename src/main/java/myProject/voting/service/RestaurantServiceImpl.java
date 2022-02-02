package myProject.voting.service;

import myProject.voting.model.Restaurant;
import myProject.voting.repository.datajpa.CrudRestaurantRepository;
import myProject.voting.util.NotFoundException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class RestaurantServiceImpl implements RestaurantService {

    private static final Sort SORT_NAME = Sort.by("name");

    private final CrudRestaurantRepository crudRestaurantRepository;

    public RestaurantServiceImpl(CrudRestaurantRepository crudRestaurantRepository) {
        this.crudRestaurantRepository = crudRestaurantRepository;
    }

    @Override
    public Restaurant save(Restaurant restaurant) {

        return crudRestaurantRepository.save(restaurant);
    }

    @Override
    public void delete(int restId) {
        get(restId);
        crudRestaurantRepository.delete(restId);
    }

    @Override
    public Restaurant get(int restId) {
        return crudRestaurantRepository.findById(restId).orElseThrow(() -> new NotFoundException("Restaurant with id=" + restId + " not found"));
    }

    @Override
    public Collection<Restaurant> getAll() {
        return crudRestaurantRepository.findAll(SORT_NAME);
    }

}
