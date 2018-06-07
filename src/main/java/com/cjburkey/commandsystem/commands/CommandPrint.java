package com.cjburkey.commandsystem.commands;

import com.cjburkey.commandsystem.Command;
import com.cjburkey.commandsystem.Parameter;
import com.cjburkey.commandsystem.TypeHandler;

public class CommandPrint extends Command {
    
    public CommandPrint() {
        super("print", 1, new Parameter[] { new Parameter("message", TypeHandler.STRING) });
        description = "Prints the provided message to the screen";
    }

    public String execute(Command parentCommand, Object[] arguments) {
        return (String) arguments[0];
    }
    
}