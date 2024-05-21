package com.bankstech.BankingSystemApplication.controller;

import com.bankstech.BankingSystemApplication.entity.User;
import com.bankstech.BankingSystemApplication.model.ChangePassword;
import com.bankstech.BankingSystemApplication.model.Link;
import com.bankstech.BankingSystemApplication.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

@Controller
public class UserController {

    private final String USER_URL = "/user";

    private final String ADMIN_URL = "/admin/user";

    private ModelAndView mv;

    @Autowired
    private UserService userService;


    @GetMapping("/login")
    public ModelAndView loginPage(){
       mv = new ModelAndView("login");
       mv.addObject("title","Login");
       return mv;
    }

    @GetMapping("/")
    public ModelAndView dashboard(Principal principal){
        mv = new ModelAndView("index");
        User user = userService.getByEmail(principal.getName());
        mv.addObject("userData",user);
        mv.addObject("title","Home Page");
        mv.addObject("link",new Link("",""));
        mv.addObject("isRoles",userService.isUserHasRoles(user));

        return mv;
    }

    @GetMapping("/change-password")
    public ModelAndView changePassword(Principal principal){
        User user = userService.getByEmail(principal.getName());
        ChangePassword changePassword = ChangePassword.builder()
                .userId(user.getUserId())
                .email(user.getEmail())
                .build();
        mv = new ModelAndView("change-password");
        mv.addObject("title","Change Password");
        mv.addObject("changePassword",changePassword);
        mv.addObject("userData",user);
        mv.addObject("link",new Link("",""));
        mv.addObject("isRoles",userService.isUserHasRoles(user));

        return mv;
    }

}
