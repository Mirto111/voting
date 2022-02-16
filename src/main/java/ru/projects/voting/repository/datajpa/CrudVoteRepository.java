package ru.projects.voting.repository.datajpa;

import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import ru.projects.voting.model.VotingResult;

@Transactional(readOnly = true)
public interface CrudVoteRepository extends JpaRepository<VotingResult, Integer> {

    List<VotingResult> findAllByVotingDate(LocalDate localDate);
}
