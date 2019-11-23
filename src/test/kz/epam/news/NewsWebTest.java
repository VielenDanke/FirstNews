package kz.epam.news;

import kz.epam.news.config.HibernateConfig;
import kz.epam.news.config.security.SecurityConfig;
import kz.epam.news.config.web.WebAppInitializer;
import kz.epam.news.config.web.WebConfig;
import kz.epam.news.entity.Comment;
import kz.epam.news.entity.News;
import kz.epam.news.entity.User;
import kz.epam.news.service.interfaces.NewsService;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;

import javax.servlet.ServletContext;
import javax.servlet.http.Part;
import javax.transaction.Transactional;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Base64;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {HibernateConfig.class, WebConfig.class, SecurityConfig.class, WebAppInitializer.class})
@WebAppConfiguration
@Transactional
public class NewsWebTest {

    @Autowired
    private WebApplicationContext webApplicationContext;
    @Autowired
    private NewsService<News> newsService;
    private MockMvc mockMvc;
    private Comment comment;
    private News news;
    private MockMultipartFile mockMultipartFile;

    @Before
    public void setup() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
        comment = new Comment();
        news = new News();
        //C:/Users/Danke/Desktop/FirstNews/webapp/assets/img
        //C:/Users/Vladislav_Dankevich/IdeaProjects/FirstNews/webapp/assets/img
        Path path = Paths.get("C:/Users/Danke/Desktop/FirstNews/webapp/assets/img/breaking_news.png");
        mockMultipartFile = new MockMultipartFile("breaking_news.png", "breaking_news.png", "image/png", Files.readAllBytes(path));

        news = new News();
        news.setSection("testing");
        news.setTopic("testing");
        news.setShortDescription("testing");
        news.setDescription("testing");

        comment.setAuthorName("testing");
        comment.setDescriptionComment("testing");
    }

    @Test
    public void configTest() {
        ServletContext servletContext = webApplicationContext.getServletContext();

        Assert.assertNotNull(servletContext);
        Assert.assertTrue(servletContext instanceof MockServletContext);
        Assert.assertNotNull(webApplicationContext.getBean("userController"));
        Assert.assertNotNull(webApplicationContext.getBean("newsCommentController"));
    }

    @Test
    public void getMethodShouldReturnCorrectView() throws Exception {
        this.mockMvc.perform(get("/registration")).andDo(print())
                .andExpect(view().name("registration"));

        this.mockMvc.perform(get("/add_admin")).andDo(print())
                .andExpect(view().name("add_admin"));

        this.mockMvc.perform(get("/login")).andDo(print())
                .andExpect(view().name("login"));

        this.mockMvc.perform(get("/add")).andDo(print())
                .andExpect(view().name("sample"));
    }

    @Test
    public void getAllItemsAndRedirectToMainPage() throws Exception {
        this.mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"));
    }

    @Test
    public void shouldReturnSectionByParameter() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.post("/section").param("section", "sport"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"));
    }

    @Test
    @Rollback
    public void shouldRedirectAfterAddNotEmptyComment() throws Exception {
        newsService.add(news);
        comment.setNewsID(news.getId());

        this.mockMvc.perform(post("/add_comment").flashAttr("comment", comment))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @Rollback
    public void shouldAddFileToDatabaseByUsingBase64EncoderAndAddNews() throws Exception {
        MockHttpServletRequestBuilder multipart = multipart("/save")
                .file("file", mockMultipartFile.getBytes())
                .flashAttr("news", news);

        this.mockMvc.perform(multipart)
                .andExpect(status().isOk())
                .andExpect(view().name("sample"));
    }
}
