package kz.epam.news.service.impl;

import kz.epam.news.entity.News;
import kz.epam.news.repository.interfaces.NewsDao;
import kz.epam.news.service.interfaces.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class NewsServiceImpl implements NewsService<News> {

    @Autowired
    private NewsDao<News> newsDao;

    @Override
    @Transactional
    public void add(News news) {
        newsDao.add(news);
    }

    @Override
    public List<News> getAll() {
        return newsDao.getAll();
    }

    @Override
    @Transactional
    public void deleteAll() {
        newsDao.deleteAll();
    }

    @Override
    public List<News> getBySection(String section) {
        return newsDao.getNewsBySection(section);
    }

    @Override
    public List<News> getByTopicLike(String topic) {
        return newsDao.getNewsByTopicLike(topic);
    }

    @Override
    public List<News> getByDescriptionLike(String description) {
        return newsDao.getNewsByDescriptionLike(description);
    }

    @Override
    public News getNewsByID(Long id) {
        return newsDao.getNewsByID(id);
    }
}
