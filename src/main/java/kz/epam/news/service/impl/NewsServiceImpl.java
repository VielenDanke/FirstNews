package kz.epam.news.service.impl;

import kz.epam.news.entity.News;
import kz.epam.news.exception.WrongFileException;
import kz.epam.news.repository.interfaces.NewsDao;
import kz.epam.news.service.interfaces.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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

    @Override
    public void uploadNewsImageFile(MultipartFile file, String pathToDirectory) throws WrongFileException {

        String imageFormatJpg = "image/jpg";
        String imageFormatJpeg = "image/jpeg";
        String imageFormatPng = "image/png";

        if (file.isEmpty()) {
            throw new WrongFileException("File doesn't exists");
        }

        if (file.getContentType().equalsIgnoreCase(imageFormatJpeg) || file.getContentType().equalsIgnoreCase(imageFormatJpg) ||
                file.getContentType().equalsIgnoreCase(imageFormatPng)) {
            try {
                byte[] bytes = file.getBytes();
                Path path = Paths.get(pathToDirectory);
                Files.write(path, bytes);
            } catch (IOException e) {
                throw new WrongFileException("Something went wrong");
            }
        } else {
            throw new WrongFileException("Wrong file format");
        }
    }
}
