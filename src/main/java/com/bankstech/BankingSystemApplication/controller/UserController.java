package com.bankstech.BankingSystemApplication.controller;

import com.bankstech.BankingSystemApplication.entity.Role;
import com.bankstech.BankingSystemApplication.entity.User;
import com.bankstech.BankingSystemApplication.model.*;
import com.bankstech.BankingSystemApplication.service.GenderService;
import com.bankstech.BankingSystemApplication.service.RoleService;
import com.bankstech.BankingSystemApplication.service.UserService;
import com.bankstech.BankingSystemApplication.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
public class UserController {

    private final String USER_URL = "/user";

    private final String ADMIN_URL = "/admin/user";

    private ModelAndView mv;

    @Autowired
    private UserService userService;

    @Autowired
    private GenderService genderService;

    @Autowired
    private RoleService roleService;

    private ResponseMessage rm;
    @Autowired
    private UserServiceImpl userServiceImpl;


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
        mv.addObject("genders",genderService.options(0L));
        mv.addObject("link",new Link("admin","user","createUser"));
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

    @GetMapping(ADMIN_URL+"/all")
    public ModelAndView all(Principal principal){
        User user = userService.getByEmail(principal.getName());
        mv = new ModelAndView("user-all");
        mv.addObject("title", "All Users");
        mv.addObject("users",userService.all());
        mv.addObject("isRoles",userService.isUserHasRoles(user));
        mv.addObject("userData",user);
        mv.addObject("link",new Link("admin","user","allUsers"));

        return mv;
    }

    @GetMapping(ADMIN_URL+"/view")
    public ModelAndView view(@RequestParam Long id, Principal principal){
        User userData = userService.getByEmail(principal.getName());
        mv = new ModelAndView("user-view");
        mv.addObject("userData",userData);
        mv.addObject("title","View User");
        mv.addObject("user", userService.getById(id));
        mv.addObject("isRoles",userService.isUserHasRoles(userData));
        mv.addObject("link",new Link("admin","user","allUsers"));

        return mv;
    }

    @GetMapping(ADMIN_URL+"/role")
    public ModelAndView roles(@RequestParam Long id,Principal principal){
        User userData = userService.getByEmail(principal.getName());
        User user = userService.getById(id);
        mv = new ModelAndView("user-roles");
        mv.addObject("title", "User Roles");
        mv.addObject("userData",userData);
        mv.addObject("user",user);
        mv.addObject("isRoles",userService.isUserHasRoles(userData));
        mv.addObject("link",new Link("admin","user","allUsers"));

        return mv;
    }

    @GetMapping(ADMIN_URL+"/role/delete")
    public String deleteRole(@RequestParam("role_id") Long roleId, @RequestParam("user_id") Long userId, Principal principal,RedirectAttributes ra){
        User updatedBy = userService.getByEmail(principal.getName());
        rm = userService.deleteUserRole(roleId,userId,updatedBy);
        ra.addFlashAttribute(rm.getType(),rm.getMessage());

        return "redirect:"+ADMIN_URL+"/role?id="+userId;
    }

    @GetMapping(ADMIN_URL+"/role/edit")
    public ModelAndView editRole(@RequestParam Long id, Principal principal){
        User userData = userService.getByEmail(principal.getName());
        User user = userService.getById(id);
        List<Long> roleIds = new ArrayList<>();
        for(Role r : user.getRoles()){
            roleIds.add(r.getRoleId());
        }
        if(roleIds.size() == 0){
            roleIds.add(0L);
        }
        mv = new ModelAndView("user-edit-roles");
        mv.addObject("title", "Edit User Roles");
        mv.addObject("userData",userData);
        mv.addObject("user",user);
        mv.addObject("link",new Link("admin","user","allUsers"));
        mv.addObject("options", roleService.options(roleIds));
        mv.addObject("isRoles",userService.isUserHasRoles(userData));
        mv.addObject("addUserRoleModel", AddUserRoleModel.builder()
                        .userId(user.getUserId())
                        .userName(user.getFirstName()+" "+user.getMiddleName()+" "+user.getLastName())
                        .roles(user.getRoles())
                .build());

        return mv;
    }

    @PostMapping(ADMIN_URL+"/role/save")
    public String saveRole(@ModelAttribute AddUserRoleModel addUserRoleModel,Principal principal,RedirectAttributes ra){
        User updatedBy = userService.getByEmail(principal.getName());
        rm = userService.saveUserRole(addUserRoleModel,updatedBy);

        ra.addFlashAttribute(rm.getType(),rm.getMessage());

        if(rm.getType().equals("error")){
            return "redirect:"+ADMIN_URL+"/role/edit?id="+addUserRoleModel.getUserId();
        }

        return "redirect:"+ADMIN_URL+"/role?id="+addUserRoleModel.getUserId();
    }

    @GetMapping(ADMIN_URL+"/edit")
    public ModelAndView editUser(@RequestParam Long id, Principal principal){
        User userData = userService.getByEmail(principal.getName());
        User user = userService.getById(id);
        UpdateUserModel updateUserModel = userService.convertUserToUpdateUserModel(user);

        mv = new ModelAndView("user-edit");
        mv.addObject("title","Edit User");
        mv.addObject("userData", userData);
        mv.addObject("isRoles", userService.isUserHasRoles(userData));
        mv.addObject("user",user);
        mv.addObject("genders",genderService.options(user.getGender().getGenderId()));
        mv.addObject("link",new Link("admin","user","allUsers"));
        mv.addObject("updateUserModel",updateUserModel);

        return mv;
    }

    @PostMapping(ADMIN_URL+"/update")
    public String updateUser(@ModelAttribute UpdateUserModel updateUserModel, Principal principal,RedirectAttributes ra){
        User updatedBy = userService.getByEmail(principal.getName());
        rm = userService.editUser(updateUserModel,updatedBy);
        ra.addFlashAttribute(rm.getType(),rm.getMessage());
        if(rm.getType().equals("error")){
            return "redirect:"+ADMIN_URL+"/edit?id="+updateUserModel.getUserId();
        }

        return "redirect:"+ADMIN_URL+"/all";
    }

    @GetMapping(ADMIN_URL+"/delete")
    public String deleteUser(@RequestParam Long id, Principal principal, RedirectAttributes ra){
        User deletedBy = userService.getByEmail(principal.getName());
        rm = userService.delete(id,deletedBy);
        ra.addFlashAttribute(rm.getType(),rm.getMessage());

        return "redirect:"+ADMIN_URL+"/all";
    }


}
