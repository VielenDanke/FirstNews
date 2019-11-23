package kz.epam.news.repository.interfaces;

import kz.epam.news.entity.Comment;
import kz.epam.news.repository.CRUDRepository;

import java.util.List;

public interface CommentRepositoryInterface<E extends Comment> extends CRUDRepository<E> {

    List<Comment> selectAllCommentsByNewsID(Long newsID);
}
