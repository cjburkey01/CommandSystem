import com.cjburkey.commandsystem.CommandSystem;
import com.cjburkey.commandsystem.commands.CommandPrint;

public class TestCommandSystem {
    
    // TODO: Test the command system
    public static void main(String[] args) {
        CommandSystem.getTopLevelSystem().registerCommand(new CommandPrint());
        System.out.println(CommandSystem.executeCommands("help"));
        System.out.println();
        
        System.out.println(CommandSystem.executeCommands("print \"si lo\""));
        System.out.println();
        
        System.out.println(CommandSystem.executeCommands("bob \"si lo\""));
        System.out.println();
        
        System.out.println(CommandSystem.executeCommands("help print"));
        System.out.println();
    } 
    
}