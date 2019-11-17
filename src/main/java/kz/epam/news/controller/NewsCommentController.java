package kz.epam.news.controller;

import kz.epam.news.entity.Comment;
import kz.epam.news.entity.News;
import kz.epam.news.service.interfaces.CommentService;
import kz.epam.news.service.interfaces.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.List;
import java.util.Random;

@Controller
@SessionAttributes("allNews")
public class NewsCommentController {

    @Autowired
    private NewsService<News> newsServiceInterface;

    @Autowired
    private CommentService<Comment> commentServiceInterface;

    @ModelAttribute("news")
    public News news() {
        return new News();
    }

    @ModelAttribute("comment")
    public Comment comment() {
        return new Comment();
    }

    @RequestMapping("/")
    public ModelAndView displayAllNews() {

        List<News> allNews = newsServiceInterface.getAll();

        ModelAndView modelAndView = new ModelAndView("index");
        modelAndView.addObject("allNews", allNews);

        return modelAndView;
    }

    @RequestMapping("/add")
    public String addNews() {
        return "sample";
    }

    @RequestMapping("/error")
    public String getErrorPage() {
        return "error";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String addNews(@ModelAttribute("news") News news, @RequestParam("file") MultipartFile file, Model model) {

        String uniqueCodeWithFileExtension = news.getTopic() + news.getShortDescription() +
                new Random().nextInt(900) + file.getOriginalFilename();

        String pathToDirectory = "C:/Users/Danke/Desktop/newsImages/image/" + uniqueCodeWithFileExtension;

        newsServiceInterface.uploadNewsImageFile(file, pathToDirectory);

        news.setFileName(uniqueCodeWithFileExtension);

        newsServiceInterface.add(news);

        model.addAttribute("errorInFile", "File successfully uploaded");

        return "sample";
    }

    @RequestMapping("/section")
    public ModelAndView getNewsBySection(@RequestParam String section) {

        List<News> newsBySection = newsServiceInterface.getBySection(section);

        ModelAndView modelAndView = new ModelAndView("index");
        modelAndView.addObject("allNews", newsBySection);

        return modelAndView;
    }

    @RequestMapping(value = "/add_comment", method = RequestMethod.POST)
    public String addComment(@ModelAttribute("comment") Comment comment) {

        commentServiceInterface.add(comment);

        return "redirect:/";
    }

    @RequestMapping("/comments")
    public ModelAndView getAllComments(@RequestParam("id") Long id) {

        News news = newsServiceInterface.getNewsByID(id);

        List<Comment> comments = commentServiceInterface.getAllCommentsByNewsID(id);

        ModelAndView modelAndView = new ModelAndView("comments");
        modelAndView.addObject("comments_to_news", comments);
        modelAndView.addObject("news_by_id", news);

        return modelAndView;
    }
}
