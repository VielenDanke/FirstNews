package kz.epam.news.repository;

import java.util.List;

public interface DaoCRUD<E> {

    void add(E e);

    List<E> getAll();

    void deleteAll();

    void update(E e);

    void deleteById(Long id);
}
