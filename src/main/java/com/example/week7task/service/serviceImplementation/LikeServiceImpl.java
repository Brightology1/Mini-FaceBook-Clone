package com.example.week7task.service.serviceImplementation;

import com.example.week7task.model.Likes;
import com.example.week7task.model.Post;
import com.example.week7task.model.User;
import com.example.week7task.repository.LikesRepository;
import com.example.week7task.repository.PostRepository;
import com.example.week7task.service.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LikeServiceImpl implements LikeService {

    private final LikesRepository likesRepository;
    private final PostRepository postRepository;

    public LikeServiceImpl(LikesRepository likesRepository, PostRepository postRepository) {
        this.likesRepository = likesRepository;
        this.postRepository = postRepository;
    }

    /**
     * CREATE operation on Comment
     * @param user
     * @param postId
     * @param action
     * @return boolean(true for successful creation and false on failure on liked/unliked post)
     * */
    public boolean likePost(User user, Long postId, String action){
        boolean result = false;

        Post post = postRepository.findById(postId).get();
        if(post != null){
            Likes like = new Likes();
            like.setUser(user);
            like.setPost(post);

            if(action.equals("1")){
                likesRepository.save(like);
                System.out.println("save");
            }else{
                likesRepository.deleteLikesByPostAndUser(post, user);
                System.out.println("delete");
            }

            result = true;
        }

        return result;
    }
}
