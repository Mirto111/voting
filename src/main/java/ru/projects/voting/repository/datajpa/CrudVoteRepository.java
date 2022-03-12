package ru.projects.voting.repository.datajpa;

import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.projects.voting.model.VotingResult;

/**
 * Репозиторий для сохранения и получения результатов голосования.
 */

@Transactional(readOnly = true)
public interface CrudVoteRepository extends JpaRepository<VotingResult, Integer> {

    @Query("SELECT v FROM VotingResult v WHERE v.votingDate=:date ORDER BY v.restaurantName")
    List<VotingResult> findAllByVotingDate(LocalDate date);
}
