package ru.projects.voting.to;

/**
 * Транспортная сущность для результатов голосования.
 */
public class VotingResultTo {

  private final String restaurantName;

  private final int count;

  public VotingResultTo(String restaurantName, int count) {
    this.restaurantName = restaurantName;
    this.count = count;
  }

  public String getRestaurantName() {
    return restaurantName;
  }

  public int getCount() {
    return count;
  }
}
