package myProject.voting.web.rest;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import myProject.voting.VotingApplication;
import myProject.voting.model.Restaurant;
import myProject.voting.model.Vote;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = VotingApplication.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class RestaurantRestControllerTest {


    private static final String API_ROOT = "http://localhost:8080/rest/restaurants";

    private RequestSpecification givenAuth() {

        return RestAssured.given().
                auth().preemptive().basic("admin@gmail.com", "admin");

    }


    @Test
    public void get() {

        Restaurant restaurant = createRandomRestaurant();
        String location = createRestaurantAsUri(restaurant);
        Response response = givenAuth().get(location);
        assertEquals(HttpStatus.OK.value(), response.getStatusCode());
    }


    @Test
    public void vote() {

        Restaurant restaurant = createRandomRestaurant();
        createRestaurantAsUri(restaurant);
        //кол-во голосов до
        long countVoteBefore = Optional.ofNullable(Vote.getResults().get(restaurant.getName())).orElse(0L);
        assertEquals(0, countVoteBefore);

        final Response response = givenAuth().given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(restaurant)
                .post(API_ROOT+"/vote");
        assertEquals(HttpStatus.OK.value(), response.getStatusCode());
        //кол-во голосов после
        long countVoteAfter = Vote.getResults().get(restaurant.getName());
        assertEquals(1, countVoteAfter);

    }


    private Restaurant createRandomRestaurant() {
        final Restaurant restaurant = new Restaurant();
        restaurant.setName(randomAlphabetic(5));

        return restaurant;

    }

    private String createRestaurantAsUri(Restaurant restaurant) {
        final Response response = givenAuth().given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(restaurant)
                .post(API_ROOT);
        return API_ROOT + "/" + response.jsonPath()
                .get("id");
    }




}