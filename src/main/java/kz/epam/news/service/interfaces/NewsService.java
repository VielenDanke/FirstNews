package kz.epam.news.service.interfaces;

import kz.epam.news.entity.News;
import kz.epam.news.exception.WrongFileException;
import kz.epam.news.service.ServiceCRUD;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface NewsService<E extends News> extends ServiceCRUD<News> {

    List<E> getBySection(String section);

    List<E> getByTopicLike(String topicLike);

    List<E> getByDescriptionLike(String descriptionLike);

    E getNewsByID(Long id);

    void uploadNewsImageFile(MultipartFile file, String pathToDirectory) throws WrongFileException;
}
