package kz.epam.news.service.interfaces;

import kz.epam.news.entity.Comment;
import kz.epam.news.service.ServiceCRUD;

import java.util.List;

public interface CommentService<E extends Comment> extends ServiceCRUD<E> {

    List<Comment> getAllCommentsByNewsID(Long newsID);
}
