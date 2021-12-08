package com.example.week7task.repository;

import com.example.week7task.model.Likes;
import com.example.week7task.model.Post;
import com.example.week7task.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface LikesRepository extends JpaRepository<Likes, Long> {
    List<Likes> findAllByPostPostId(Long postId);
    List<Likes> findAllByPostPostIdAndUserId(Long postId, Long personId);
    @Transactional
    void deleteLikesByPostAndUser(Post post, User user);
}
