package com.bankstech.BankingSystemApplication.controller;

import com.bankstech.BankingSystemApplication.entity.Account;
import com.bankstech.BankingSystemApplication.entity.AccountStatus;
import com.bankstech.BankingSystemApplication.entity.User;
import com.bankstech.BankingSystemApplication.model.AccountModel;
import com.bankstech.BankingSystemApplication.model.Link;
import com.bankstech.BankingSystemApplication.model.ResponseMessage;
import com.bankstech.BankingSystemApplication.service.*;
import com.bankstech.BankingSystemApplication.utils.DateUtils;
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
public class AccountController {

    private final String ADMIN_URL = "/admin/account";
    private final String USER_URL = "/user/account";
    @Autowired
    private AccountService accountService;
    @Autowired
    private AccountTypeService accountTypeService;
    @Autowired
    private AccountStatusService accountStatusService;
    @Autowired
    private UserService userService;
    @Autowired
    private GenderService genderService;
    @Autowired
    private StateService stateService;
    @Autowired
    private DateUtils dateUtils;
    private ModelAndView mv;
    private ResponseMessage rm;



    @GetMapping(ADMIN_URL+"/create")
    public ModelAndView create(Principal principal){
        User userData = userService.getByEmail(principal.getName());
        mv = new ModelAndView("account-create");
        mv.addObject("title","Create Account");
        mv.addObject("userData",userData);
        mv.addObject("isRoles",userService.isUserHasRoles(userData));
        mv.addObject("link",new Link("admin","account","createAccount"));
        mv.addObject("accountModel",new AccountModel());
        mv.addObject("accountTypes",accountTypeService.options(0L));
        mv.addObject("genders",genderService.options(0L));
        mv.addObject("states",stateService.options(0L));

        return mv;
    }

    @PostMapping(ADMIN_URL+"/save")
    public String save(@ModelAttribute AccountModel accountModel, Principal principal, RedirectAttributes ra){
        User createdBy = userService.getByEmail(principal.getName());
        rm = accountService.create(accountModel,createdBy);
        ra.addFlashAttribute(rm.getType(),rm.getMessage());

        return "redirect:"+ADMIN_URL+"/create";
    }

    @GetMapping(ADMIN_URL+"/all-savings")
    public ModelAndView allSavings(Principal principal){
        User userData = userService.getByEmail(principal.getName());
        mv = new ModelAndView("account-all-savings");
        mv.addObject("title","All Savings Account");
        mv.addObject("userData",userData);
        mv.addObject("isRoles", userService.isUserHasRoles(userData));
        mv.addObject("link", new Link("admin","account","allSavingsAccount"));
        mv.addObject("accounts",accountService.getAllAccountByType("savings"));

        return mv;
    }

    @GetMapping(ADMIN_URL+"/all-current")
    public ModelAndView allCurrent(Principal principal){
        User userData = userService.getByEmail(principal.getName());
        mv = new ModelAndView("account-all-current");
        mv.addObject("title","All Current Account");
        mv.addObject("userData",userData);
        mv.addObject("isRoles", userService.isUserHasRoles(userData));
        mv.addObject("link", new Link("admin","account","allCurrentAccount"));
        mv.addObject("accounts",accountService.getAllAccountByType("current"));

        return mv;
    }

    @GetMapping(ADMIN_URL+"/view-savings")
    public ModelAndView viewSavings(@RequestParam("id")Long id,Principal principal){
        User userData = userService.getByEmail(principal.getName());
        Account account = accountService.getById(id);
        mv = new ModelAndView("account-view-savings");
        mv.addObject("title","View Account");
        mv.addObject("userData",userData);
        mv.addObject("isRoles",userService.isUserHasRoles(userData));
        mv.addObject("link", new Link("admin","account","allSavingsAccount"));
        mv.addObject("account",account);
        mv.addObject("dateOfBirth", dateUtils.convertToString(account.getDateOfBirth()));

        return mv;
    }

    @GetMapping(ADMIN_URL+"/view-current")
    public ModelAndView viewCurrent(@RequestParam("id")Long id,Principal principal){
        User userData = userService.getByEmail(principal.getName());
        Account account = accountService.getById(id);
        mv = new ModelAndView("account-view-current");
        mv.addObject("title","View Account");
        mv.addObject("userData",userData);
        mv.addObject("isRoles",userService.isUserHasRoles(userData));
        mv.addObject("link", new Link("admin","account","allCurrentAccount"));
        mv.addObject("account",account);
        mv.addObject("dateOfBirth", dateUtils.convertToString(account.getDateOfBirth()));

        return mv;
    }

    @GetMapping(ADMIN_URL+"/edit-savings")
    public ModelAndView editSavings(@RequestParam("id")Long id,Principal principal){
        User userData = userService.getByEmail(principal.getName());
        Account account = accountService.getById(id);
        mv = new ModelAndView("account-edit");
        mv.addObject("title","Edit");
        mv.addObject("userData",userData);
        mv.addObject("isRoles",userService.isUserHasRoles(userData));
        mv.addObject("link", new Link("admin","account","allSavingsAccount"));
        mv.addObject("accountModel",accountService.convertToAccountModel(account));
        mv.addObject("genders",genderService.options(account.getGender().getGenderId()));
        mv.addObject("states",stateService.options(account.getStateOfOrigin().getStateId()));
        mv.addObject("accountStatuses", accountStatusService.options(account.getAccountStatus().getAccountStatusId()));

        return mv;
    }


    @PostMapping(ADMIN_URL+"/update")
    public String update(@ModelAttribute AccountModel accountModel,Principal principal,RedirectAttributes ra){
        User updatedBy = userService.getByEmail(principal.getName());
        Account account = accountService.getById(accountModel.getAccountId());
        rm = accountService.update(accountModel,updatedBy);
        ra.addFlashAttribute(rm.getType(),rm.getMessage());
        if(rm.getType().equals("error")){
            if(account.getAccountType().getName().equalsIgnoreCase("Savings")){
                return "redirect:"+ADMIN_URL+"/edit-savings?id="+accountModel.getAccountId();
            }
            else{
                return "redirect:"+ADMIN_URL+"/edit-current?id="+accountModel.getAccountId();
            }
        }

        if(account.getAccountType().getName().equalsIgnoreCase("Savings")){
            return "redirect:"+ADMIN_URL+"/all-savings";
        }

        return "redirect:"+ADMIN_URL+"/all-current";

    }

    @GetMapping(ADMIN_URL+"/edit-current")
    public ModelAndView editCurrent(@RequestParam("id")Long id,Principal principal){
        User userData = userService.getByEmail(principal.getName());
        Account account = accountService.getById(id);
        mv = new ModelAndView("account-edit");
        mv.addObject("title","Edit");
        mv.addObject("userData",userData);
        mv.addObject("isRoles",userService.isUserHasRoles(userData));
        mv.addObject("link", new Link("admin","account","allCurrentAccount"));
        mv.addObject("accountModel",accountService.convertToAccountModel(account));
        mv.addObject("genders",genderService.options(account.getGender().getGenderId()));
        mv.addObject("states",stateService.options(account.getStateOfOrigin().getStateId()));
        mv.addObject("accountStatuses", accountStatusService.options(account.getAccountStatus().getAccountStatusId()));

        return mv;
    }


}
