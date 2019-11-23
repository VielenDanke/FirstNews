package kz.epam.news.service.impl;

import kz.epam.news.entity.News;
import kz.epam.news.exception.WrongDataException;
import kz.epam.news.repository.interfaces.NewsDao;
import kz.epam.news.service.interfaces.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Random;

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
    @Transactional
    public void addNewsWithFile(News news, MultipartFile file) {

        if (file.isEmpty() || news.getSection() == null || news.getSection().equalsIgnoreCase("") || newsValidator(news)) {
            throw new WrongDataException("All fields should be filled");
        }

        String uniqueCodeWithFileExtension = news.getTopic() + news.getShortDescription() +
                new Random().nextInt(900) + file.getOriginalFilename();

        try {
            news.setFileInputStreamName(Base64.getEncoder().encodeToString(file.getBytes()));
        } catch (IOException e) {
            throw new WrongDataException(e.getMessage());
        }

        news.setLocalDate(LocalDate.now());
        news.setFileName(uniqueCodeWithFileExtension);
        newsDao.add(news);
    }

    @Override
    public List<News> getAll() {
        List<News> newsList = newsDao.getAll();

        if (newsList.isEmpty()) {
            newsList = new ArrayList<>();
            newsList.add(new News());

            return newsList;
        }
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

    @Override
    public List<String> getSectionList() {
        return newsDao.getSections();
    }

    @Override
    public void update(News news) {

        if (!newsValidator(news)) {
            newsDao.update(news);
        } else {
            throw new WrongDataException("All fields should be filled by update");
        }
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        newsDao.deleteById(id);
    }

    @Override
    public BigDecimal getNewsIdFromComments(Long id) {
        return newsDao.getNewsIdFromComments(id);
    }

    private boolean newsValidator(News news) {

        return news.getTopic() == null || news.getShortDescription() == null || news.getDescription() == null
                || news.getTopic().equalsIgnoreCase("") || news.getShortDescription().equalsIgnoreCase("")
                || news.getDescription().equalsIgnoreCase("");
    }
}
