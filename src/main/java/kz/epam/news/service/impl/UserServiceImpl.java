package kz.epam.news.service.impl;

import kz.epam.news.entity.Role;
import kz.epam.news.entity.User;
import kz.epam.news.exception.WrongDataException;
import kz.epam.news.repository.interfaces.UserRepositoryInterface;
import kz.epam.news.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService<User> {

    @Autowired
    private UserRepositoryInterface<User> userRepositoryInterface;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void add(User user) {

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        Optional<User> userFromDB = userRepositoryInterface.getUserByUsername(user.getUsername());

        if (userFromDB.isPresent()) {
            throw new WrongDataException("User exists");
        }

        user.setEnabled(1);

        if (user.getIin() != null) {
            user.setAuthority(Collections.singleton(Role.ROLE_ADMIN));
        } else {
            user.setAuthority(Collections.singleton(Role.ROLE_USER));
        }
        userRepositoryInterface.add(user);
    }

    @Override
    public List<User> getAll() {
        return userRepositoryInterface.getAll();
    }

    @Override
    @Transactional
    public void deleteAll() {
        userRepositoryInterface.deleteAll();
    }

    @Override
    public Optional<User> getUserByUsername(String username) {
        return userRepositoryInterface.getUserByUsername(username);
    }

    @Override
    public void update(User user) {
        userRepositoryInterface.update(user);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        userRepositoryInterface.deleteById(id);
    }

    @Override
    public Optional<User> getUserByID(Long id) {
        return userRepositoryInterface.getUserByID(id);
    }

    @Override
    public List<User> getUsersByUsernameLike(String usernameLike) {
        return userRepositoryInterface.getUsersByUsernameLike(usernameLike);
    }
}
