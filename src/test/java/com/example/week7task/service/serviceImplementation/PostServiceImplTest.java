package com.example.week7task.service.serviceImplementation;

import com.example.week7task.model.Post;
import com.example.week7task.model.User;
import com.example.week7task.repository.PostRepository;
import com.example.week7task.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
class PostServiceImplTest {

    @Mock
    private PostRepository postRepository;
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private PostServiceImpl postServiceImpl;
    Post post;
    User user;

    @BeforeEach
    void setUp() {
        post =  new Post();
        post.setTitle("Bright");
        post.setImageName("LightHouse.jpg");
        post.setChecker("Active");
        post.setBody("Body");


        user = new User();
        user.setFirstname("Ugo");
        user.setLastname("Anos");
        user.setPassword("artYje123@");
        user.setEmail("ugo@gmail.com");
        user.setDob("12/12/1990");
        user.setGender("Male");
    }

    @Test
    void createPost() {
        //Mock User Repository
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        //Mock post repository
        when(postRepository.save(any(Post.class))).thenReturn(post);

        //call the create post method
        boolean result = postServiceImpl.createPost(1L, post);
        assertTrue(result);

        //verify
        verify(postRepository, times(1)).save(any(Post.class));
        verify(userRepository, times(1)).findById(anyLong());
    }

    @Test
    void editPost() {
    }

    @Test
    void deletePost() {
    }
}