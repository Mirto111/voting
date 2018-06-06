package myProject.voting.service;

import datajpa.RestaurantTestData;
import myProject.voting.model.Dish;
import myProject.voting.repository.datajpa.CrudDishRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDate;

import static org.junit.Assert.*;


@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
public class DishServiceImplTest {

    @Autowired
    DishService dishService;

    @Test
    public void save() {
        Dish dish = new Dish(LocalDate.now(),"Eda",5.5);
        dishService.save(dish, RestaurantTestData.REST_ID);
    }


    @Test
    public void delete() {
        dishService.delete(7, RestaurantTestData.REST_ID);
    }

    @Test
    public void getAllForDay() {
        dishService.getAllForDay(RestaurantTestData.REST_ID, LocalDate.now());
    }
}