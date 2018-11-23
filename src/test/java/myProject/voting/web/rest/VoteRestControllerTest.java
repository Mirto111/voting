package myProject.voting.web.rest;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import myProject.voting.VotingApplication;
import myProject.voting.model.Restaurant;
import myProject.voting.model.VoteSystem;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = VotingApplication.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class VoteRestControllerTest {


    private static final String API_ROOT = "http://localhost:8080/rest/voting";

    private RequestSpecification givenAuth() {

        return RestAssured.given().
                auth().preemptive().basic("admin@gmail.com", "admin");

    }

    @Test
    public void vote() {

        Restaurant restaurant = createRandomRestaurant();
        createRestaurantAsUri(restaurant);
        //кол-во голосов до
        long countVoteBefore = Optional.ofNullable(VoteSystem.getResults().get(restaurant.getName())).orElse(0L);
        assertEquals(0, countVoteBefore);

        final Response response = givenAuth().given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(restaurant)
                .post(API_ROOT+"/vote/"+restaurant.getId()+"?restName="+restaurant.getName());
        assertEquals(HttpStatus.OK.value(), response.getStatusCode());
        //кол-во голосов после
        long countVoteAfter = VoteSystem.getResults().get(restaurant.getName());
        assertEquals(1, countVoteAfter);

    }


    @Test
    public void saveResults() {

        VoteSystem.getVoteCount().put(1,"Три пискаря");
        VoteSystem.getVoteCount().put(2,"У моря");

        Response response = givenAuth()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .post(API_ROOT+"/saveResults");

        assertEquals(HttpStatus.OK.value(), response.getStatusCode());

        response = givenAuth().get(API_ROOT+"/pastResults"+"?votingDate="+LocalDate.now());

        assertEquals(HttpStatus.OK.value(), response.getStatusCode());
        assertTrue(response.as(List.class)
                .size() > 0);

    }



    @Test
    public void getEndOfVotingTime() {
        Response response = givenAuth().get(API_ROOT +"/endVotingTime");
        String delQuote = response.print().replace("\u005c\u0022","");
        assertEquals(VoteSystem.getEndOfVotingTime(), LocalTime.parse(delQuote));


    }

    @Test
    public void setEndOfVotingTime() {

        VoteSystem.setEndOfVotingTime(LocalTime.of(15,0));

        Response response = givenAuth()
                .post(API_ROOT+"/setEndVotingTime?endVotingTime=15:00:00");

        assertEquals(HttpStatus.OK.value(), response.getStatusCode());
        response = givenAuth().get(API_ROOT +"/endVotingTime");
        String delQuote = response.print().replace("\u005c\u0022","");
        assertEquals(LocalTime.of(15,0),LocalTime.parse(delQuote));

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
                .post("http://localhost:8080/rest/restaurants");

        restaurant.setId(response.jsonPath().get("id"));
        return API_ROOT + "/" + response.jsonPath()
                .get("id");
    }


}