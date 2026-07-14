package com.example.demo.tools_arguments;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;

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
    public String getBalance(
            // @ToolParam(description = "Unit (Dollar, peso)", required = true) String unit,
            @ToolParam(description = "Currency /Dollars, Pesos)", required = true) String currency
    ){
        return "Your current balance is:" + Math.round(Math.random() * 10000) / 100.0 + " in " + currency;
    }

    @Tool(description = "This function is used to get the number of tickets by priority.")
    public String getTicketsByPriority(
            @ToolParam(description = "Ticket Priority ", required = true) TicketPriority priority
    ){
        return "Your number of tickets are:" + Math.round(Math.random() * 100)  + " with priority " + priority;
    }

    @Tool(description = "This function is used to set the user appointment")
    public String setAppointment( int year, int month, int day, int hour, int minute) {

        LocalDate localDate = LocalDate.of(year, month, day);
        LocalTime localTime = LocalTime.of(hour, minute);
        return "Your schedule is already set to :" + localDate + " at time " + localTime;
    }

    public enum TicketPriority {
        LOW(1), MEDIUM(2), HIGH(3), URGENT(4);
        // 2. Create a final field to store the integer value
        private final int value;

        // 3. Define a private constructor (implicitly private)
        TicketPriority(int value) {
            this.value = value;
        }

        // 4. Create a getter method to retrieve the value
        public int getValue() {
            return this.value;
        }

        // 5. Optional: Static method to find an enum by its int value
        public static TicketPriority fromInt(int value) {
            for (TicketPriority code : TicketPriority.values()) {
                if (code.getValue() == value) {
                    return code;
                }
            }
            throw new IllegalArgumentException("Unknown status code: " + value);
        }
    }

}
