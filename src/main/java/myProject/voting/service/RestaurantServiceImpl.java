package myProject.voting.service;

import myProject.voting.AuthorizedUser;
import myProject.voting.model.Restaurant;
import myProject.voting.model.Vote;
import myProject.voting.repository.datajpa.CrudRestaurantRepository;
import myProject.voting.util.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class RestaurantServiceImpl implements RestaurantService {

    private static final Sort SORT_NAME = Sort.by("name");

    @Autowired
    CrudRestaurantRepository crudRestaurantRepository;

    @Override
    public Restaurant save(Restaurant restaurant) {
        return crudRestaurantRepository.save(restaurant);
    }

    @Override
    public int delete(int restId) {
        return crudRestaurantRepository.delete(restId);
    }

    @Override
    public Restaurant get(int restId) {
        return crudRestaurantRepository.findById(restId).orElseThrow(NotFoundException::new);
    }

    @Override
    public Collection<Restaurant> getAll() {
        return crudRestaurantRepository.findAll(SORT_NAME);
    }


}
