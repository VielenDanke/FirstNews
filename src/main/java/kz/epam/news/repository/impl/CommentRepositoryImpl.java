package kz.epam.news.repository.impl;

import kz.epam.news.entity.Comment;
import kz.epam.news.repository.interfaces.CommentDao;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

import static kz.epam.news.config.HibernateConfig.CACHEABLE;
import static kz.epam.news.config.HibernateConfig.CACHEABLE_FLAG;

@Repository
@SuppressWarnings("unchecked")
public class CommentRepositoryImpl implements CommentDao<Comment> {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void add(Comment comment) {
        entityManager.persist(comment);
    }

    @Override
    public List<Comment> getAll() {
        return entityManager.createQuery("from Comment").setHint(CACHEABLE, CACHEABLE_FLAG).getResultList();
    }

    @Override
    public void deleteAll() {
        entityManager.createQuery("delete from Comment").setHint(CACHEABLE, CACHEABLE_FLAG).executeUpdate();
    }

    @Override
    public List<Comment> selectAllCommentsByNewsID(Long newsID) {
        Query query = entityManager.createQuery("select c from Comment c where c.newsID=:newsID").setHint(CACHEABLE, CACHEABLE_FLAG);
        query.setParameter("newsID", newsID);
        return query.getResultList();
    }

    @Override
    public void update(Comment comment) {
        entityManager.merge(comment);
    }

    @Override
    public void deleteById(Long id) {
        entityManager.createNamedQuery("Comment.deleteById", Comment.class).setParameter("id", id).executeUpdate();
    }
}
