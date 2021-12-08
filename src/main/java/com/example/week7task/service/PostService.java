package com.example.week7task.service;

import com.example.week7task.model.Post;
import com.example.week7task.model.User;
import com.example.week7task.otherModel.PostMapper;

import java.util.List;

public interface PostService {
    boolean createPost(Long userId, Post post);
    List<PostMapper> getPost(User currentUser);
}
