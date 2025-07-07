package com.profidata.command.types;

public interface CommandWithArguments extends Command {
    void execute(String[] args);
}
