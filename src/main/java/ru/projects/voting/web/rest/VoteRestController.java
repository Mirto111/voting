package ru.projects.voting.web.rest;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.projects.voting.AuthorizedUser;
import ru.projects.voting.model.VoteSystem;
import ru.projects.voting.model.VotingResult;
import ru.projects.voting.service.RestaurantService;
import ru.projects.voting.service.VoteService;
import ru.projects.voting.to.VotingResultTo;
import ru.projects.voting.util.IllegalRequestDataException;


@RestController
@RequestMapping("/rest/voting")
public class VoteRestController {

    private final VoteService voteService;
    private final RestaurantService restaurantService;

    public VoteRestController(VoteService voteService, RestaurantService restaurantService) {
        this.voteService = voteService;
        this.restaurantService = restaurantService;
    }

    @PostMapping(value = "/vote/{id}")
    public void vote(@PathVariable("id") int id, @RequestParam("restName") String restName) {
        if (LocalTime.now().isBefore(VoteSystem.getEndOfVotingTime())) {

            if (restaurantService.get(id).getName().equals(restName)) {
                voteService.vote(AuthorizedUser.id(), restaurantService.get(id).getName());
            } else {
                throw new IllegalRequestDataException("Restaurant must be with name " + restName);
            }

        } else {
            throw new IllegalArgumentException("Voting is over");
        }

    }

    @GetMapping(value = "/results", produces = MediaType.APPLICATION_JSON_VALUE)
    public Collection<VotingResultTo> getCurrentResults() {
        return VoteSystem.getResults().entrySet().stream()
                .map(m -> new VotingResultTo(m.getKey(), m.getValue().intValue()))
                .collect(Collectors.toList());
    }

    @Secured("ROLE_ADMIN")
    @PostMapping(value = "/saveResults")
    public void saveResults() {
        List<VotingResult> votes = VoteSystem.getResults().entrySet().stream().map(m -> new VotingResult(m.getKey(), m.getValue().intValue())).collect(Collectors.toList());
        voteService.saveAll(new ArrayList<>(votes));
    }

    @GetMapping(value = "/pastResults", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<VotingResult> getResultsByDate(@RequestParam(value = "votingDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate localDate) {
        return voteService.getResultsByDate(localDate);
    }

    @GetMapping(value = "/endVotingTime")
    public LocalTime getEndOfVotingTime() {
        return voteService.getEndOfVotingTime();
    }

    @Secured("ROLE_ADMIN")
    @PostMapping(value = "/setEndVotingTime")
    public void setEndOfVotingTime(@RequestParam(value = "endVotingTime") @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime localTime) {
        voteService.setEndOfVotingTime(localTime);
    }


}
