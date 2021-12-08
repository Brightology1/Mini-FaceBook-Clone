package com.example.week7task.service;

import com.example.week7task.model.User;

public interface LikeService {
    public boolean likePost(User user, Long postId, String action);
}
