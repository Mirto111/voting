package myProject.voting.service;

import myProject.voting.model.Restaurant;
import myProject.voting.model.Vote;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;

/**
 * Created by Secret_Hero on 14.02.2018.
 */
public interface RestaurantService {

    Restaurant save(Restaurant restaurant);

    int delete(int restId);

    Restaurant get(int restId);

    Collection<Restaurant> getAll();

}
