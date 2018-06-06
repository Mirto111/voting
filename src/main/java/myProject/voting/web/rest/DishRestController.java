package myProject.voting.web.rest;

import myProject.voting.model.Dish;
import myProject.voting.service.DishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Collection;

/**
 * Created by Secret_Hero on 04.03.2018.
 */

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

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, value = "/{id}")
    public void update(@RequestBody Dish dish, @PathVariable("id") int id, @RequestParam("restId") int restId) {

        dishService.save(dish, restId);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Dish create(@RequestBody Dish dish, @RequestParam("restId") int restId) {

        return dishService.save(dish, restId);
    }


    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/restaurant/{id}")
    public Collection<Dish> getAllForDay(@PathVariable("id") int restId, @RequestParam(value = "voteDate", required = false) LocalDate voteDate) {

        LocalDate localDate = voteDate != null ? voteDate : LocalDate.now();
        return dishService.getAllForDay(restId, localDate);
    }


}
