package com.example.week7task.controller;

import com.example.week7task.model.User;
import com.example.week7task.service.serviceImplementation.LikeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
public class LikeController {
    @Autowired
    LikeServiceImpl likeService;

    @RequestMapping(value = "/processLike", method = RequestMethod.POST)
    public @ResponseBody
    String likePost(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws IOException {

        User user = (User) session.getAttribute("user");

        if(user == null) return "redirect:/";

        Long postId = Long.parseLong(request.getParameter("postId"));
        String action = request.getParameter("action");

        if(likeService.likePost(user, postId, action)){
            return "successful";
        }else{
            session.setAttribute("message", "server error");
        }

        return "";
    }
}
