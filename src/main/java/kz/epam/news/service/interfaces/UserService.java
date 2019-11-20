package kz.epam.news.service.interfaces;

import kz.epam.news.entity.User;
import kz.epam.news.service.ServiceCRUD;

import java.util.List;
import java.util.Optional;

public interface UserService<E extends User> extends ServiceCRUD<User> {

    List<E> getUserByID(Long id);

    List<E> getUsersByUsernameLike(String usernameLike);

    Optional<E> getUserByUsername(String username);
}
