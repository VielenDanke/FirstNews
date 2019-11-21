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
import java.math.BigDecimal;
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

        if (file.getContentType().equalsIgnoreCase("image/jpeg") || file.getContentType().equalsIgnoreCase("image/jpg")
                || file.getContentType().equalsIgnoreCase("image/png")) {

            if (file.isEmpty() || news.getSection() == null || news.getSection().equalsIgnoreCase("") || newsValidator(news)) {
                throw new WrongDataException("All fields should be filled");
            }

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
        } else {
            throw new WrongDataException("Unsupported file format: " + file.getContentType());
        }
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

        if (comment.getDescriptionComment() == null || comment.getDescriptionComment().equalsIgnoreCase("")) {
            throw new WrongDataException("Comment cannot be empty");
        }

        commentServiceInterface.add(comment);

        return "redirect:/comments" + getStringToRedirect(comment.getId());
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

        if (!newsValidator(news)) {
            newsServiceInterface.update(news);
            return "redirect:/";
        } else {
            throw new WrongDataException("All fields should be filled by update");
        }
    }

    @PostMapping("/delete/{id}")
    public String deleteNews(@PathVariable("id") Long id) {
        newsServiceInterface.deleteById(id);
        return "redirect:/";
    }

    @PostMapping("/delete/comment/{id}")
    public String deleteComment(@PathVariable("id") Long id) {

        String toRedirect = getStringToRedirect(id);

        commentServiceInterface.deleteById(id);

        return "redirect:/comments" + toRedirect;
    }

    private String getStringToRedirect(Long id) {

        BigDecimal bigDecimal = newsServiceInterface.getNewsIdFromComments(id);
        News news = newsServiceInterface.getNewsByID(bigDecimal.longValue());

        return "?id=" + news.getId() + "&section=" + news.getSection();
    }

    private boolean newsValidator(News news) {

        return news.getTopic() == null || news.getShortDescription() == null || news.getDescription() == null
                || news.getTopic().equalsIgnoreCase("") || news.getShortDescription().equalsIgnoreCase("")
                || news.getDescription().equalsIgnoreCase("");
    }
}