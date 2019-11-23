package kz.epam.news.controller;

import kz.epam.news.entity.Role;
import kz.epam.news.entity.User;
import kz.epam.news.exception.WrongDataException;
import kz.epam.news.service.interfaces.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;
import java.util.stream.Collectors;

@Controller
public class UserController {

    @Autowired
    private UserService<User> userServiceInterface;

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
    public String registration(@ModelAttribute("user") User user) {

        user.setAuthority(Collections.singleton(Role.ROLE_USER));

        userServiceInterface.add(user);

        return "redirect:/";
    }

    @RequestMapping(value = "/add_admin", method = RequestMethod.POST)
    public String addAdmin(@ModelAttribute("user") User user) {

        user.setAuthority(Collections.singleton(Role.ROLE_ADMIN));

        userServiceInterface.add(user);

        return "redirect:/";
    }

    @PostMapping("/delete/user/{id}")
    public String deleteUserById(@PathVariable("id") Long id) {
        userServiceInterface.deleteById(id);
        return "redirect:/users";
    }

    @GetMapping("/users")
    public String toUserPage(Model model) {
        model.addAttribute("userList", userServiceInterface.getAll());
        return "users";
    }

    @GetMapping("/user/searching_by")
    public ModelAndView startSearchingByUserDecision(@RequestParam("radio") String radio, @RequestParam("search") String search) {

        ModelAndView modelAndView = new ModelAndView("users");

        switch (radio) {
            case "ID":
                StringJoiner stringJoiner = new StringJoiner("");

                String regex = "\\d+";

                for (Character c : search.toCharArray()) {
                    if (c.toString().matches(regex)) {
                        stringJoiner.add(c.toString());
                    }
                }

                String numbersFromJoiner = stringJoiner.toString();

                if (numbersFromJoiner.isEmpty()) {
                    modelAndView.addObject("error", "Insert numbers");
                    return modelAndView;
                }

                Optional<User> user = userServiceInterface.getUserByID(Long.parseLong(numbersFromJoiner));

                if (!user.isPresent()) {
                    modelAndView.addObject("error", "User not found");
                    return modelAndView;
                }
                modelAndView.addObject("userList", Arrays.asList(user.get()));
                return modelAndView;
            case "Username":
                modelAndView.addObject("userList", userServiceInterface.getUsersByUsernameLike(search));
                return modelAndView;
            default:
                throw new WrongDataException("Not a valid searching method");
        }
    }
}
