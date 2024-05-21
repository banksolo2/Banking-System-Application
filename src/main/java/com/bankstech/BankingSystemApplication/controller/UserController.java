package com.bankstech.BankingSystemApplication.controller;

import com.bankstech.BankingSystemApplication.entity.User;
import com.bankstech.BankingSystemApplication.model.ChangePassword;
import com.bankstech.BankingSystemApplication.model.CreateUserModel;
import com.bankstech.BankingSystemApplication.model.Link;
import com.bankstech.BankingSystemApplication.model.ResponseMessage;
import com.bankstech.BankingSystemApplication.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Controller
public class UserController {

    private final String USER_URL = "/user";

    private final String ADMIN_URL = "/admin/user";

    private ModelAndView mv;

    @Autowired
    private UserService userService;

    private ResponseMessage rm;


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

    @PostMapping("/change-password/save")
    public String saveChangePassword(@ModelAttribute ChangePassword changePassword, RedirectAttributes ra, Principal principal){
        User user = userService.getByEmail(principal.getName());
        rm = userService.changePassword(changePassword,user);

        ra.addFlashAttribute(rm.getType(),rm.getMessage());

        return "redirect:/change-password";

    }

    @GetMapping("logout-success")
    public ModelAndView logoutPage(){
        mv = new ModelAndView("logout-success");
        mv.addObject("title", "Logout Page");
        return  mv;
    }

    @GetMapping(ADMIN_URL+"/create")
    public ModelAndView createUser(Principal principal){
        User user = userService.getByEmail(principal.getName());
        mv = new ModelAndView("user-create");
        mv.addObject("title","Create User");
        mv.addObject("isRoles",userService.isUserHasRoles(user));
        mv.addObject("createUserModel",new CreateUserModel());
        mv.addObject("link",new Link("admin","createUser","user"));
        mv.addObject("userData",user);

        return mv;
    }

    @PostMapping(ADMIN_URL+"/save")
    public String saveUser(@ModelAttribute CreateUserModel createUserModel, Principal principal,RedirectAttributes ra){
        User user = userService.getByEmail(principal.getName());
        rm = userService.create(createUserModel,user);
        ra.addFlashAttribute(rm.getType(),rm.getMessage());

        return "redirect:"+ADMIN_URL+"/create";
    }

}
