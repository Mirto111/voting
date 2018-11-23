package myProject.voting.service;

import myProject.voting.model.VotingResult;
import myProject.voting.model.VoteSystem;
import myProject.voting.repository.datajpa.CrudVoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

@Service
public class VoteServiceImpl implements VoteService {

    @Autowired
    CrudVoteRepository crudVoteRepository;


    public List<VotingResult> getResultsByDate(LocalDate localDate) {
        return crudVoteRepository.findAllByVotingDate(localDate);
    }

    @Override
    public List<VotingResult> saveAll(List<VotingResult> votes) {
        return crudVoteRepository.saveAll(votes);
    }

    @Override
    public boolean vote(int userId, String restaurantName) {
      return  VoteSystem.getVoteCount().put(userId, restaurantName)!=null;
    }

    @Override
    public Map<String, Long> getCurrentResults() {
        return VoteSystem.getResults();
    }

    @Override
    public void setEndOfVotingTime(LocalTime localTime) {
        VoteSystem.setEndOfVotingTime(localTime);
    }

    @Override
    public LocalTime getEndOfVotingTime() {
        return VoteSystem.getEndOfVotingTime();
    }
}
