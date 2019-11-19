package kz.epam.news.service;

import java.util.List;

public interface ServiceCRUD<E> {

    void add(E e);

    List<E> getAll();

    void deleteAll();

    void update(E e);
}
