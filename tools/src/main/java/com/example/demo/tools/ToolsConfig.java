package com.example.demo.tools;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class ToolsConfig {

    @Tool(description = "This function is used to get the current Date")
    public String getDate(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate today = LocalDate.now();
        return today.format(formatter);
    }

    @Tool(description = "This function is used to get the current Time")
    public String getTime(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:SS");
        LocalTime localTime = LocalTime.now();
        return "The current time is: " + localTime.format(formatter);
    }

    @Tool(description = "This function is used to get the user balance")
    public String getBalance(){

        return "Your current balance is:" + Math.round(Math.random() * 10000) / 100.0;
    }

}
