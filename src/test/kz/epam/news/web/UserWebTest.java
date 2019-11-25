package kz.epam.news.web;

import kz.epam.news.config.HibernateConfig;
import kz.epam.news.config.security.SecurityConfig;
import kz.epam.news.config.web.WebAppInitializer;
import kz.epam.news.config.web.WebConfig;
import kz.epam.news.entity.User;
import kz.epam.news.service.interfaces.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.transaction.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import java.util.StringJoiner;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {HibernateConfig.class, WebConfig.class, SecurityConfig.class, WebAppInitializer.class})
@WebAppConfiguration
@Transactional
public class UserWebTest {

    @Mock
    private UserService<User> userService;
    @InjectMocks
    private UserController userController;
    private MockMvc mockMvc;
    private User user;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(userController).build();

        user = new User();
    }

    @Test
    public void shouldAddUserToDatabaseWithRedirect() throws Exception {
        MockHttpServletRequestBuilder postBuilder = post("/add_user")
                .flashAttr("user", user);

        this.mockMvc.perform(postBuilder)
                .andExpect(status().is3xxRedirection());
    }

    @Test
    public void shouldAddAdminToDatabaseWithRedirect() throws Exception {
        MockHttpServletRequestBuilder postBuilder = post("/add_admin")
                .flashAttr("user", user);

        this.mockMvc.perform(postBuilder)
                .andExpect(status().is3xxRedirection());
    }

    @Test
    public void shouldDeleteUserById() throws Exception {
        MockHttpServletRequestBuilder postBuilder = post("/delete/user/{id}", any(Long.TYPE));

        this.mockMvc.perform(postBuilder)
                .andExpect(status().is3xxRedirection());
    }

    @Test
    public void shouldReturnListOfUsersBySearching() throws Exception {
        MockHttpServletRequestBuilder getBuilder = get("/user/searching_by")
                .param("radio", "Username")
                .param("search", "search");

        Mockito.when(userService.getUserByID(any(Long.TYPE))).thenReturn(Optional.of(user));
        Mockito.when(userService.getUsersByUsernameLike("search")).thenReturn(new ArrayList<>(Arrays.asList(user)));

        this.mockMvc.perform(getBuilder)
                .andExpect(model().attribute("userList", Arrays.asList(user)))
                .andExpect(status().isOk())
                .andExpect(view().name("users"));
    }
}
