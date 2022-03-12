package ru.projects.voting.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

/**
 * Сущность ресторана.
 */

@Entity
@Table(name = "restaurants")
public class Restaurant extends BaseEntity {

  @Column(name = "name")
  @NotBlank
  private String name;

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "restaurant")
  @JsonProperty(access = JsonProperty.Access.READ_ONLY)
  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  private List<Dish> dishes;

  public Restaurant() {
  }

  public Restaurant(String name) {
    this(null, name);
  }

  public Restaurant(Integer id, String name) {
    super(id);
    this.name = name;
  }

  public List<Dish> getDishes() {
    return dishes;
  }

  public void setDishes(List<Dish> dishes) {
    this.dishes = dishes;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    return "Restaurant{"
        + "name='" + name + '\''
        + '}';
  }
}
