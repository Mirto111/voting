package ru.projects.voting.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import ru.projects.voting.VoteSystem;
import ru.projects.voting.model.VotingResult;
import ru.projects.voting.repository.datajpa.CrudVoteRepository;
import ru.projects.voting.to.VotingResultTo;

/**
 * Сервис для голосования.
 */

@Service
public class VoteService {

  private final CrudVoteRepository crudVoteRepository;

  public VoteService(CrudVoteRepository crudVoteRepository) {
    this.crudVoteRepository = crudVoteRepository;
  }

  /**
   * Получение результатов голосования по дате.
   *
   * @param localDate требуемая дата
   * @return коллекция с результатами голосования
   */
  public List<VotingResult> getResultsByDate(LocalDate localDate) {
    return crudVoteRepository.findAllByVotingDate(localDate);
  }

  /**
   * Сохранение результатов голосования.
   *
   * @return сохраненный список результатов
   */
  public List<VotingResult> saveAll() {
    List<VotingResult> votes = VoteSystem.getResults().entrySet().stream()
        .map(m -> new VotingResult(m.getKey(), m.getValue().intValue()))
        .collect(Collectors.toList());
    return crudVoteRepository.saveAll(votes);
  }

  /**
   * Голосование за ресторан.
   *
   * @param userId         Id пользователя
   * @param restaurantName название ресторана
   */
  public void vote(int userId, String restaurantName) {
    VoteSystem.getVoteCount().put(userId, restaurantName);
  }

  /**
   * Получение текущих результатов.
   *
   * @return список текущих результатов
   */
  public List<VotingResultTo> getCurrentResults() {
    return VoteSystem.getResults().entrySet().stream()
        .map(m -> new VotingResultTo(m.getKey(), m.getValue().intValue()))
        .sorted(Comparator.comparing(VotingResultTo::getRestaurantName))
        .collect(Collectors.toList());
  }

  /**
   * Получение времени окончания голосования.
   *
   * @return время окончания голосования
   */
  public LocalTime getEndOfVotingTime() {
    return VoteSystem.getEndOfVotingTime();
  }

  /**
   * Установка времени окончания голосования.
   *
   * @param localTime требуемое время
   */
  public void setEndOfVotingTime(LocalTime localTime) {
    VoteSystem.setEndOfVotingTime(localTime);
  }
}
