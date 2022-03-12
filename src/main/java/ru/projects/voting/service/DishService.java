package ru.projects.voting.service;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.projects.voting.model.Dish;
import ru.projects.voting.repository.datajpa.CrudDishRepository;
import ru.projects.voting.util.IllegalRequestDataException;
import ru.projects.voting.util.NotFoundException;

/**
 * Сервис для работы с блюдами ресторанов.
 */

@Service
public class DishService {

  private final CrudDishRepository crudDishRepository;
  private final RestaurantService crudRestaurantRepository;

  public DishService(CrudDishRepository crudDishRepository,
      RestaurantService crudRestaurantRepository) {
    this.crudDishRepository = crudDishRepository;
    this.crudRestaurantRepository = crudRestaurantRepository;
  }

  /**
   * Сохраняем блюдо ресторана.
   *
   * @param dish   блюдо ресторана
   * @param restId идентификатор ресторана
   * @return возвращает сохраненное блюдо
   */
  @Transactional
  public Dish save(Dish dish, int restId) {
    if (!dish.isNew()) {
      get(dish.getId(), restId);
    }
    dish.setRestaurant(crudRestaurantRepository.get(restId));
    return crudDishRepository.save(dish);
  }

  /**
   * Получаем блюдо ресторана.
   *
   * @param id     идентификатор блюда
   * @param restId идентификатор ресторана
   * @return возвращает блюдо
   * @throws NotFoundException если блюдо не найдено
   */
  public Dish get(int id, int restId) {
    return Optional.ofNullable(crudDishRepository.getByIdAndRestaurantId(id, restId))
        .orElseThrow(() -> new NotFoundException("Dish with id=" + id + " not found"));
  }

  /**
   * Удаляем блюдо по Id.
   *
   * @param id     идентификатор блюда
   * @param restId идентификатор ресторана
   * @throws IllegalRequestDataException если блюдо не было удалено
   */
  public void delete(int id, int restId) {
    get(id, restId);
    if (crudDishRepository.delete(id, restId) == 0) {
      throw new IllegalRequestDataException("Dish with id=" + id + " not found");
    }
  }

  /**
   * Получаем коллекцию блюд по Id ресторана и дате.
   *
   * @param restId    идентификатор ресторана
   * @param localDate запрашиваемая дата, если null берется текущая
   * @return возвращает коллекция блюд
   */
  public Collection<Dish> getAllByRestaurantAndDate(int restId, LocalDate localDate) {
    LocalDate date = localDate != null ? localDate : LocalDate.now();
    return crudDishRepository.getAllByRestaurantIdAndCurrentDate(restId, date);
  }

  /**
   * Получаем коллекция блюд по Id ресторана.
   *
   * @param restId идентификатор ресторана
   * @return возвращает коллекцию блюд
   */
  public Collection<Dish> getAllByRestaurant(int restId) {
    return crudDishRepository.getAllByRestaurantId(restId);
  }

}
