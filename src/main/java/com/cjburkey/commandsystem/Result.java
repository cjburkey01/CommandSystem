package com.cjburkey.commandsystem;

public final class Result<T> {
    
    public final boolean failed;
    public final T object;
    
    public Result() {
        this(true, null);
    }
    
    public Result(T object) {
        this(false, object);
    }
    
    private Result(boolean failed, T object) {
        this.failed = failed;
        this.object = object;
    }
    
    public static <T> Result<T> failedObject(T object) {
        return new Result<>(true, object);
    }
    
}