package ru.projects.voting.web.rest;

import java.time.LocalDate;
import java.util.Collection;
import org.springframework.format.annotation.DateTimeFormat;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.projects.voting.model.Dish;
import ru.projects.voting.service.DishService;

/**
 * Контроллер для работы с блюдами ресторанов.
 */

@RestController
@RequestMapping({"/rest/restaurants/{restId}/dishes"})
public class DishRestController {

  private final DishService dishService;

  public DishRestController(DishService dishService) {
    this.dishService = dishService;
  }

  /**
   * Получаем блюдо ресторана.
   *
   * @param id     идентификатор блюда
   * @param restId идентификатор ресторана
   * @return возвращает блюдо
   */
  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/{id}")
  public Dish get(@PathVariable("restId") int restId, @PathVariable("id") int id) {
    return dishService.get(id, restId);
  }

  /**
   * Удаляем блюдо по Id.
   *
   * @param id     идентификатор блюда
   * @param restId идентификатор ресторана
   */
  @Secured("ROLE_ADMIN")
  @DeleteMapping(value = "/{id}")
  public void delete(@PathVariable("restId") int restId, @PathVariable("id") int id) {
    dishService.delete(id, restId);
  }

  /**
   * Обновляем блюдо ресторана.
   *
   * @param dish   блюдо ресторана
   * @param restId идентификатор ресторана
   */
  @Secured("ROLE_ADMIN")
  @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
  public void update(@RequestBody Dish dish, @PathVariable("restId") int restId) {
    dishService.save(dish, restId);
  }

  /**
   * Создаем новое блюдо ресторана.
   *
   * @param dish   блюдо ресторана
   * @param restId идентификатор ресторана
   * @return возвращает сохраненное блюдо
   */
  @Secured("ROLE_ADMIN")
  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public Dish create(@RequestBody Dish dish, @PathVariable("restId") int restId) {
    return dishService.save(dish, restId);
  }

  /**
   * Получаем коллекцию блюд по Id ресторана и дате.
   *
   * @param restId идентификатор ресторана
   * @param date   запрашиваемая дата, если null берется текущая
   * @return возвращает коллекция блюд
   */
  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/by-date")
  public Collection<Dish> getAllByRestaurantForDay(@PathVariable int restId,
      @RequestParam(value = "date", required = false)
      @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
    return dishService.getAllByRestaurantAndDate(restId, date);
  }

  /**
   * Получаем коллекция блюд по Id ресторана.
   *
   * @param restId идентификатор ресторана
   * @return возвращает коллекцию блюд
   */
  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public Collection<Dish> getAllByRestaurant(@PathVariable int restId) {
    return dishService.getAllByRestaurant(restId);
  }
}
