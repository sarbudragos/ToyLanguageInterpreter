package Exceptions;

public class InvalidTypeException extends InterpreterException{
    public InvalidTypeException(String errorMessage) {
        super(errorMessage);
    }
}
