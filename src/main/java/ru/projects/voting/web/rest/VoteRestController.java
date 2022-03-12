package ru.projects.voting.web.rest;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;
import java.util.List;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.projects.voting.AuthorizedUser;
import ru.projects.voting.VoteSystem;
import ru.projects.voting.model.VotingResult;
import ru.projects.voting.service.RestaurantService;
import ru.projects.voting.service.VoteService;
import ru.projects.voting.to.VotingResultTo;
import ru.projects.voting.util.IllegalRequestDataException;


/**
 * Контроллер системы голосования.
 */

@RestController
@RequestMapping("/rest/voting")
public class VoteRestController {

  private final VoteService voteService;
  private final RestaurantService restaurantService;

  public VoteRestController(VoteService voteService, RestaurantService restaurantService) {
    this.voteService = voteService;
    this.restaurantService = restaurantService;
  }

  /**
   * Голосование за ресторан по Id.
   *
   * @param restId   идентификатор ресторана
   * @param restName название ресторана
   */
  @PostMapping(value = "/vote/{id}")
  @Transactional
  public void vote(@PathVariable("id") int restId, @RequestParam("restName") String restName) {
    if (LocalTime.now().isBefore(VoteSystem.getEndOfVotingTime())) {
      if (restaurantService.get(restId).getName().equals(restName)) {
        voteService.vote(AuthorizedUser.id(), restaurantService.get(restId).getName());
      } else {
        throw new IllegalRequestDataException("Restaurant must be with name " + restName);
      }
    } else {
      throw new IllegalArgumentException("Voting is over");
    }

  }

  /**
   * Получение коллекции текущих результатов голосования.
   *
   * @return коллекция текущих результатов
   */
  @GetMapping(value = "/results", produces = MediaType.APPLICATION_JSON_VALUE)
  public Collection<VotingResultTo> getCurrentResults() {
    return voteService.getCurrentResults();
  }

  /**
   * Сохранение результатов голосования.
   */
  @Secured("ROLE_ADMIN")
  @PostMapping(value = "/saveResults")
  public void saveResults() {
    voteService.saveAll();
  }

  /**
   * Получение списка результатов голосования по дате.
   *
   * @param localDate требуемая дата
   * @return возвращает список результатов
   */
  @GetMapping(value = "/pastResults", produces = MediaType.APPLICATION_JSON_VALUE)
  public List<VotingResult> getResultsByDate(
      @RequestParam(value = "votingDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate localDate) {
    return voteService.getResultsByDate(localDate);
  }

  /**
   * Получение времени окончания голосования.
   *
   * @return время окончания голосования
   */
  @GetMapping(value = "/endVotingTime")
  public LocalTime getEndOfVotingTime() {
    return voteService.getEndOfVotingTime();
  }

  /**
   * Установка времени окончания голосования.
   *
   * @param localTime требуемое время
   */
  @Secured("ROLE_ADMIN")
  @PostMapping(value = "/setEndVotingTime")
  public void setEndOfVotingTime(
      @RequestParam(value = "endVotingTime") @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime localTime) {
    voteService.setEndOfVotingTime(localTime);
  }


}
