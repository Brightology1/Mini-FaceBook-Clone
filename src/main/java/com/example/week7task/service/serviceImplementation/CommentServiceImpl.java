package com.example.week7task.service.serviceImplementation;

import com.example.week7task.model.Comment;
import com.example.week7task.model.Post;
import com.example.week7task.model.User;
import com.example.week7task.otherModel.CommentMapper;
import com.example.week7task.repository.CommentRepository;
import com.example.week7task.repository.PostRepository;
import com.example.week7task.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private PostRepository postRepository;


    public boolean createComment(Long userId, Long postId, Comment comment){
        boolean result = false;

        try{
            Post post = postRepository.findById(postId).get();
            //set the post
            comment.setPost(post);

            commentRepository.save(comment);
            result = true;

        }catch (Exception e){
            e.printStackTrace();
        }

        return result;
    }


    public List<CommentMapper> getComments(Long postId){
        List<CommentMapper> comments = new ArrayList();

        try{

            List<Comment> commentsData = commentRepository.findAllByPostPostId(postId);

            for (Comment commentEach:commentsData) {
                CommentMapper comment = new CommentMapper();
                comment.setId(commentEach.getId());
                comment.setPostId(commentEach.getPost().getPostId());
                comment.setComment(commentEach.getComment());
                comment.setUsername(commentEach.getUser().getLastname()+" "+commentEach.getUser().getFirstname());
                comment.setTitle(commentEach.getPost().getTitle());
                comment.setImageName("/images/"+commentEach.getPost().getImageName());
                comment.setUserId(commentEach.getUser().getId());

                comments.add(comment);
            }

        }catch (Exception e){
            e.printStackTrace();
        }

        return comments;
    }

    public boolean editComment(Long commentId, User user, Long postId, String comment) {
        boolean status = false;
        Post post = postRepository.findById(postId).get();

        Comment data = commentRepository.findCommentById(commentId);

        if(post != null && data != null){
            data.setComment(comment);
            data.setUser(user);
            data.setPost(post);
            commentRepository.save(data);

            status = true;
        }
        return status;
    }


    public boolean deleteComment(Long commentId){
        boolean status =  false;
       if(commentRepository.existsById(commentId)){
           commentRepository.deleteCommentById(commentId);
       }
        return status;
    }
}
