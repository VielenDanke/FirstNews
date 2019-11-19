package kz.epam.news.service.impl;

import kz.epam.news.entity.User;
import kz.epam.news.repository.interfaces.UserDao;
import kz.epam.news.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService<User> {

    @Autowired
    private UserDao<User> userDao;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void add(User user) {
        String passwordAfterEncode = passwordEncoder.encode(user.getPassword());
        user.setPassword(passwordAfterEncode);
        userDao.add(user);
    }

    @Override
    public List<User> getAll() {
        return userDao.getAll();
    }

    @Override
    @Transactional
    public void deleteAll() {
        userDao.deleteAll();
    }

    @Override
    public Optional<User> getUserByUsername(String username) {
        return userDao.getUserByUsername(username);
    }

    @Override
    public void update(User user) {
        userDao.update(user);
    }
}
