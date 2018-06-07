package com.cjburkey.commandsystem.commands;

import com.cjburkey.commandsystem.Command;
import com.cjburkey.commandsystem.Parameter;
import com.cjburkey.commandsystem.TypeHandler;

/**
 * A default command that is automatically added to each command system (that has the parameter set to <code>true</code>) to display information about the system
 * @author cjburkey
 */
public class CommandHelp extends Command {
    
    public CommandHelp() {
        super("help", 0, new Parameter("command", TypeHandler.STRING));
        this.description = "Displays help information about the possible commands or the provided command";
    }
    
    public String execute(Command parentCommand, Object[] arguments) {
        if (arguments.length == 0) {
            StringBuilder output = new StringBuilder();
            for (Command command : getParentSystem().getRegisteredCommands()) {
                output.append(command.getUsage(getParentSystem().prefix));
                if (command.description != null) {
                    output.append('\n');
                    output.append(' ');
                    output.append(' ');
                    output.append(command.description);
                }
                output.append('\n');
            }
            output.deleteCharAt(output.length() - 1);
            return output.toString();
        }
        Command cmd = getParentSystem().getCommand((String) arguments[0]);
        if (cmd == null) {
            return "Command not found";   // TODO: COMMAND NOT FOUND
        }
        return cmd.getUsage(getParentSystem().prefix) + "\n  " + cmd.description;
    }
    
}