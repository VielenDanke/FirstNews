package kz.epam.news.repository.interfaces;

import kz.epam.news.entity.User;
import kz.epam.news.repository.DaoCRUD;

import java.util.List;
import java.util.Optional;

public interface UserDao<E extends User> extends DaoCRUD<User> {

    E getUserByID(Long id);

    List<E> getUsersByUsernameLike(String usernameLike);

    Optional<E> getUserByUsername(String username);
}
