package myProject.voting.service;

import myProject.voting.model.Vote;
import myProject.voting.repository.datajpa.CrudVoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class VoteServiceImpl implements VoteService {

    @Autowired
    CrudVoteRepository crudVoteRepository;


    @Override
    public List<Vote> getAllByDate(LocalDate localDate) {
        return crudVoteRepository.findAllByVotingDate(localDate);
    }

    @Override
    public List<Vote> saveAll(List<Vote> votes) {
        return crudVoteRepository.saveAll(votes);
    }

    @Override
    public void vote(int userId, String restaurantName) {
        Vote.getVoteCount().put(userId, restaurantName);
    }
}
