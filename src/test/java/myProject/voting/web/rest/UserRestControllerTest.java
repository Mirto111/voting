package myProject.voting.web.rest;


import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.introspect.Annotated;
import com.fasterxml.jackson.databind.introspect.JacksonAnnotationIntrospector;
import io.restassured.RestAssured;
import io.restassured.internal.mapping.Jackson2Mapper;
import io.restassured.mapper.ObjectMapper;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import java.util.Collections;
import myProject.voting.VotingApplication;
import myProject.voting.model.Role;
import myProject.voting.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;


@SpringBootTest(classes = VotingApplication.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class UserRestControllerTest {

  private static final String API_ROOT = "http://localhost:8080/rest/users";

  private static final ObjectMapper objectMapper = new Jackson2Mapper(((type, charset) -> {
    com.fasterxml.jackson.databind.ObjectMapper om = new com.fasterxml.jackson.databind.ObjectMapper()
        .findAndRegisterModules();
    om.setAnnotationIntrospector(new IgnoreJacksonWriteOnlyAccess());
    return om;
  }));

  private static RequestSpecification givenAuth() {
    return RestAssured.given().
        auth().preemptive().basic("admin@gmail.com", "admin");
  }


  static User createRandomUser() {
    final User user = new User();
    user.setEmail("test" + randomAlphabetic(3) + "@yandex.ru");
    user.setName(randomAlphabetic(15));
    user.setPassword(randomAlphabetic(8));
    user.setRoles(Collections.singleton(Role.USER));
    return user;
  }

  static String createUserAsUri(User user) {

    final Response response = givenAuth().given()
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .body(user, objectMapper)
        .post(API_ROOT);
    response.asString();
    return API_ROOT + "/" + response.jsonPath()
        .get("id");
  }

  @Test
  public void delete() {
    final User user = createRandomUser();
    final String location = createUserAsUri(user);

    Response response = givenAuth().delete(location);
    assertEquals(HttpStatus.OK.value(), response.getStatusCode());

    response = givenAuth().get(location);
    assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatusCode());
  }

  @Test
  public void create() {
    User user = createRandomUser();

    final Response response = givenAuth()
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .body(user, objectMapper)
        .post(API_ROOT);
    assertEquals(HttpStatus.CREATED.value(), response.getStatusCode());
  }

  @Test
  public void get() {
    final User user = createRandomUser();
    final String location = createUserAsUri(user);

    final Response response = givenAuth().get(location);
    assertEquals(HttpStatus.OK.value(), response.getStatusCode());
  }

  @Test
  public void update() {

    final User user = createRandomUser();
    final String location = createUserAsUri(user);

    user.setId(Integer.parseInt(location.split("rest/users/")[1]));
    user.setName("Dave");

    Response response = givenAuth()
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .body(user, objectMapper)
        .put(location);

    assertEquals(HttpStatus.OK.value(), response.getStatusCode());

    response = givenAuth().get(location);
    assertEquals(HttpStatus.OK.value(), response.getStatusCode());
    assertEquals("Dave", response.jsonPath().get("name"));
  }

  @Test
  public void getByEmail() {
    final User user = createRandomUser();
    createUserAsUri(user);

    final Response response = givenAuth().when().get(API_ROOT + "/email/" + user.getEmail());

    assertEquals(HttpStatus.OK.value(), response.getStatusCode());

    assertEquals(user.getEmail(), response.jsonPath().get("email"));

  }

  private static class IgnoreJacksonWriteOnlyAccess extends JacksonAnnotationIntrospector {

    @Override
    public JsonProperty.Access findPropertyAccess(Annotated m) {
      JsonProperty.Access access = super.findPropertyAccess(m);
      if (access == JsonProperty.Access.WRITE_ONLY) {
        return JsonProperty.Access.AUTO;
      }
      return access;
    }
  }
}