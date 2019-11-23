package kz.epam.news.repository.interfaces;

import kz.epam.news.entity.User;
import kz.epam.news.repository.CRUDRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepositoryInterface<E extends User> extends CRUDRepository<User> {

    Optional<E> getUserByID(Long id);

    List<E> getUsersByUsernameLike(String usernameLike);

    Optional<E> getUserByUsername(String username);
}
