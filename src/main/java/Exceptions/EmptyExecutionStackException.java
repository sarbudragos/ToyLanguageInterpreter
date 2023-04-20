package Exceptions;

public class EmptyExecutionStackException extends InterpreterException{
    public EmptyExecutionStackException(String errorMessage) {
        super(errorMessage);
    }
}
