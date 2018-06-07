package com.cjburkey.commandsystem;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import com.cjburkey.commandsystem.commands.CommandHelp;

public class CommandSystem {
    
    private static CommandSystem parent;
    
    public final String prefix;
    private final List<Command> commands;
    
    public CommandSystem(boolean addHelp, String prefix) {
        this.prefix = prefix;
        commands = new LinkedList<>();
        if (addHelp) {
            registerCommand(new CommandHelp());
        }
    }
    
    public boolean registerCommand(Command command) {
        if (getCommand(command.name) != null) {
            return false;
        }
        commands.add(command);
        command.onRegister(this);
        return true;
    }
    
    public Command getCommand(String name) {
        for (Command command : commands) {
            if (command.name.equalsIgnoreCase(name)) {
                return command;
            }
        }
        return null;
    }
    
    public Command[] getRegisteredCommands() {
        return commands.toArray(new Command[commands.size()]);
    }
    
    public String getHelp() {
        StringBuilder output = new StringBuilder();
        output.append(prefix);
        if (!prefix.isEmpty()) {
            output.append(' ');
        }
        for (Command command : commands) {
            output.append(command.name);
            output.append('/');
        }
        output.deleteCharAt(output.length() - 1);
        return output.toString();
    }
    
    public String executeCommands(Command parentCommand, String input) {
        if (input == null || (input = input.trim()).isEmpty()) {
            return null;    // Empty or problematic input
        }
        String[] cmds = input.split(Pattern.quote(";"));
        if (cmds.length < 1) {
            return null;    // No commands entered
        }
        StringBuilder output = new StringBuilder();
        for (String cmd : cmds) {
            if (cmd == null || (cmd = cmd.trim()).isEmpty()) {
                continue;
            }
            Result<String> ret = handleCommand(parentCommand, cmd);
            if (ret.failed) {
                if (ret.object == null) {
                    output.append("That command was not found");
                } else {
                    output.append(ret.object);
                }
                output.append('\n');
            }
            if (ret.object != null) {   // Commands can return null to signal a silent command
                output.append(ret.object);
                output.append('\n');
            }
        }
        if (output.toString().endsWith("\n")) {
            output.deleteCharAt(output.length() - 1);   // Remove trailing newline
        }
        return output.toString();
    }
    
    private Result<String> handleCommand(Command parentCommand, String cmd) {
        String[] spl = splitCommand(cmd = cmd.trim());
        if (spl.length < 1) {   // Shouldn't happen, but it is best to be safe
            return new Result<String>();
        }
        Command command = getCommand(spl[0]);
        if (command == null) {
            return new Result<String>();    // Command does not exist
        }
        if (command.getHasSubCommands()) {
            if (spl.length - 1 < 1) {
                return Result.failedObject(command.getUsage(prefix));
            }
            return new Result<>(command.getSubCommand().executeCommands(command, cmd.substring(cmd.indexOf(' ')).trim()));  // Execute sub commands
        }
        if ((spl.length - 1) < command.requiredArguments || (spl.length - 1) > command.arguments.length) {
            return Result.failedObject(command.getUsage(prefix));   // Too few or too many arguments
        }
        Object[] arguments = new Object[spl.length - 1];
        for (int i = 0; (i < command.arguments.length) && (i < (spl.length - 1)); i ++) {
            Result<?> value = command.arguments[i].type.parse(spl[i + 1]);
            if (value.failed) {
                return Result.failedObject(command.getUsage(prefix));   // Incorrect type argument
            }
            arguments[i] = value.object;
        }
        return new Result<>(command.execute(parentCommand, arguments));
    }
    
    private static String[] splitCommand(String input) {
        List<String> list = new ArrayList<String>();
        Matcher m = Pattern.compile("([^\"]\\S*|\".+?\")\\s*").matcher(input);
        while (m.find()) {
            list.add(m.group(1).replaceAll(Pattern.quote("\""), ""));
        }
        return list.toArray(new String[list.size()]);
    }
    
    public static String executeCommands(String input) {
        return getTopLevelSystem().executeCommands(null, input);
    }
    
    public static CommandSystem getTopLevelSystem() {
        if (parent == null) {
            parent = new CommandSystem(true, "");
        }
        return parent;
    }
    
}