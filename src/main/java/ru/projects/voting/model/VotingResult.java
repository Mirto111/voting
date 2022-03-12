package ru.projects.voting.model;

import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * Сущность результата голосования.
 */

@Entity
@Table(name = "voting_result")
public class VotingResult extends BaseEntity {

  @Column(name = "vote_date", nullable = false)
  @NotNull
  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private LocalDate votingDate;

  @Column(name = "rest_name", nullable = false)
  @NotBlank
  private String restaurantName;

  @Column(name = "count_vote", nullable = false)
  private int count;

  public VotingResult() {
  }

  public VotingResult(@NotBlank String restaurantName, int count) {
    this(null, LocalDate.now(), restaurantName, count);
  }

  public VotingResult(Integer id, @NotNull LocalDate votingDate, @NotBlank String restaurantName,
      int count) {
    super(id);
    this.votingDate = votingDate;
    this.restaurantName = restaurantName;
    this.count = count;
  }

  public LocalDate getVotingDate() {
    return votingDate;
  }

  public void setVotingDate(LocalDate votingDate) {
    this.votingDate = votingDate;
  }

  public String getRestaurantName() {
    return restaurantName;
  }

  public void setRestaurantName(String restaurantName) {
    this.restaurantName = restaurantName;
  }

  public int getCount() {
    return count;
  }

  public void setCount(int count) {
    this.count = count;
  }

  @Override
  public String toString() {
    return "VotingResult{"
        + "votingDate=" + votingDate
        + ", restaurantName='"
        + restaurantName + '\''
        + ", count=" + count
        + '}';
  }
}
