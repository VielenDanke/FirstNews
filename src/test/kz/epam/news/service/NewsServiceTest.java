package kz.epam.news.service;

import kz.epam.news.config.HibernateConfig;
import kz.epam.news.config.security.SecurityConfig;
import kz.epam.news.config.web.WebAppInitializer;
import kz.epam.news.config.web.WebConfig;
import kz.epam.news.entity.News;
import kz.epam.news.exception.WrongDataException;
import kz.epam.news.repository.interfaces.NewsRepositoryInterface;
import kz.epam.news.service.impl.NewsServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.Base64;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {HibernateConfig.class, WebConfig.class, SecurityConfig.class, WebAppInitializer.class})
@WebAppConfiguration
@Transactional
public class NewsServiceTest {

    @Mock
    private NewsRepositoryInterface<News> newsRepositoryInterface;
    @Mock
    private Base64 base64;
    private News news;
    private MultipartFile file;
    @InjectMocks
    private NewsServiceImpl newsService;

    @Before
    public void setup() {
        news = new News();
        file = new MockMultipartFile("as", "as", "as", "123".getBytes());
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldAddNewsCorrect() {
        newsService.addNewsWithFile(news, file);

        Assert.assertNotNull(news.getFileInputStreamName());
        Assert.assertEquals(LocalDate.now(), news.getLocalDate());
        Assert.assertNotNull(news.getFileName());
    }
}