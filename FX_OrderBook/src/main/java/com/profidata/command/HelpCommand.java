package com.profidata.command;

import com.profidata.command.types.Command;
import com.profidata.command.types.CommandWithoutArguments;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class HelpCommand implements CommandWithoutArguments {
    private static HelpCommand INSTANCE;
    private final List<Command> commands;

    //SINGLETON
    private HelpCommand(){
        //add all commands in the list
        commands = new ArrayList<>();
        commands.add(CancelCommand.getInstance());
        commands.add(ExitCommand.getInstance());
        commands.add(this);
        commands.add(NewCommand.getInstance());
        commands.add(OrdersCommand.getInstance());
        commands.add(RatesCommand.getInstance());
        commands.add(SummaryCommand.getInstance());

        //maintains the list of commands ordered alphabetically
        commands.sort(Comparator.comparing(Command::getName));
    }

    public String getName(){
        return "help";
    }

    public String getDescription() {
        return "Provides list of all commands and how to use them";
    }

    //prints all commands and their corresponding description
    public void execute() {
        System.out.println("Available Commands: \n");

        for(Command command : commands) {
            System.out.printf("\t%-70s %s%-100s\n",
                    command.getName(),
                    "- ",
                    command.getDescription());
        }

    }

    public static HelpCommand getInstance(){
        if(INSTANCE == null) {
            INSTANCE = new HelpCommand();
        }

        return INSTANCE;
    }
}
