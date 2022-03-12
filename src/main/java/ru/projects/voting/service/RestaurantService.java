package ru.projects.voting.service;

import java.time.LocalDate;
import java.util.Collection;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.projects.voting.model.Restaurant;
import ru.projects.voting.repository.datajpa.CrudRestaurantRepository;
import ru.projects.voting.util.NotFoundException;

/**
 * Сервис для работы с ресторанами.
 */

@Service
public class RestaurantService {

  private static final Sort SORT_NAME = Sort.by("name");

  private final CrudRestaurantRepository crudRestaurantRepository;

  public RestaurantService(CrudRestaurantRepository crudRestaurantRepository) {
    this.crudRestaurantRepository = crudRestaurantRepository;
  }

  /**
   * Сохраняем ресторан.
   *
   * @param restaurant ресторан для сохранения
   * @return возвращает сохраненный ресторан
   */
  public Restaurant save(Restaurant restaurant) {
    return crudRestaurantRepository.save(restaurant);
  }

  /**
   * Удаление ресторана по Id.
   *
   * @param restId Id ресторана
   */
  public void delete(int restId) {
    get(restId);
    crudRestaurantRepository.delete(restId);
  }

  /**
   * Получение ресторана по Id.
   *
   * @param restId Id ресторана
   * @return возвращаем ресторан
   * @throws NotFoundException если ресторан не найден
   */
  public Restaurant get(int restId) {
    return crudRestaurantRepository.findById(restId)
        .orElseThrow(() -> new NotFoundException("Restaurant with id=" + restId + " not found"));
  }

  /**
   * Получение коллекции ресторанов.
   *
   * @return возвращает коллекцию отсортированную по имени ресторана
   */
  public Collection<Restaurant> getAll() {
    return crudRestaurantRepository.findAll(SORT_NAME);
  }

  /**
   * Получение ресторанов вместе со списком блюд на указанную дату.
   *
   * @param date требуемая дата
   * @return возвращает коллекцию ресторанов
   */
  public Collection<Restaurant> getRestaurantsWithDishesByDate(LocalDate date) {
    return crudRestaurantRepository.getRestaurantsWithDishesByDate(date);
  }

  /**
   * Получение ресторана со списком блюд на указанную дату.
   *
   * @param restId Id ресторана
   * @param date   требуемая дата
   * @return возвращает ресторан
   */
  public Restaurant getWithDishByDate(int restId, LocalDate date) {
    return crudRestaurantRepository.getRestaurantByIdAndDate(restId, date);
  }


}
