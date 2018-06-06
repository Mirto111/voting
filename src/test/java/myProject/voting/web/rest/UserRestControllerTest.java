package myProject.voting.web.rest;

import datajpa.UserTestData;
import myProject.voting.model.Role;
import myProject.voting.model.User;
import myProject.voting.service.UserService;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.request.RequestPostProcessor;

import static datajpa.UserTestData.ADMIN;
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserRestControllerTest extends AbstractControllerTest{

    private static final String REST_URL = "/rest/users/";
    @Autowired
    UserService userService;

    @Autowired
    RestaurantRestController restaurantRestController;

    @Test
    @WithMockUser(username = "user@yandex.ru", password = "user",authorities = { "ROLE_USER" })
    public void testGet() throws Exception {
        userService.getAll();

/*      mockMvc.perform(get(REST_URL + UserTestData.ADMIN_ID).with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andDo(print());*/

    }

    @Test
    public void testGetUnauth() throws Exception {
        mockMvc.perform(get(REST_URL))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"USER", "ADMIN"})
    public void shouldReturn200WhenSendingRequestToControllerWithRoleUser() throws Exception {
        mockMvc.perform(get("/test")).andExpect(status().isOk());
    }

    @Test
    @WithMockUser
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


        System.out.println(restaurantRestController.getResults());

    }

    @Test
    public void getByMail() {
    }

    public static RequestPostProcessor userHttpBasic(User user) {
        return SecurityMockMvcRequestPostProcessors.httpBasic(user.getEmail(), user.getPassword());
    }
}