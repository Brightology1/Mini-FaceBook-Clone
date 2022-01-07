package com.example.week7task.service.serviceImplementation;

import com.example.week7task.model.Comment;
import com.example.week7task.model.Likes;
import com.example.week7task.model.Post;
import com.example.week7task.model.User;
import com.example.week7task.otherModel.PostMapper;
import com.example.week7task.repository.CommentRepository;
import com.example.week7task.repository.LikesRepository;
import com.example.week7task.repository.PostRepository;
import com.example.week7task.repository.UserRepository;
import com.example.week7task.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PostServiceImpl implements PostService {

     private final
    PostRepository postRepository;
    private final
    LikesRepository likesRepository;
    private final
    CommentRepository commentRepository;
    private final
    UserRepository userRepository;

    public PostServiceImpl(PostRepository postRepository, LikesRepository likesRepository, CommentRepository commentRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.likesRepository = likesRepository;
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
    }


    public boolean createPost(Long userId, Post post) {
        boolean result = false;
        User user = userRepository.findById(userId).get();
        if(user != null){
            post.setChecker("ACTIVE");
            postRepository.save(post);
            result = true;
        }
        return result;
    }



    public List<Post> getPostById(Long postId){
        List<Post> postList = postRepository.findPostByPostId(postId);
         if(postList == null){
             System.out.println("Something went wrong1");
         }
        return postList;
    }

    public List<PostMapper> getPost(User currentUser) {
        List<PostMapper> posts = new ArrayList<>();
        List<Post> postData = postRepository.findAllByCheckerIsOrderByPostIdDesc("ACTIVE");

        for (Post postEach:postData) {

            PostMapper post = new PostMapper();
            post.setId(postEach.getPostId());
            post.setTitle(postEach.getTitle());
            post.setBody(postEach.getBody());
            post.setImageName("/image/"+postEach.getImageName());
            post.setName(postEach.getUser().getLastname()+ " "+ postEach.getUser().getFirstname());

            //the total number of likes on this particular post
            List<Likes> numberOfLikes = likesRepository.findAllByPostPostId(postEach.getPostId());
            int likeCount = numberOfLikes.size();
            post.setNoLikes(likeCount);

            //the total number of comments on this particular post
            List<Comment> noOfComment = commentRepository.findAllByPostPostId(postEach.getPostId());
            int commentCount = noOfComment.size();
            post.setNoComments(commentCount);

            //return true if current user liked this post, else false
            List<Likes> postLiked = likesRepository.findAllByPostPostIdAndUserId(postEach.getPostId(), currentUser.getId());
            if(postLiked.size() > 0){
                post.setLikedPost(true);
            }

            posts.add(post);
        }

        return posts;
    }

    public boolean editPost(User user, Long postId, String title, String body) {
        boolean status = false;
        Post post = postRepository.findById(postId).get();
        if (post != null){
            post.setTitle(title);
        post.setBody(body);
        postRepository.save(post);

        status = true;
    }
        return status;
    }

    public boolean deletePost(Long postId, Long personId) {
        boolean status = false;
        Post post = postRepository.findPostByPostIdAndUserId(postId, personId);
        if (post != null) {
            post.setChecker("INACTIVE");
            postRepository.save(post);
            status = true;
        }
        return status;
    }
}
