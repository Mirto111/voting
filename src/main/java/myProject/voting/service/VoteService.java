package myProject.voting.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import myProject.voting.model.VoteSystem;
import myProject.voting.model.VotingResult;
import myProject.voting.repository.datajpa.CrudVoteRepository;
import org.springframework.stereotype.Service;

@Service
public class VoteService {

    private final CrudVoteRepository crudVoteRepository;

    public VoteService(CrudVoteRepository crudVoteRepository) {
        this.crudVoteRepository = crudVoteRepository;
    }

    public List<VotingResult> getResultsByDate(LocalDate localDate) {
        return crudVoteRepository.findAllByVotingDate(localDate);
    }

    public List<VotingResult> saveAll(List<VotingResult> votes) {
        return crudVoteRepository.saveAll(votes);
    }

    public boolean vote(int userId, String restaurantName) {
        return VoteSystem.getVoteCount().put(userId, restaurantName) != null;
    }

    public Map<String, Long> getCurrentResults() {
        return VoteSystem.getResults();
    }

    public void setEndOfVotingTime(LocalTime localTime) {
        VoteSystem.setEndOfVotingTime(localTime);
    }

    public LocalTime getEndOfVotingTime() {
        return VoteSystem.getEndOfVotingTime();
    }
}
