package com.bankstech.BankingSystemApplication.controller;

import com.bankstech.BankingSystemApplication.entity.Bank;
import com.bankstech.BankingSystemApplication.entity.User;
import com.bankstech.BankingSystemApplication.model.BankModel;
import com.bankstech.BankingSystemApplication.model.Link;
import com.bankstech.BankingSystemApplication.model.ResponseMessage;
import com.bankstech.BankingSystemApplication.service.BankService;
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
public class BankController {

    @Autowired
    private BankService bankService;
    @Autowired
    private UserService userService;

    private ModelAndView mv;

    private final String ADMIN_URL = "/admin/bank";
    private final String DEVELOPER_URL = "/developer/bank";
    private ResponseMessage rm;

    @GetMapping(ADMIN_URL+"/all")
    public ModelAndView all(Principal principal){
        User userData = userService.getByEmail(principal.getName());
        mv = new ModelAndView("bank-all");
        mv.addObject("title","All Banks");
        mv.addObject("userData",userData);
        mv.addObject("isRoles",userService.isUserHasRoles(userData));
        mv.addObject("banks", bankService.all());
        mv.addObject("link",new Link("admin","bank","allBanks"));

        return mv;
    }

    @GetMapping(ADMIN_URL+"/create")
    public ModelAndView create(Principal principal){
        User userData = userService.getByEmail(principal.getName());
        mv = new ModelAndView("bank-create");
        mv.addObject("title","Create Bank");
        mv.addObject("userData",userData);
        mv.addObject("isRoles",userService.isUserHasRoles(userData));
        mv.addObject("bankModel",new BankModel());
        mv.addObject("link",new Link("admin","bank","createBank"));

        return mv;
    }

    @PostMapping(ADMIN_URL+"/save")
    public String save(@ModelAttribute BankModel bankModel, Principal principal, RedirectAttributes ra){
        User createdBy = userService.getByEmail(principal.getName());
        rm = bankService.create(bankModel,createdBy);
        ra.addFlashAttribute(rm.getType(),rm.getMessage());

        return "redirect:"+ADMIN_URL+"/create";
    }

    @GetMapping(ADMIN_URL+"/view")
    public ModelAndView view(@RequestParam Long id,Principal principal){
        User userData = userService.getByEmail(principal.getName());
        Bank bank = bankService.getById(id);
        mv = new ModelAndView("bank-view");
        mv.addObject("title","View Bank");
        mv.addObject("userData",userData);
        mv.addObject("bank",bank);
        mv.addObject("isRoles",userService.isUserHasRoles(userData));
        mv.addObject("link",new Link("admin","bank","allBanks"));

        return mv;
    }

    @GetMapping(ADMIN_URL+"/edit")
    public ModelAndView edit(@RequestParam Long id, Principal principal){
        User userData = userService.getByEmail(principal.getName());
        Bank bank = bankService.getById(id);
        mv = new ModelAndView("bank-edit");
        mv.addObject("title", "Edit Bank");
        mv.addObject("userData",userData);
        mv.addObject("bankModel",BankModel.builder().bankId(bank.getBankId()).name(bank.getName()).build());
        mv.addObject("isRoles",userService.isUserHasRoles(userData));
        mv.addObject("link",new Link("admin","bank","allBanks"));

        return mv;
    }

    @PostMapping(ADMIN_URL+"/update")
    public String update(@ModelAttribute BankModel bankModel, Principal principal, RedirectAttributes ra){
        User updatedBy = userService.getByEmail(principal.getName());
        rm = bankService.update(bankModel,updatedBy);
        ra.addFlashAttribute(rm.getType(),rm.getMessage());

        if(rm.getType().equals("error")){
            return "redirect:"+ADMIN_URL+"/edit?id="+bankModel.getBankId();
        }

        return "redirect:"+ADMIN_URL+"/all";
    }

    @GetMapping(ADMIN_URL+"/delete")
    public String delete(@RequestParam Long id, Principal principal, RedirectAttributes ra){
        User deletedBy = userService.getByEmail(principal.getName());
        rm = bankService.delete(id,deletedBy);
        ra.addFlashAttribute(rm.getType(),rm.getMessage());

        return "redirect:"+ADMIN_URL+"/all";
    }
}
