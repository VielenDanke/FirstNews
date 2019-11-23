package kz.epam.news.service.impl;

import kz.epam.news.entity.Comment;
import kz.epam.news.exception.WrongDataException;
import kz.epam.news.repository.interfaces.CommentDao;
import kz.epam.news.service.interfaces.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService<Comment> {

    @Autowired
    private CommentDao<Comment> commentDao;

    @Override
    @Transactional
    public void add(Comment comment) {

        if (comment.getDescriptionComment() == null || comment.getDescriptionComment().equalsIgnoreCase("")) {
            throw new WrongDataException("Comment cannot be empty");
        }
        commentDao.add(comment);
    }

    @Override
    public List<Comment> getAll() {
        return commentDao.getAll();
    }

    @Override
    @Transactional
    public void deleteAll() {
        commentDao.deleteAll();
    }

    @Override
    public List<Comment> getAllCommentsByNewsID(Long newsID) {
        return commentDao.selectAllCommentsByNewsID(newsID);
    }

    @Override
    public void update(Comment comment) {
        commentDao.update(comment);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        commentDao.deleteById(id);
    }
}
