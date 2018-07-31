package myProject.voting.web.rest;

import myProject.voting.model.Dish;
import myProject.voting.service.DishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Collection;


@RestController
@RequestMapping("/rest/dishes")
public class DishRestController {


    @Autowired
    DishService dishService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/{id}")
    public Dish get(@RequestParam("restId") int restId, @PathVariable("id") int id) {
        return dishService.get(id, restId);
    }

    @DeleteMapping(value = "/{id}")
    public void delete(@RequestParam("restId") int restId, @PathVariable("id") int id) {
        dishService.delete(id, restId);
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public void update(@RequestBody Dish dish, @RequestParam("restId") int restId) {

        dishService.save(dish, restId);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Dish create(@RequestBody Dish dish, @RequestParam("restId") int restId) {

        return dishService.save(dish, restId);
    }


    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/restaurant/{id}")
    public Collection<Dish> getAllForDay(@PathVariable("id") int restId, @RequestParam(value = "currentDate", required = false) LocalDate currentDate) {

        LocalDate localDate = currentDate != null ? currentDate : LocalDate.now();
        return dishService.getAllByRestaurantAndDate(restId, localDate);
    }


    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Collection<Dish> getAllByDate(@RequestParam(value = "currentDate", required = false) LocalDate currentDate) {
        LocalDate localDate = currentDate != null ? currentDate : LocalDate.now();
        return dishService.getAllByDate(localDate);
    }
}
