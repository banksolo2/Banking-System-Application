package com.bankstech.BankingSystemApplication.controller;

import com.bankstech.BankingSystemApplication.entity.State;
import com.bankstech.BankingSystemApplication.entity.User;
import com.bankstech.BankingSystemApplication.model.Link;
import com.bankstech.BankingSystemApplication.model.ResponseMessage;
import com.bankstech.BankingSystemApplication.service.StateService;
import com.bankstech.BankingSystemApplication.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Controller
public class StateController {

    @Autowired
    private UserService userService;

    @Autowired
    private StateService stateService;

    private ModelAndView mv;

    private final String ADMIN_URL = "/admin/state";

    private ResponseMessage rm;

    @GetMapping(ADMIN_URL+"/create")
    public ModelAndView create(Principal principal){
        User userData = userService.getByEmail(principal.getName());
        mv = new ModelAndView("state-create");
        mv.addObject("title","Create State");
        mv.addObject("userData",userData);
        mv.addObject("isRoles",userService.isUserHasRoles(userData));
        mv.addObject("state", new State());
        mv.addObject("link",new Link("admin","state","createState"));

        return mv;
    }


    @PostMapping(ADMIN_URL+"/save")
    public String save(@ModelAttribute State state, RedirectAttributes ra){
        rm = stateService.create(state);
        ra.addFlashAttribute(rm.getType(),rm.getMessage());

        return "redirect:"+ADMIN_URL+"/create";
    }

    @GetMapping(ADMIN_URL+"/all")
    public ModelAndView all(Principal principal){
        User userData = userService.getByEmail(principal.getName());
        mv = new ModelAndView("state-all");
        mv.addObject("title", "All States");
        mv.addObject("userData",userData);
        mv.addObject("link",new Link("admin","state","allStates"));
        mv.addObject("isRoles",userService.isUserHasRoles(userData));
        mv.addObject("states",stateService.all());

        return mv;
    }

    @GetMapping(ADMIN_URL+"/delete")
    public String delete(@RequestParam("id")Long id,RedirectAttributes ra){
        rm = stateService.delete(id);
        ra.addFlashAttribute(rm.getType(),rm.getMessage());
        return "redirect:"+ADMIN_URL+"/all";
    }

    @GetMapping(ADMIN_URL+"/edit")
    public ModelAndView edit(@RequestParam("id")Long id, Principal principal){
        User userData = userService.getByEmail(principal.getName());
        mv = new ModelAndView("state-edit");
        mv.addObject("title", "Edit State");
        mv.addObject("userData",userData);
        mv.addObject("isRoles",userService.isUserHasRoles(userData));
        mv.addObject("link",new Link("admin","state","allStates"));
        mv.addObject("state",stateService.getById(id));

        return mv;
    }

    @PostMapping(ADMIN_URL+"/update")
    public String update(@ModelAttribute State state,RedirectAttributes ra){
        rm = stateService.update(state);
        ra.addFlashAttribute(rm.getType(),rm.getMessage());
        if(rm.getType().equals("error")){
            return "redirect:"+ADMIN_URL+"/edit?id="+state.getStateId();
        }

        return "redirect:"+ADMIN_URL+"/all";
    }
}
