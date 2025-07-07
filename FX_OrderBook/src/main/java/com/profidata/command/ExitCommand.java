package com.profidata.command;

import com.profidata.command.types.CommandWithoutArguments;

public class ExitCommand implements CommandWithoutArguments {
    public static ExitCommand INSTANCE;

    //SINGLETON
    private ExitCommand(){}

    @Override
    public String getName() {
        return "exit";
    }

    @Override
    public String getDescription() {
        return "Stops the program";
    }

    @Override
    public void execute() {
        System.exit(0);
    }

    public static ExitCommand getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new ExitCommand();
        }

        return INSTANCE;
    }
}
