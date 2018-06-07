package com.cjburkey.commandsystem;

import java.util.HashSet;
import java.util.Set;

public final class TypeHandler {
    
    public static final TypeHandler INSTANCE = new TypeHandler();
    
    public static final Type<Byte> INTEGER_8 = new Type<>("int8", Byte.class, (input) -> { try { return new Result<>(Byte.parseByte(input)); } catch (Exception e) { return new Result<Byte>(); } });
    public static final Type<Short> INTEGER_16 = new Type<>("int16", Short.class, (input) -> { try { return new Result<>(Short.parseShort(input)); } catch (Exception e) { return new Result<Short>(); } });
    public static final Type<Integer> INTEGER_32 = new Type<>("int32", Integer.class, (input) -> { try { return new Result<>(Integer.parseInt(input)); } catch (Exception e) { return new Result<Integer>(); } });
    public static final Type<Long> INTEGER_64 = new Type<>("int64", Long.class, (input) -> { try { return new Result<>(Long.parseLong(input)); } catch (Exception e) { return new Result<Long>(); } });
    public static final Type<Float> FLOAT_32 = new Type<>("float32", Float.class, (input) -> { try { return new Result<>(Float.parseFloat(input)); } catch (Exception e) { return new Result<Float>(); } });
    public static final Type<Double> FLOAT_64 = new Type<>("float64", Double.class, (input) -> { try { return new Result<>(Double.parseDouble(input)); } catch (Exception e) { return new Result<Double>(); } });
    public static final Type<Boolean> BOOLEAN = new Type<>("bool", Boolean.class, (input) -> { try { return new Result<>(Boolean.parseBoolean(input)); } catch (Exception e) { return new Result<Boolean>(); } });
    public static final Type<String> STRING = new Type<>("string", String.class, (input) -> { return new Result<>(input); });
    public static final Type<Character> CHAR = new Type<>("char", Character.class, (input) -> { if (input.length() != 1) { return new Result<Character>(); } return new Result<>(input.toCharArray()[0]); });
    
    private final Set<Type<?>> types;
    
    private TypeHandler() {
        types = new HashSet<>();

        types.add(INTEGER_8);
        types.add(INTEGER_16);
        types.add(INTEGER_32);
        types.add(INTEGER_64);
        types.add(FLOAT_32);
        types.add(FLOAT_64);
        types.add(BOOLEAN);
        types.add(STRING);
        types.add(CHAR);
    }
    
    /**
     * Locates the type with the provided name
     * @param name The name of the type to locate
     * @return The located type, or <code>null</code> if no type has that name
     */
    public Type<?> getTypeByName(String name) {
        for (Type<?> type : types) {
            if (type.name.equals(name)) {
                return type;
            }
        }
        return null;
    }
    
    /**
     * Locates the type that represents the provided input object
     * @param input The object for which to locate the type
     * @return The type of the object, or <code>null</code> on failure
     */
    public Type<?> getType(Object input) {
        for (Type<?> type : types) {
            if (type.getIsType(input)) {
                return type;
            }
        }
        return null;
    }
    
}