package kz.epam.news.repository.impl;

import kz.epam.news.entity.News;
import kz.epam.news.repository.interfaces.NewsDao;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

import static kz.epam.news.config.HibernateConfig.CACHEABLE;
import static kz.epam.news.config.HibernateConfig.CACHEABLE_FLAG;

@Repository
@SuppressWarnings("unchecked")
public class NewsRepositoryImpl implements NewsDao<News> {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void add(News news) {
        entityManager.persist(news);
    }

    @Override
    public List<News> getAll() {
        return entityManager.createQuery("from News").getResultList();
    }

    @Override
    public void deleteAll() {
        entityManager.createQuery("delete from News").executeUpdate();
    }

    @Override
    public List<News> getNewsByTopicLike(String topicLike) {
        Query query = entityManager.createQuery("select n from News n where n.topic like concat('%',:topic,'%')").setHint(CACHEABLE, CACHEABLE_FLAG);
        query.setParameter("topic", topicLike);
        return query.getResultList();
    }

    @Override
    public List<News> getNewsBySection(String section) {
        return entityManager.createNamedQuery("News.findBySection").setParameter("section", section).setHint(CACHEABLE, CACHEABLE_FLAG).getResultList();
    }

    @Override
    public List<News> getNewsByDescriptionLike(String descriptionLike) {
        Query query = entityManager.createQuery("select n from News n where n.description like concat('%',:descriptionLike,'%')").setHint(CACHEABLE, CACHEABLE_FLAG);
        query.setParameter("descriptionLike", descriptionLike);
        return query.getResultList();
    }

    @Override
    public News getNewsByID(Long id) {
        Query query = entityManager.createNativeQuery("select n.ID, n.SECTION, n.TOPIC, n.DESCRIPTION, n.SHORT_DESCRIPTION, n.FILE_NAME, n.FILE_INPUT_STREAM_NAME, n.LOCAL_DATE from News n where n.ID=:id", News.class).setHint(CACHEABLE, CACHEABLE_FLAG);
        query.setParameter("id", id);
        return (News) query.getSingleResult();
    }

    @Override
    @Transactional
    public void update(News news) {
        entityManager.createQuery("update News n set n.topic=:topic, n.description=:description, n.shortDescription=:shortDescription where n.id=:id")
        .setParameter("topic", news.getTopic())
        .setParameter("description", news.getDescription())
        .setParameter("shortDescription", news.getShortDescription())
        .setParameter("id", news.getId())
        .executeUpdate();
    }

    @Override
    public List<String> getSections() {
        return entityManager.createNativeQuery("select * from SECTIONS").getResultList();
    }
}
