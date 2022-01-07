package com.example.week7task.controller;

import com.example.week7task.model.Comment;
import com.example.week7task.model.Post;
import com.example.week7task.model.User;
import com.example.week7task.otherModel.Login;
import com.example.week7task.otherModel.PostMapper;
import com.example.week7task.service.serviceImplementation.PostServiceImpl;
import com.example.week7task.service.serviceImplementation.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private PostServiceImpl postService;


    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView showRegister(HttpServletRequest request, HttpServletResponse response) {

        HttpSession session = request.getSession();
        session.removeAttribute("message");

        ModelAndView mav = new ModelAndView("index");
        mav.addObject("user", new User());
        mav.addObject("login", new Login());

        return mav;
    }


    @RequestMapping(value = "/processLogout", method = RequestMethod.GET)
    public String logout(HttpServletRequest request, HttpServletResponse response) {

        HttpSession session = request.getSession();
        session.invalidate();

        return "redirect:/";
    }


    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public ModelAndView showHome(HttpServletRequest request, HttpServletResponse response) {

        HttpSession httpSession = request.getSession();
        User user = (User) httpSession.getAttribute("user");

        if(user == null) {
            ModelAndView mav = new ModelAndView("index");
            mav.addObject("person", new User());
            mav.addObject("login", new Login());
            httpSession.setAttribute("mess", "!!!Please Login");
            return mav;
        }

        ModelAndView mav = new ModelAndView("home");
        mav.addObject("post", new Post());
        mav.addObject("commentData", new Comment());

        List<PostMapper> post = postService.getPost(user);

        mav.addObject("user", user);
        mav.addObject("posts", post);

        return mav;
    }

    @RequestMapping(value = "/registerProcess", method = RequestMethod.POST)
    public String addUser(HttpServletRequest request, HttpServletResponse response,
                          @ModelAttribute("user") User user) {

        HttpSession httpSession = request.getSession();

        if(userService.createUser(user)){
            httpSession.setAttribute("mess", "Successfully registered!!!");
        }else{
            httpSession.setAttribute("mess", "Failed to register or email already exist");
        }

        return "redirect:/";
    }


    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView showLogin(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView("index");
        mav.addObject("login", new Login());

        return mav;
    }


    @RequestMapping(value = "/loginProcess", method = RequestMethod.POST)
    public String loginProcess(HttpServletRequest request, HttpServletResponse response,
                               @ModelAttribute("login") Login login) {

        User user = userService.getUser(login.getEmail(), login.getPassword());

        HttpSession httpSession = request.getSession();

        if (user != null) {
            httpSession.setAttribute("user", user);
            return "redirect:/home";
        } else {
            httpSession.setAttribute("mess", "Email or Password is wrong!!!");
            return "redirect:/";
        }
    }
}
