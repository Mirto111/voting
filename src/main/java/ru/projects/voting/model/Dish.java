package ru.projects.voting.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.math.BigDecimal;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * Сущность блюда ресторана(пункта меню).
 */

@Entity
@Table(name = "dishes")
public class Dish extends BaseEntity {

  @Column(name = "date_time", nullable = false)
  @NotNull
  private LocalDate currentDate;

  @Column(name = "description", nullable = false)
  @NotBlank
  private String description;

  @Column(name = "price", nullable = false)
  private BigDecimal price;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "rest_id", nullable = false)
  @JsonIgnore
  private Restaurant restaurant;

  public Dish() {
  }

  public Dish(Integer id, LocalDate localDate, String description, BigDecimal price) {
    super(id);
    this.currentDate = localDate;
    this.description = description;
    this.price = price;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public void setPrice(BigDecimal money) {
    this.price = money;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public LocalDate getCurrentDate() {
    return currentDate;
  }

  public void setCurrentDate(LocalDate currentDate) {
    this.currentDate = currentDate;
  }

  public Restaurant getRestaurant() {
    return restaurant;
  }

  public void setRestaurant(Restaurant restaurant) {
    this.restaurant = restaurant;
  }

  @Override
  public String toString() {
    return "Dish{"
        + "currentDate=" + currentDate
        + ", description='" + description + '\''
        + ", price=" + price
        + '}';
  }
}
