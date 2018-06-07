package com.cjburkey.commandsystem;

import java.util.Arrays;

public abstract class Command {
    
    public final String name;
    public final int requiredArguments;
    public final Parameter[] arguments;
    private CommandSystem subCommand;
    private CommandSystem parentSystem;
    private boolean hasSubCommand;
    public String description = null;
    
    protected Command(String name) {
        this(name, 0, true);
    }
    
    protected Command(String name, int requiredArguments, Parameter... arguments) {
        this(name, requiredArguments, false, arguments);
    }
    
    private Command(String name, int requiredArguments, boolean hasSubCommand, Parameter... arguments) {
        this.name = name;
        this.requiredArguments = requiredArguments;
        this.arguments = arguments;
        this.hasSubCommand = hasSubCommand;
    }
    
    public final void onRegister(CommandSystem parentSystem) {
        this.parentSystem = parentSystem;
        if (hasSubCommand) {
            subCommand = new CommandSystem(true, parentSystem.prefix + ' ' + name);
        }
    }
    
    /**
     * Executes this command
     * @param parentCommand The parent command, or <code>null</code> if there is not one
     * @param parentSystem The command system that called this command
     * @param arguments The array of arguments passed to the command. Guaranteed to be at least <code>requiredArguments</code> long, each the correct type, and in the correct order
     * @return A string to display to the user, or <code>null</code> if the command is silent
     */
    public abstract String execute(Command parentCommand, Object[] arguments);
    
    /**
     * Checks whether this command has sub commands
     * @return Whether this command has a command handler
     */
    public boolean getHasSubCommands() {
        return hasSubCommand && subCommand != null;
    }
    
    public CommandSystem getSubCommand() {
        return subCommand;
    }
    
    public CommandSystem getParentSystem() {
        return parentSystem;
    }
    
    /**
     * Returns a string displaying the usage of this command
     * @param prefix Prepended text for the usage
     * @return The final usage string
     */
    public String getUsage(String prefix) {
        StringBuilder output = new StringBuilder();
        output.append(prefix);
        if (!prefix.isEmpty()) {
            output.append(' ');
        }
        output.append(name);
        if (getHasSubCommands()) {
            return subCommand.getHelp();
        }
        for (int i = 0; i < arguments.length; i ++) {
            output.append(' ');
            if (i == requiredArguments) {
                output.append('[');
            }
            output.append(arguments[i].name);
            output.append(':');
            output.append(arguments[i].type.name);
        }
        if (arguments.length > requiredArguments) {
            output.append(']');
        }
        return output.toString();
    }
    
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Arrays.hashCode(arguments);
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + requiredArguments;
        result = prime * result + ((subCommand == null) ? 0 : subCommand.hashCode());
        return result;
    }
    
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Command other = (Command) obj;
        if (!Arrays.equals(arguments, other.arguments)) {
            return false;
        }
        if (name == null) {
            if (other.name != null) {
                return false;
            }
        } else if (!name.equals(other.name)) {
            return false;
        }
        if (requiredArguments != other.requiredArguments) {
            return false;
        }
        if (subCommand == null) {
            if (other.subCommand != null) {
                return false;
            }
        } else if (!subCommand.equals(other.subCommand)) {
            return false;
        }
        return true;
    }
    
}