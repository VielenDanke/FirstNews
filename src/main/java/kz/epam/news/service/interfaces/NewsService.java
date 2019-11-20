package kz.epam.news.service.interfaces;

import kz.epam.news.entity.News;
import kz.epam.news.service.ServiceCRUD;

import java.math.BigDecimal;
import java.util.List;

public interface NewsService<E extends News> extends ServiceCRUD<News> {

    List<E> getBySection(String section);

    List<E> getByTopicLike(String topicLike);

    List<E> getByDescriptionLike(String descriptionLike);

    E getNewsByID(Long id);

    List<String> getSectionList();

    BigDecimal getNewsIdFromComments(Long id);
}
