package com.example.week7task.service;

import com.example.week7task.model.Comment;
import com.example.week7task.model.User;
import com.example.week7task.otherModel.CommentMapper;

import java.util.List;

public interface CommentService {
    boolean createComment(Long userId, Long postId, Comment comment);
    public List<CommentMapper> getComments(Long postId);
    boolean editComment(Long commentId, User user, Long postId, String comment);
    boolean deleteComment(Long commentId);
}
