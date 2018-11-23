package myProject.voting.model;



import java.time.LocalTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class VoteSystem {

    // ключ - id пользователя, значение - название ресторана
    private static ConcurrentHashMap<Integer, String> voteCount = new ConcurrentHashMap<>();

    private static LocalTime endOfVotingTime = LocalTime.of(11,00);


    public static Map<String, Long> getResults() {

        return voteCount.values().stream().collect(Collectors.groupingBy(v -> v, Collectors.counting()));
    }

    public static ConcurrentHashMap<Integer, String> getVoteCount() {
        return voteCount;
    }


    public static void setEndOfVotingTime(LocalTime endOfVotingTime) {
        VoteSystem.endOfVotingTime = endOfVotingTime;
    }

    public static LocalTime getEndOfVotingTime() {
        return endOfVotingTime;
    }
}
