package kz.epam.news.service.impl;

import kz.epam.news.entity.Comment;
import kz.epam.news.exception.WrongDataException;
import kz.epam.news.repository.interfaces.CommentRepositoryInterface;
import kz.epam.news.service.interfaces.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService<Comment> {

    @Autowired
    private CommentRepositoryInterface<Comment> commentRepositoryInterface;

    @Override
    @Transactional
    public void add(Comment comment) {

        if (comment.getDescriptionComment() == null || comment.getDescriptionComment().equalsIgnoreCase("")) {
            throw new WrongDataException("error.comment.not.empty");
        }
        commentRepositoryInterface.add(comment);
    }

    @Override
    public List<Comment> getAll() {
        return commentRepositoryInterface.getAll();
    }

    @Override
    @Transactional
    public void deleteAll() {
        commentRepositoryInterface.deleteAll();
    }

    @Override
    public List<Comment> getAllCommentsByNewsID(Long newsID) {
        return commentRepositoryInterface.selectAllCommentsByNewsID(newsID);
    }

    @Override
    public void update(Comment comment) {
        commentRepositoryInterface.update(comment);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        commentRepositoryInterface.deleteById(id);
    }
}
