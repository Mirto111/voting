package myProject.voting.web.rest;

import myProject.voting.AuthorizedUser;
import myProject.voting.model.Restaurant;
import myProject.voting.model.VotingResult;
import myProject.voting.model.VoteSystem;
import myProject.voting.service.RestaurantService;
import myProject.voting.service.VoteService;
import myProject.voting.to.VotingResultTo;
import myProject.voting.util.IllegalRequestDataException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/rest/voting")
public class VoteRestController {

    @Autowired
    VoteService voteService;

    @Autowired
    RestaurantService restaurantService;


    @PostMapping(value = "/vote/{id}")
    public void vote(@PathVariable("id") int id, @RequestParam("restName") String restName) {
        if (LocalTime.now().isBefore(VoteSystem.getEndOfVotingTime())){

                if (restaurantService.get(id).getName().equals(restName)){
                    voteService.vote(AuthorizedUser.id(), restaurantService.get(id).getName());
                }else {
                    throw new IllegalRequestDataException("Restaurant must be with name " + restName);
                }

        }else {
            throw new IllegalArgumentException("Voting is over");
        }

    }

    @GetMapping(value = "/results", produces = MediaType.APPLICATION_JSON_VALUE)
    public Collection<VotingResultTo> getCurrentResults() {

        List<VotingResultTo>votes= VoteSystem.getResults().entrySet().stream().map(m -> new VotingResultTo(m.getKey(), m.getValue().intValue())).collect(Collectors.toList());

        return votes;
    }

    @Secured("ROLE_ADMIN")
    @PostMapping(value = "/saveResults")
    public void saveResults() {

        List<VotingResult>votes= VoteSystem.getResults().entrySet().stream().map(m -> new VotingResult(m.getKey(), m.getValue().intValue())).collect(Collectors.toList());
        voteService.saveAll(new ArrayList<>(votes));

    }
    @GetMapping(value = "/pastResults", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<VotingResult> getResultsByDate(@RequestParam(value = "votingDate")@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate localDate) {
        return voteService.getResultsByDate(localDate);
    }

    @GetMapping(value = "/endVotingTime")
    public LocalTime getEndOfVotingTime() {

        return voteService.getEndOfVotingTime();
    }
    @Secured("ROLE_ADMIN")
    @PostMapping(value = "/setEndVotingTime")
    public void setEndOfVotingTime(@RequestParam(value = "endVotingTime") @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime localTime){
        voteService.setEndOfVotingTime(localTime);
    }


}
