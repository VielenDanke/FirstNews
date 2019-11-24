package kz.epam.news.service;

import kz.epam.news.config.HibernateConfig;
import kz.epam.news.config.security.SecurityConfig;
import kz.epam.news.config.web.WebAppInitializer;
import kz.epam.news.config.web.WebConfig;
import kz.epam.news.entity.User;
import kz.epam.news.repository.interfaces.UserRepositoryInterface;
import kz.epam.news.service.impl.UserServiceImpl;
import kz.epam.news.service.interfaces.UserService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.transaction.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {HibernateConfig.class, WebConfig.class, SecurityConfig.class, WebAppInitializer.class})
@WebAppConfiguration
@Transactional
public class UserServiceTest {

    @Mock
    private UserRepositoryInterface<User> userUserRepositoryInterface;
    @Mock
    private PasswordEncoder passwordEncoder;
    @InjectMocks
    private UserServiceImpl userService;
    private User user;

    @Before
    public void setup() {
        user = new User();
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void userServiceAddMethodTest() {
        userService.add(user);

        Assert.assertEquals(Integer.valueOf(1), user.getEnabled());
        Assert.assertNotNull(user.getAuthority());
    }
}