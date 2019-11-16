package kz.epam.news.repository.interfaces;

import kz.epam.news.entity.Comment;
import kz.epam.news.repository.DaoCRUD;

import java.util.List;

public interface CommentDao<E extends Comment> extends DaoCRUD<E> {

    List<Comment> selectAllCommentsByNewsID(Long newsID);
}
