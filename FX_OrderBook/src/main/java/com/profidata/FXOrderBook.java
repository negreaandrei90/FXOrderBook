package com.profidata;


import com.profidata.command.*;

import java.util.Scanner;

public class FXOrderBook {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String command;
        String mainCommand;

        System.out.println("Welcome to FX BookOrder!\nWhat would you like to do?\nIf you need an overview of the commands, type 'help'");
        while(true) {
            command = scanner.nextLine();   //read command
            String[] commandParts = command.trim().split("\\s+");

            if(commandParts.length == 0) {
                System.out.println("Please enter a command.");
            } else {
                mainCommand = commandParts[0];  // new | cancel | rates | orders | summary | help | exit
                switch(mainCommand) {
                    case "new":
                        NewCommand newCommand = NewCommand.getInstance();
                        newCommand.execute(commandParts);
                        break;
                    case "cancel":
                        CancelCommand cancelCommand = CancelCommand.getInstance();
                        cancelCommand.execute(commandParts);
                        break;
                    case "rates":
                        RatesCommand ratesCommand = RatesCommand.getInstance();
                        ratesCommand.execute();
                        break;
                    case "orders":
                        OrdersCommand ordersCommand = OrdersCommand.getInstance();
                        ordersCommand.execute();
                        break;
                    case "summary":
                        SummaryCommand summaryCommand = SummaryCommand.getInstance();
                        summaryCommand.execute();
                        break;
                    case "help":
                        HelpCommand helpCommand = HelpCommand.getInstance();
                        helpCommand.execute();
                        break;
                    case "exit":
                        ExitCommand exitCommand = ExitCommand.getInstance();
                        exitCommand.execute();
                    default:
                        System.out.println(mainCommand + " is an invalid command");
                }
            }
        }
    }
}