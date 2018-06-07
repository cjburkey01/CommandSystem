package com.cjburkey.commandsystem;

public class Type<T> {
    
    public final String name;
    public final Class<T> type;
    public final TypeParse<T> parser;
    
    public Type(String name, Class<T> type, TypeParse<T> parser) {
        this.name = name;
        this.type = type;
        this.parser = parser;
    }
    
    /**
     * Checks whether the provided object is of this type or a child of this type
     * @param input The object to check
     * @return <code>true</code> if the object is an instance of this type or a child type, <code>false</code> if not
     */
    public boolean getIsType(Object input) {
        if (input == null) {
            return false;
        }
        return type.isAssignableFrom(input.getClass());
    }
    
    /**
     * Attempts to cast the provided object to this type.
     * @param input The object to cast
     * @return A result of the operation, which contains whether it succeeded or failed
     */
    public Result<T> cast(Object input) {
        if (!getIsType(input)) {
            return new Result<>();
        }
        return new Result<>(type.cast(input));
    }
    
    /**
     * Attempts to parse the provided string into this type
     * @param input The string to parse
     * @return A result of the operation, which contains whether it succeeded or failed
     */
    public Result<T> parse(String input) {
        return parser.parse(input);
    }
    
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((parser == null) ? 0 : parser.hashCode());
        result = prime * result + ((type == null) ? 0 : type.hashCode());
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
        Type<?> other = (Type<?>) obj;
        if (name == null) {
            if (other.name != null) {
                return false;
            }
        } else if (!name.equals(other.name)) {
            return false;
        }
        if (parser == null) {
            if (other.parser != null) {
                return false;
            }
        } else if (!parser.equals(other.parser)) {
            return false;
        }
        if (type == null) {
            if (other.type != null) {
                return false;
            }
        } else if (!type.equals(other.type)) {
            return false;
        }
        return true;
    }
    
}