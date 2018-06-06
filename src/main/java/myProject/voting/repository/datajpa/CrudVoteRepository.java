package myProject.voting.repository.datajpa;

import myProject.voting.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface CrudVoteRepository extends JpaRepository<Vote,Integer> {

    @Override
    <S extends Vote> List<S> saveAll(Iterable<S> entities);

    List<Vote> findAllByVotingDate(LocalDate localDate);
}
