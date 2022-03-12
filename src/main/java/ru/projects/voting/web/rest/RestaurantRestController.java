package ru.projects.voting.web.rest;

import java.time.LocalDate;
import java.util.Collection;
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
import ru.projects.voting.model.Restaurant;
import ru.projects.voting.service.RestaurantService;
import ru.projects.voting.util.IllegalRequestDataException;

/**
 * Контроллер для работы с ресторанами.
 */

@RestController
@RequestMapping("/rest/restaurants")
public class RestaurantRestController {

  private final RestaurantService restaurantService;

  public RestaurantRestController(RestaurantService restaurantService) {
    this.restaurantService = restaurantService;
  }

  /**
   * Получение ресторана по Id.
   *
   * @param id Id ресторана
   * @return возвращаем ресторан
   */
  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/{id}")
  public Restaurant get(@PathVariable("id") int id) {
    return restaurantService.get(id);
  }

  /**
   * Удаление ресторана по Id.
   *
   * @param id Id ресторана
   */
  @Secured("ROLE_ADMIN")
  @DeleteMapping(value = "/{id}")
  public void delete(@PathVariable("id") int id) {
    restaurantService.delete(id);
  }


  /**
   * Создаем новый ресторан.
   *
   * @param restaurant ресторан для сохранения
   * @return возвращает сохраненный ресторан
   */
  @Secured("ROLE_ADMIN")
  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public Restaurant create(@RequestBody Restaurant restaurant) {
    return restaurantService.save(restaurant);
  }

  /**
   * Обновляем данные ресторана.
   *
   * @param restaurant ресторан для сохранения
   * @param id         Id ресторана
   */
  @Secured("ROLE_ADMIN")
  @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, value = "/{id}")
  public void update(@RequestBody Restaurant restaurant, @PathVariable("id") int id) {
    if (restaurant.isNew() || restaurant.getId() != id) {
      throw new IllegalRequestDataException("Restaurant must be with id=" + id);
    }
    restaurantService.save(restaurant);
  }

  /**
   * Получение коллекции ресторанов.
   *
   * @return возвращает коллекцию отсортированную по имени ресторана
   */
  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public Collection<Restaurant> getAll() {
    return restaurantService.getAll();
  }

  /**
   * Получение ресторанов вместе со списком блюд.
   *
   * @return возвращает коллекцию ресторанов
   */
  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/menu")
  public Collection<Restaurant> getMenuAllForToday() {
    return restaurantService.getRestaurantsWithDishesByDate(LocalDate.now());
  }

  /**
   * Получение ресторана со списком блюд.
   *
   * @param restId Id ресторана
   * @return возвращает ресторан
   */
  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/{id}/menu")
  public Restaurant getWithDish(@PathVariable("id") int restId) {
    return restaurantService.getWithDishByDate(restId, LocalDate.now());
  }

}


