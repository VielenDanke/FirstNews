package kz.epam.news.repository.interfaces;

import kz.epam.news.entity.News;
import kz.epam.news.repository.CRUDRepository;

import java.math.BigDecimal;
import java.util.List;

public interface NewsRepositoryInterface<E extends News> extends CRUDRepository<News> {

    List<E> getNewsByTopicLike(String topicLike);

    List<E> getNewsBySection(String section);

    List<E> getNewsByDescriptionLike(String descriptionLike);

    E getNewsByID(Long id);

    List<String> getSections();

    BigDecimal getNewsIdFromComments(Long id);
}
