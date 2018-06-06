package myProject.voting.service;

import myProject.voting.model.Vote;

import java.time.LocalDate;
import java.util.List;

public interface VoteService {

    List<Vote> getAllByDate(LocalDate localDate);

    List<Vote> saveAll(List<Vote> votes);

    void vote(int userId, String restaurantName);


}
