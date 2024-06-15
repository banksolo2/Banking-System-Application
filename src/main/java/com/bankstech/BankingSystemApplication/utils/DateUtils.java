package com.bankstech.BankingSystemApplication.utils;


import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class DateUtils {


    public List<String> getMonthList(){
        return new ArrayList<>(List.of(
                "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"
        ));
    }

    public List<String> getMonthNumberList() {
        return new ArrayList<>(List.of(
                "01", "02", "03", "04","05","06","07","08","09","10","11","12"
        ));
    }

    public int getMonthNumber(String month){
        List<String> months = getMonthList();
        return months.indexOf(month)+1;
    }

    public String getMonthName(int month){
        List<String> months = getMonthList();
        return months.get(month - 1);
    }

    public LocalDate convertToDate(String date){
        String result[] = date.split(" ");
        //System.out.println(Integer.parseInt(result[2]));

        return LocalDate.of(Integer.parseInt(result[2]),getMonthNumber(result[1]),Integer.parseInt(result[0]));
    }

    public String convertToString(LocalDate date){
        if(date.getDayOfMonth() < 10){
            return "0"+date.getDayOfMonth()+" "+getMonthName(date.getMonthValue())+" "+date.getYear();
        };
        return date.getDayOfMonth()+" "+getMonthName(date.getMonthValue())+" "+date.getYear();
    }


}
