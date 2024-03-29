package ru.projects.voting.web.rest;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.junit.jupiter.api.Assertions.assertEquals;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import java.math.BigDecimal;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import ru.projects.voting.VotingApplication;
import ru.projects.voting.model.Dish;
import ru.projects.voting.model.Restaurant;


@SpringBootTest(classes = VotingApplication.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class DishRestControllerTest {

  private static final String API_ROOT = "http://localhost:8080/rest/restaurants";

  private RequestSpecification givenAuth() {
    return RestAssured.given().
        auth().preemptive().basic("admin@gmail.com", "admin");
  }

  @Test
  public void get() {
    Dish dish = createRandomDish();
    String location = createDishAsUri(dish);
    final Response response = givenAuth().get(location);
    assertEquals(HttpStatus.OK.value(), response.getStatusCode());
  }

  @Test
  public void delete() {
    Dish dish = createRandomDish();
    String location = createDishAsUri(dish);

    Response response = givenAuth().delete(location);
    assertEquals(HttpStatus.OK.value(), response.getStatusCode());

    response = givenAuth().get(location);
    assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatusCode());
  }

  @Test
  public void update() {
    Dish dish = createRandomDish();
    String location = createDishAsUri(dish);

    dish.setPrice(BigDecimal.valueOf(10.20));

    Response response = givenAuth()
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .body(dish)
        .put(API_ROOT + "/" + dish.getRestaurant().getId() + "/dishes");
    assertEquals(HttpStatus.OK.value(), response.getStatusCode());

    response = givenAuth().get(location);
    assertEquals(HttpStatus.OK.value(), response.getStatusCode());
    assertEquals(dish.getPrice(), BigDecimal.valueOf(response.jsonPath().getDouble("price")));
  }

  @Test
  public void create() {
    Dish dish = createRandomDish();

    final Response response = givenAuth()
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .body(dish)
        .post(API_ROOT + "/" + dish.getRestaurant().getId() + "/dishes");

    assertEquals(HttpStatus.CREATED.value(), response.getStatusCode());
  }

  private Dish createRandomDish() {
    final Dish dish = new Dish();
    dish.setDescription(randomAlphabetic(7));
    dish.setCurrentDate(LocalDate.now());
    dish.setPrice(BigDecimal.valueOf(10.15));
    dish.setRestaurant(createRandomRestaurant());
    return dish;
  }

  private String createDishAsUri(Dish dish) {
    final Response response = givenAuth().given()
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .body(dish)
        .post(API_ROOT + "/" + dish.getRestaurant().getId() + "/dishes");

    dish.setId(response.jsonPath().get("id"));
    return API_ROOT + "/" + dish.getRestaurant().getId() + "/dishes/" + response.jsonPath()
        .get("id");
  }

  private Restaurant createRandomRestaurant() {
    final Restaurant restaurant = new Restaurant();
    restaurant.setName(randomAlphabetic(5));
    final Response response = givenAuth().given()
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .body(restaurant)
        .post("http://localhost:8080/rest/restaurants");

    restaurant.setId(response.jsonPath().get("id"));

    return restaurant;
  }
}