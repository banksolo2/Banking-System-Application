package com.bankstech.BankingSystemApplication.controller;

import com.bankstech.BankingSystemApplication.entity.User;
import com.bankstech.BankingSystemApplication.model.*;
import com.bankstech.BankingSystemApplication.service.AccountService;
import com.bankstech.BankingSystemApplication.service.BankService;
import com.bankstech.BankingSystemApplication.service.TransactionService;
import com.bankstech.BankingSystemApplication.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Controller
@RequestMapping("/transaction")
public class TransactionController {

    @Autowired
    private UserService userService;
    @Autowired
    private TransactionService transactionService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private BankService bankService;

    private ResponseMessage rm;
    private ModelAndView mv;


    @GetMapping("/deposit")
    public ModelAndView deposit(Principal principal){
        User userData = userService.getByEmail(principal.getName());
        mv = new ModelAndView("transaction-deposit");
        mv.addObject("userData",userData);
        mv.addObject("isRoles",userService.isUserHasRoles(userData));
        mv.addObject("title","Make Deposit");
        mv.addObject("depositModel",new DepositModel());
        mv.addObject("link",new Link("transaction","deposit"));
        mv.addObject("accounts",accountService.options(0L));

        return mv;
    }

    @PostMapping("/deposit/save")
    public String depositSave(@ModelAttribute DepositModel depositModel, Principal principal, RedirectAttributes ra){
        User doneBy = userService.getByEmail(principal.getName());
        rm = transactionService.deposit(depositModel,doneBy);
        ra.addFlashAttribute(rm.getType(),rm.getMessage());

        return "redirect:/transaction/deposit";
    }

    @GetMapping("/transfer")
    public ModelAndView transfer(Principal principal){
        User userData = userService.getByEmail(principal.getName());
        mv = new ModelAndView("transaction-transfer");
        mv.addObject("title","Make Transfer");
        mv.addObject("userData",userData);
        mv.addObject("isRoles",userService.isUserHasRoles(userData));
        mv.addObject("accounts",accountService.options(0L));
        mv.addObject("transferModel",new TransferModel());
        mv.addObject("link",new Link("transaction","transfer"));

        return mv;
    }

    @PostMapping("/transfer/save")
    public String transferSave(@ModelAttribute TransferModel transferModel,Principal principal,RedirectAttributes ra){
        User doneBy = userService.getByEmail(principal.getName());
        rm = transactionService.transfer(transferModel,doneBy);
        ra.addFlashAttribute(rm.getType(),rm.getMessage());

        return "redirect:/transaction/transfer";
    }

    @GetMapping("/transfer-other-bank")
    public ModelAndView transferOtherBank(Principal principal){
        User userData = userService.getByEmail(principal.getName());
        mv = new ModelAndView("transaction-transfer-other-banks");
        mv.addObject("title","Transfer To Other Banks");
        mv.addObject("accounts",accountService.options(0L));
        mv.addObject("banks",bankService.options(0L));
        mv.addObject("userData",userData);
        mv.addObject("isRoles",userService.isUserHasRoles(userData));
        mv.addObject("transferOtherBankModel",new TransferOtherBankModel());
        mv.addObject("link",new Link("transaction","transferOtherBank"));

        return mv;
    }

    @PostMapping("/transfer-other-bank/save")
    public String saveTransferOtherBank(@ModelAttribute TransferOtherBankModel transferOtherBankModel,Principal principal,RedirectAttributes ra){
        User doneBy = userService.getByEmail(principal.getName());
        rm = transactionService.transferToOtherBank(transferOtherBankModel,doneBy);
        ra.addFlashAttribute(rm.getType(),rm.getMessage());

        return "redirect:/transaction/transfer-other-bank";
    }

    @GetMapping("/all")
    public ModelAndView all(Principal principal){
        User userData = userService.getByEmail(principal.getName());
        mv = new ModelAndView("transaction-all");
        mv.addObject("title","All Transaction");
        mv.addObject("userData",userData);
        mv.addObject("isRoles",userService.isUserHasRoles(userData));
        mv.addObject("link",new Link("transaction","allTransaction"));
        mv.addObject("transactions",transactionService.all());

        return mv;
    }

    @GetMapping("/view")
    public ModelAndView view(@RequestParam("id")Long id, Principal principal){
        User userData = userService.getByEmail(principal.getName());
        mv = new ModelAndView("transaction-view");
        mv.addObject("title","View");
        mv.addObject("userData",userData);
        mv.addObject("isRoles",userService.isUserHasRoles(userData));
        mv.addObject("link",new Link("transaction","allTransaction"));
        mv.addObject("transaction",transactionService.getById(id));

        return mv;
    }

    @GetMapping("/history")
    public ModelAndView history(Principal principal){
        User userData = userService.getByEmail(principal.getName());
        mv = new ModelAndView("transaction-history");
        mv.addObject("title","History");
        mv.addObject("userData",userData);
        mv.addObject("link",new Link("transaction","history"));
        mv.addObject("isRoles",userService.isUserHasRoles(userData));
        mv.addObject("transactionHistoryModel",new TransactionHistoryModel());
        mv.addObject("accounts",accountService.options(0L));

        return mv;
    }

    @GetMapping("/history/search")
    public ModelAndView historySearch(@ModelAttribute TransactionHistoryModel transactionHistoryModel, Principal principal){
        User userData = userService.getByEmail(principal.getName());
        mv = new ModelAndView("transaction-history-result");
        mv.addObject("title","Result");
        mv.addObject("userData",userData);
        mv.addObject("link",new Link("transaction","history"));
        mv.addObject("isRoles",userService.isUserHasRoles(userData));
        mv.addObject("transactions",transactionService.getHistory(transactionHistoryModel));

        return mv;
    }

}
