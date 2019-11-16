package kz.epam.news.service.interfaces;

import kz.epam.news.entity.User;
import kz.epam.news.service.ServiceCRUD;

import java.util.Optional;

public interface UserService<E extends User> extends ServiceCRUD<User> {

    Optional<E> getUserByUsername(String username);
}
