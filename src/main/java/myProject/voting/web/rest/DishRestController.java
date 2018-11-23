package myProject.voting.web.rest;

import myProject.voting.model.Dish;
import myProject.voting.service.DishService;
import myProject.voting.util.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Optional;


@RestController
@RequestMapping("/rest/dishes")
public class DishRestController {


    @Autowired
    DishService dishService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/{id}")
    public Dish get(@RequestParam("restId") int restId, @PathVariable("id") int id) {

        return  dishService.get(id, restId);
    }

    @Secured("ROLE_ADMIN")
    @DeleteMapping(value = "/{id}")
    public void delete(@RequestParam("restId") int restId, @PathVariable("id") int id) {

        dishService.delete(id, restId);
    }

    @Secured("ROLE_ADMIN")
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public void update(@RequestBody Dish dish, @RequestParam("restId") int restId) {

        dishService.save(dish, restId);
    }
    @Secured("ROLE_ADMIN")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Dish create(@RequestBody Dish dish, @RequestParam("restId") int restId) {

        return dishService.save(dish, restId);
    }


    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/restaurant/{id}")
    public Collection<Dish> getAllByRestaurantForDay(@PathVariable("id") int restId, @RequestParam(value = "currentDate", required = false)@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate currentDate) {

        return dishService.getAllByRestaurantAndDate(restId, currentDate);
    }


    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Collection<Dish> getAllByDate(@RequestParam(value = "currentDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate currentDate) {

        return dishService.getAllByDate(currentDate);
    }
}
