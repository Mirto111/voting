package ru.projects.voting;

import java.time.LocalTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * Класс системы голосования.
 */
public class VoteSystem {

  // ключ - id пользователя, значение - название ресторана
  private static final ConcurrentHashMap<Integer, String> voteCount = new ConcurrentHashMap<>();

  private static LocalTime endOfVotingTime = LocalTime.of(11, 0);

  public static Map<String, Long> getResults() {
    return voteCount.values().stream()
        .collect(Collectors.groupingBy(v -> v, Collectors.counting()));
  }

  public static ConcurrentHashMap<Integer, String> getVoteCount() {
    return voteCount;
  }

  public static LocalTime getEndOfVotingTime() {
    return endOfVotingTime;
  }

  public static void setEndOfVotingTime(LocalTime endOfVotingTime) {
    VoteSystem.endOfVotingTime = endOfVotingTime;
  }
}
