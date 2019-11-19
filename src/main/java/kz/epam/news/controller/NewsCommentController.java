package kz.epam.news.controller;

import kz.epam.news.entity.Comment;
import kz.epam.news.entity.News;
import kz.epam.news.exception.WrongDataException;
import kz.epam.news.service.interfaces.CommentService;
import kz.epam.news.service.interfaces.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.security.Principal;
import java.util.*;

@Controller
@SessionAttributes({"allNews", "lastNews"})
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
        News news = allNews.get(allNews.size() - 1);

        ModelAndView modelAndView = new ModelAndView("index");
        modelAndView.addObject("allNews", allNews);
        modelAndView.addObject("lastNews", news);

        return modelAndView;
    }

    @RequestMapping("/add")
    public String addNews(Model model) {
        model.addAttribute("sectionList", newsServiceInterface.getSectionList());
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

        try {
            news.setFileInputStreamName(Base64.getEncoder().encodeToString(file.getBytes()));
        } catch (IOException e) {
            throw new WrongDataException(e.getMessage());
        }

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
    public ModelAndView getAllComments(@RequestParam("id") Long id, Principal principal) {

        News news = newsServiceInterface.getNewsByID(id);

        List<Comment> comments = commentServiceInterface.getAllCommentsByNewsID(id);

        ModelAndView modelAndView = new ModelAndView("comments");
        modelAndView.addObject("username", principal.getName());
        modelAndView.addObject("comments_to_news", comments);
        modelAndView.addObject("news_by_id", news);

        return modelAndView;
    }

    @GetMapping("/searching_by")
    public ModelAndView startSearchingByUserDecision(@RequestParam("radio") String radio, @RequestParam("search") String search) {

        ModelAndView modelAndView = new ModelAndView("index");

        switch (radio) {
            case "Topic":
                modelAndView.addObject("allNews", newsServiceInterface.getByTopicLike(search));
                return modelAndView;
            case "Description":
                modelAndView.addObject("allNews", newsServiceInterface.getByDescriptionLike(search));
                return modelAndView;
            default:
                throw new WrongDataException("Not a valid searching method");
        }
    }

    @PostMapping("/{id}")
    public String updateNews(@PathVariable("id") Long id, @ModelAttribute("news") News news) {
        news.setId(id);
        newsServiceInterface.update(news);
        return "redirect:/";
    }

    @PostMapping("/delete/{id}")
    public String deleteNews(@PathVariable("id") Long id) {
        newsServiceInterface.deleteById(id);
        return "redirect:/";
    }

    @PostMapping("/delete/comment/{id}")
    public String deleteComment(@PathVariable("id") Long id) {
        commentServiceInterface.deleteById(id);
        return "comments";
    }
}