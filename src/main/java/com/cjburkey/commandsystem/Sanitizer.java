package com.cjburkey.commandsystem;

import java.util.regex.Pattern;

public class Sanitizer {
    
    /**
     * Ensures that the input character sequence is started by any alphabetic character or underscore, and is optionally followed by an infinite number of alpha-numeric characters (including underscores)
     * @param input The input string
     * @return Whether the input string is valid
     */
    public boolean checkClean(String input) {
        return Pattern.matches("([A-Za-z_]{1})[A-Za-z0-9_]*", input);
    }
    
}