package myProject.voting.web.rest;

import java.time.LocalDate;
import java.util.Collection;
import myProject.voting.model.Restaurant;
import myProject.voting.service.RestaurantService;
import myProject.voting.util.IllegalRequestDataException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/rest/restaurants")
public class RestaurantRestController {

    private final RestaurantService restaurantService;

    public RestaurantRestController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/{id}")
    public Restaurant get(@PathVariable("id") int id) {
        return restaurantService.get(id);
    }

    @Secured("ROLE_ADMIN")
    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable("id") int id) {
        restaurantService.delete(id);
    }

    @Secured("ROLE_ADMIN")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Restaurant create(@RequestBody Restaurant restaurant) {
        return restaurantService.save(restaurant);
    }

    @Secured("ROLE_ADMIN")
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, value = "/{id}")
    public void update(@RequestBody Restaurant restaurant, @PathVariable("id") int id) {
        if (restaurant.getId() != id) {
            throw new IllegalRequestDataException("Restaurant must be with id=" + id);
        }
        restaurantService.save(restaurant);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Collection<Restaurant> getAll() {
        return restaurantService.getAll();
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/menu")
    public Collection<Restaurant> getMenuAllForToday() {
        return restaurantService.getAllByDate(LocalDate.now());
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/{id}/menu")
    public Restaurant getWithDish(@PathVariable("id")int restId) {
        return restaurantService.getWithDishByDate(restId, LocalDate.now());
    }

}


