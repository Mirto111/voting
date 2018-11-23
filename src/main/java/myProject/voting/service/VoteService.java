package myProject.voting.service;

import myProject.voting.model.VotingResult;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

public interface VoteService {

    List<VotingResult> getResultsByDate(LocalDate localDate);

    List<VotingResult> saveAll(List<VotingResult> votes);

    boolean vote(int userId, String restaurantName);

    Map<String, Long> getCurrentResults();

    void setEndOfVotingTime(LocalTime localTime);

    LocalTime getEndOfVotingTime();

}
