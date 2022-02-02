package myProject.voting.repository.datajpa;

import myProject.voting.model.VotingResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Transactional(readOnly = true)
public interface CrudVoteRepository extends JpaRepository<VotingResult, Integer> {

    @Override
    @Transactional
    <S extends VotingResult> List<S> saveAll(Iterable<S> entities);

    List<VotingResult> findAllByVotingDate(LocalDate localDate);
}
