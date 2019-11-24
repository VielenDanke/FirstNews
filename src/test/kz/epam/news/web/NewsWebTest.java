package kz.epam.news.web;

import kz.epam.news.config.HibernateConfig;
import kz.epam.news.config.security.SecurityConfig;
import kz.epam.news.config.web.WebAppInitializer;
import kz.epam.news.config.web.WebConfig;
import kz.epam.news.entity.Comment;
import kz.epam.news.entity.News;
import kz.epam.news.entity.User;
import kz.epam.news.service.interfaces.CommentService;
import kz.epam.news.service.interfaces.NewsService;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
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
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {HibernateConfig.class, WebConfig.class, SecurityConfig.class, WebAppInitializer.class})
@WebAppConfiguration
@Transactional
public class NewsWebTest {

    @Mock
    private NewsService<News> newsService;
    @Mock
    private CommentService<Comment> commentService;
    @InjectMocks
    private NewsCommentController newsCommentController;
    private MockMvc mockMvc;

    private Comment comment;
    private News news;
    private MockMultipartFile mockMultipartFile;

    @Before
    public void setup() throws Exception {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(newsCommentController).build();
        comment = new Comment();
        news = new News();
        mockMultipartFile = new MockMultipartFile("a", "a", "a", "123".getBytes());
    }

    @Test
    public void getMethodShouldReturnCorrectView() throws Exception {
        this.mockMvc.perform(get("/add")).andDo(print())
                .andExpect(view().name("sample"));
    }

    @Test
    public void getAllItemsAndRedirectToMainPage() throws Exception {
        Mockito.when(newsService.getAll()).thenReturn(new ArrayList<>(Arrays.asList(news)));

        this.mockMvc.perform(get("/"))
                .andExpect(model().attributeExists("allNews", "lastNews"))
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
    public void shouldRedirectAfterAddNotEmptyComment() throws Exception {
        BigDecimal bigDecimal = new BigDecimal(1);

        MockHttpServletRequestBuilder postBuilder = post("/add_comment")
                .flashAttr("comment", comment);

        Mockito.when(newsService.getNewsIdFromComments(comment.getId())).thenReturn(bigDecimal);
        Mockito.when(newsService.getNewsByID(bigDecimal.longValue())).thenReturn(new News());

        this.mockMvc.perform(postBuilder).andExpect(status().is3xxRedirection());
    }

    @Test
    public void shouldAddFileToDatabaseByUsingBase64EncoderAndAddNews() throws Exception {
        MockHttpServletRequestBuilder multipart = multipart("/save")
                .file("file", mockMultipartFile.getBytes())
                .flashAttr("news", news);

        this.mockMvc.perform(multipart)
                .andExpect(status().isOk())
                .andExpect(view().name("sample"));
    }

    @Test
    public void shouldReturnAllComments() throws Exception {
        Long id = (long) 1;
        MockHttpServletRequestBuilder getBuilder = get("/comments")
                .param("id", "1")
                .principal(() -> "Oleg");

        Mockito.when(newsService.getNewsByID(id)).thenReturn(news);
        Mockito.when(commentService.getAllCommentsByNewsID(id)).thenReturn(new ArrayList<>(Arrays.asList(comment)));

        this.mockMvc.perform(getBuilder)
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("username", "comments_to_news", "news_by_id"))
                .andExpect(view().name("all_comments"));
    }

    @Test
    public void shouldReturnMainPageWithCurrentSectionList() throws Exception {

        MockHttpServletRequestBuilder getBuilder = get("/searching_by")
                .param("radio", "Topic")
                .param("search", "search");

        Mockito.when(newsService.getByTopicLike("search")).thenReturn(Arrays.asList(news));
        Mockito.when(newsService.getByDescriptionLike("search")).thenReturn(Arrays.asList(news));

        this.mockMvc.perform(getBuilder)
                .andExpect(model().attributeExists("allNews"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"));
    }
}