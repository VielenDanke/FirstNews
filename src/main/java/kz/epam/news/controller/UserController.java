package kz.epam.news.controller;

import kz.epam.news.entity.Role;
import kz.epam.news.entity.User;
import kz.epam.news.service.interfaces.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Optional;

@Controller
public class UserController {

    @Autowired
    private UserService<User> userServiceInterface;
    @Autowired
    private Logger logger;

    @ModelAttribute("user")
    public User user() {
        return new User();
    }

    @RequestMapping("/login")
    public String getLoginForm() {
        return "login";
    }

    @RequestMapping("/registration")
    public String getRegistrationForm() {
        return "registration";
    }

    @RequestMapping("/add_admin")
    public String goToAddAdmin() {
        return "add_admin";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(@ModelAttribute("user") User user) {
       return "redirect:/";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String registration(@ModelAttribute("user") User user, Model model) {

        return saveUser(user, Role.ROLE_USER, model);
    }

    @RequestMapping(value = "/add_admin", method = RequestMethod.POST)
    public String addAdmin(@ModelAttribute("user") User user, Model model) {

        return saveUser(user, Role.ROLE_ADMIN, model);
    }

    private String saveUser(User user, Role role, Model model) {

        Optional<User> userFromDB = userServiceInterface.getUserByUsername(user.getUsername());

        if (userFromDB.isPresent()) {
            logger.info("User exists");
            model.addAttribute("error", "User exists");
            return "registration";
        }
        logger.info("Creating new user");
        user.setEnabled(1);
        user.setAuthority(Collections.singleton(role));
        userServiceInterface.add(user);
        return "redirect:/";
    }
}
