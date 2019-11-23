package kz.epam.news.repository;

import kz.epam.news.config.HibernateConfig;
import kz.epam.news.config.security.SecurityConfig;
import kz.epam.news.config.web.WebAppInitializer;
import kz.epam.news.config.web.WebConfig;
import kz.epam.news.entity.News;
import kz.epam.news.entity.User;
import kz.epam.news.repository.interfaces.NewsRepositoryInterface;
import kz.epam.news.repository.interfaces.UserRepositoryInterface;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {HibernateConfig.class, WebConfig.class, SecurityConfig.class, WebAppInitializer.class})
@WebAppConfiguration
@Transactional
public class RepositoryTest {

    @Autowired
    private UserRepositoryInterface<User> userRepositoryInterface;
    @Autowired
    private NewsRepositoryInterface<News> newsRepositoryInterface;
    @Autowired
    private Logger logger;

    private User user;
    private News news;
    private List<News> newsArrayList;

    @Before
    public void setup() {
        user = new User();

        user.setUsername("testing");
        user.setPassword("testing");
        user.setEnabled(1);

        news = new News();
        news.setFileInputStreamName("testing");
        news.setSection("testing");
        news.setTopic("testing");
        news.setShortDescription("testing");
        news.setDescription("testing");
        news.setFileName("testing");
        news.setLocalDate(LocalDate.now());

        newsArrayList = new ArrayList<>();
        newsArrayList.add(news);
    }

    @Test
    @Rollback
    public void userServiceInterfaceMethodTestingShouldReturnNotNullValues() {
        logger.info("*****STARTING******");
        userRepositoryInterface.add(user);
        Assert.assertNotNull(userRepositoryInterface.getUserByID(user.getId()));
        Assert.assertNotNull(userRepositoryInterface.getUserByUsername("testing"));
        Assert.assertNotNull(userRepositoryInterface.getUsersByUsernameLike("est"));
        logger.info("*****FINISHING******");
    }

    @Test
    @Rollback
    public void newsServiceInterfaceTestingShouldReturnNotNullValues() {
        logger.info("*****STARTING******");
        newsRepositoryInterface.add(news);
        Assert.assertNotNull(newsRepositoryInterface.getNewsBySection("testing"));
        Assert.assertEquals(newsArrayList, newsRepositoryInterface.getNewsByTopicLike("est"));
        Assert.assertNotNull(newsRepositoryInterface.getNewsByDescriptionLike("est"));
        Assert.assertNotNull(newsRepositoryInterface.getNewsByID(news.getId()));
        logger.info("*****FINISHING******");
    }
}
