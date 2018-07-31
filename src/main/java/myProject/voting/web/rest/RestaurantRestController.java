package myProject.voting.web.rest;

import myProject.voting.AuthorizedUser;
import myProject.voting.model.Restaurant;
import myProject.voting.model.Vote;
import myProject.voting.service.RestaurantService;
import myProject.voting.service.VoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/rest/restaurants")
public class RestaurantRestController {

    @Autowired
    RestaurantService restaurantService;

    @Autowired
    VoteService voteService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/{id}")
    public Restaurant get(@PathVariable("id") int id) {
        return restaurantService.get(id);
    }

    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable("id") int id) {
        restaurantService.delete(id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Restaurant create(@RequestBody Restaurant restaurant) {
        return restaurantService.save(restaurant);
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, value = "/{id}")
    public void update(@RequestBody Restaurant restaurant, @PathVariable("id") int id) {
        restaurantService.save(restaurant);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Collection<Restaurant> getAll() {
        return restaurantService.getAll();
    }

    @PostMapping(value = "/vote")
    public void vote(@RequestBody Restaurant restaurant) {

        voteService.vote(AuthorizedUser.id(), restaurant.getName());

    }

    @GetMapping(value = "/results", produces = MediaType.APPLICATION_JSON_VALUE)
    public Collection<Vote> getResults() {

        Map<String, Long> results = Vote.getVoteCount().values().stream().collect(Collectors.groupingBy(v -> v, Collectors.counting()));

        return results.entrySet().stream().map(m -> new Vote(m.getKey(), m.getValue().intValue())).collect(Collectors.toList());
    }

    @GetMapping(value = "/saveResults")
    public Collection<Vote> saveResults() {

      return voteService.saveAll(new ArrayList<>(getResults()));

    }
}


