package com.cjburkey.commandsystem;

@FunctionalInterface
public interface TypeParse<T> {
    
    Result<T> parse(String input);
    
}