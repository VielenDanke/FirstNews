package kz.epam.news.service.impl;

import kz.epam.news.entity.News;
import kz.epam.news.exception.WrongDataException;
import kz.epam.news.repository.interfaces.NewsRepositoryInterface;
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
    private NewsRepositoryInterface<News> newsRepositoryInterface;

    @Override
    @Transactional
    public void add(News news) {
        newsRepositoryInterface.add(news);
    }

    @Override
    @Transactional
    public void addNewsWithFile(News news, MultipartFile file) {

        String uniqueCodeWithFileExtension = news.getTopic() + news.getShortDescription() +
                new Random().nextInt(900) + file.getOriginalFilename();

        try {
            news.setFileInputStreamName(Base64.getEncoder().encodeToString(file.getBytes()));
        } catch (IOException e) {
            throw new WrongDataException("error.file.input.output");
        }

        news.setLocalDate(LocalDate.now());
        news.setFileName(uniqueCodeWithFileExtension);
        newsRepositoryInterface.add(news);
    }

    @Override
    public List<News> getAll() {
        List<News> newsList = newsRepositoryInterface.getAll();

        if (newsList.isEmpty()) {
            newsList = new ArrayList<>();
            newsList.add(new News());

            return newsList;
        }
        return newsRepositoryInterface.getAll();
    }

    @Override
    @Transactional
    public void deleteAll() {
        newsRepositoryInterface.deleteAll();
    }

    @Override
    public List<News> getBySection(String section) {
        return newsRepositoryInterface.getNewsBySection(section);
    }

    @Override
    public List<News> getByTopicLike(String topic) {
        return newsRepositoryInterface.getNewsByTopicLike(topic);
    }

    @Override
    public List<News> getByDescriptionLike(String description) {
        return newsRepositoryInterface.getNewsByDescriptionLike(description);
    }

    @Override
    public News getNewsByID(Long id) {
        return newsRepositoryInterface.getNewsByID(id);
    }

    @Override
    public List<String> getSectionList() {
        return newsRepositoryInterface.getSections();
    }

    @Override
    public void update(News news) {

        if (!news.getTopic().isEmpty() && !news.getShortDescription().isEmpty() && !news.getDescription().isEmpty()) {
            newsRepositoryInterface.update(news);
        } else {
            throw new WrongDataException("error.all.fields.should.be.filled");
        }
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        newsRepositoryInterface.deleteById(id);
    }

    @Override
    public BigDecimal getNewsIdFromComments(Long id) {
        return newsRepositoryInterface.getNewsIdFromComments(id);
    }
}
