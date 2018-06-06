package myProject.voting.service;

import datajpa.RestaurantTestData;
import datajpa.UserTestData;
import myProject.voting.model.Restaurant;
import myProject.voting.model.User;
import myProject.voting.model.Vote;
import myProject.voting.repository.datajpa.CrudRestaurantRepository;
import myProject.voting.web.rest.RestaurantRestController;
import org.hibernate.Hibernate;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;


@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
public class RestaurantServiceImplTest {

    @Autowired
    RestaurantService restaurantService;
    @Autowired
    VoteService voteService;


    @Test
    public void save() {
        restaurantService.save(RestaurantTestData.RESTAURANT_2);
    }


    @Test
    @Transactional
    public void get() {

        Restaurant restaurant = restaurantService.get(RestaurantTestData.REST_ID);

        //Assert.assertEquals(RestaurantTestData.RESTAURANT_1, restaurant);
    }

    @Test
    public void delete() {
        Assert.assertTrue(restaurantService.delete(RestaurantTestData.REST_ID) != 0);
    }


    @Test
    public void vote() {


        voteService.vote(1, "Три кабана");
        voteService.vote(2, "Кфс");

    }

    @Test
    public void getResultsVoting() {


    }

}

/*    @Test
    public void getAll() {

    }

 }*/
