package com.example.week7task.controller;

import com.example.week7task.model.Post;
import com.example.week7task.model.User;
import com.example.week7task.service.serviceImplementation.PostServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Controller
@MultipartConfig
public class PostController {

    @Autowired
    PostServiceImpl postService;

    /**
     * Post request to process posts made by users
     * redirects to login page if user is not in the session
     * maps an image file to a folder then saves its name in the database
     * save post to database or perhaps an error occurs
     * redirects back to home
     * */
    @RequestMapping(value = "/postProcessing", method = RequestMethod.POST)
    public String addUser(HttpServletRequest request, HttpServletResponse response,
                          @ModelAttribute("post") Post post, HttpSession session) {

        User user = (User) session.getAttribute("user");

        if(user == null) return "redirect:/";

        try {

            Part part = request.getPart("file");

            //set imageName
            String imageName = part.getSubmittedFileName();
            post.setImageName(imageName);

            //set person
            post.setUser(user);

            //path to store image
            String path = "/Users/mac/Desktop/Week-7-Task/src/main/resources/static/image"+ File.separator+post.getImageName();

            InputStream in = part.getInputStream();
            boolean success = uploadFile(in, path);

            if(postService.createPost(user.getId(), post)) {
                session.setAttribute("message", "File uploaded successfully");
            }else{
                session.setAttribute("message", "Error uploading image to database");
            }

        } catch (IOException | ServletException e) {
            e.printStackTrace();
        }

        return "redirect:/home";
    }

    /**
     * Get request to get edit ppage
     * redirects to login page if user is not in the session
     * renders the the edit page
     * */
    @RequestMapping(value = "/edit/{post}", method = RequestMethod.GET)
    public String editComment(@PathVariable("post") Long post_id, Model model, HttpServletRequest request, HttpServletResponse response, HttpSession session) {

        User user = (User) session.getAttribute("user");

        if(user == null) return "redirect:/";

        Long postId = post_id;

        List<Post> post = postService.getPostById(postId);

        model.addAttribute("postData", post.get(0));
        model.addAttribute("user", user);

        return "edit";
    }

    /**
     * Post request to edit posts made by users
     * redirects to login page if user is not in the session
     * save edited post to database or perhaps an error occurs
     * redirects back to home page
     * */
    @RequestMapping(value = "/editProcessing", method = RequestMethod.POST)
    public String addUser(HttpServletRequest request, HttpServletResponse response, HttpSession session,
                          @ModelAttribute("post") Post post) {

        User user = (User) session.getAttribute("user");

        if(user == null) return "redirect:/";

        if(postService.editPost(user,post.getPostId(),post.getTitle(),post.getBody())) {
            session.setAttribute("message", "Post edited successfully");
        }else{
            session.setAttribute("message", "Error editing post!");
        }

        return "redirect:/home";
    }

    /**
     * Post request to delete posts made by users
     * redirects to login page if user is not in the session
     * delete post to database or perhaps an error occurs
     * redirects back to home page
     * */
    @RequestMapping(value = "/deletePost", method = RequestMethod.POST)
    public String deleteComment(HttpServletRequest request,
                                HttpServletResponse response, HttpSession session) {

        User user = (User) session.getAttribute("user");

        if(user == null) return "redirect:/";

        Long postId = Long.parseLong(request.getParameter("postId"));

        if(postService.deletePost(postId, user.getId())){
            session.setAttribute("message", "Post deleted successfully");
        }else {
            session.setAttribute("message", "Error deleting post! or you don't have access to delete this post");
        }

        return "redirect:/home";
    }


    /**
     * method for reading images to a specific path
     * @param in
     * @param path
     * @return boolean
     */
    public boolean uploadFile(InputStream in, String path){
        boolean test = false;

        try{
            byte[] byt = new byte[in.available()];
            in.read(byt);
            FileOutputStream fops = new FileOutputStream(path);
            fops.write(byt);
            fops.flush();
            fops.close();
            test = true;
        }catch(Exception e){
            e.printStackTrace();
        }

        return test;
    }
}
