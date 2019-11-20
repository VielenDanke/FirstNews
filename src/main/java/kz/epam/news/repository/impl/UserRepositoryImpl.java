package kz.epam.news.repository.impl;

import kz.epam.news.entity.User;
import kz.epam.news.repository.interfaces.UserDao;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;
import java.util.Optional;

@Repository
@SuppressWarnings("unchecked")
public class UserRepositoryImpl implements UserDao<User> {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void add(User user) {
        entityManager.persist(user);
    }

    @Override
    public List<User> getAll() {
        return entityManager.createQuery("from User").getResultList();
    }

    @Override
    public void deleteAll() {
        entityManager.createQuery("delete from User").executeUpdate();
    }

    @Override
    public Optional<User> getUserByID(Long id) {
        return entityManager.createQuery("select u from User u where u.id=:id")
                .setParameter("id", id)
                .setMaxResults(1)
                .getResultList()
                .stream()
                .findFirst();

    }

    @Override
    public List<User> getUsersByUsernameLike(String usernameLike) {
        Query query = entityManager.createQuery("select u from User u where upper(u.username) like concat('%',:usernameLike,'%')");
        query.setParameter("usernameLike", usernameLike.toUpperCase());
        return query.getResultList();
    }

    @Override
    public Optional<User> getUserByUsername(String username) {

        return entityManager.createQuery("select u from User u where u.username=:username")
                .setParameter("username", username)
                .setMaxResults(1)
                .getResultList()
                .stream()
                .findFirst();
    }

    @Override
    public void update(User user) {
        entityManager.merge(user);
    }

    @Override
    public void deleteById(Long id) {
        entityManager.createQuery("delete from User u where u.id=:id").setParameter("id", id).executeUpdate();
    }
}
