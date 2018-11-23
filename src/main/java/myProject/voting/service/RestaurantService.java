package myProject.voting.service;

import myProject.voting.model.Restaurant;

import java.util.Collection;

/**
 * Created by Secret_Hero on 14.02.2018.
 */
public interface RestaurantService {

    Restaurant save(Restaurant restaurant);

    void delete(int restId);

    Restaurant get(int restId);

    Collection<Restaurant> getAll();

}
