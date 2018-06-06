package myProject.voting.web.rest;

import datajpa.UserTestData;
import myProject.voting.model.Restaurant;
import myProject.voting.model.User;
import myProject.voting.model.Vote;
import myProject.voting.service.VoteService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.*;

public class RestaurantRestControllerTest extends AbstractControllerTest {
    @Autowired
    VoteService voteService;
    @Autowired
    RestaurantRestController restaurantRestController;

    @Test
    public void get() {
    }

    @Test
    public void delete() {
    }

    @Test
    public void create() {
    }

    @Test
    public void update() {
    }

    @Test
    public void getAll() {
    }


    @Test
    public void getResults() {
    }


}