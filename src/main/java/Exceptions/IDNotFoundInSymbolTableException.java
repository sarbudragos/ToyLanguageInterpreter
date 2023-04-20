package Exceptions;

public class IDNotFoundInSymbolTableException extends InterpreterException{
    public IDNotFoundInSymbolTableException(String errorMessage) {
        super(errorMessage);
    }
}
