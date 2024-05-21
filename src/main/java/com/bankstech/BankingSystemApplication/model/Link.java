package com.bankstech.BankingSystemApplication.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class Link {
    private String head;
    private String sub;
    private String option;

    public Link(String head,String sub){
        this.head = head;
        this.sub = sub;
    }

    public Link(String head,String sub,String option){
        this.head = head;
        this.sub = sub;
        this.option = option;
    }
}
