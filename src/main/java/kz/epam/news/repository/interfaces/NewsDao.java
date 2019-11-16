package kz.epam.news.repository.interfaces;

import kz.epam.news.entity.News;
import kz.epam.news.repository.DaoCRUD;

import java.util.List;

public interface NewsDao<E extends News> extends DaoCRUD<News> {

    List<E> getNewsByTopicLike(String topicLike);

    List<E> getNewsBySection(String section);

    List<E> getNewsByDescriptionLike(String descriptionLike);

    E getNewsByID(Long id);
}
