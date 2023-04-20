package Model.Statements;

import Exceptions.InterpreterException;
import Exceptions.InvalidTypeException;
import Model.ADTs.DictionaryInterface;
import Model.Expressions.Expression;
import Model.ProgramState.ProgramState;
import Model.Types.StringType;
import Model.Types.Type;
import Model.Values.StringValue;
import Model.Values.Value;

import java.io.BufferedReader;
import java.io.IOException;

public class CloseFileStatement implements Statement{
    Expression expression;

    public CloseFileStatement(Expression expression)
    {
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState programState) throws InterpreterException {
        DictionaryInterface<StringValue, BufferedReader> fileTable = programState.getFileTable();

        Value result = expression.evaluate(programState.getSymbolTable(), programState.getMemoryHeap());

        if(!result.getType().equals(new StringType()))
        {
            throw new InvalidTypeException("File name must be a string.");
        }

        if(!fileTable.containsKey((StringValue) result))
        {
            throw new InterpreterException("File already open.");
        }

        BufferedReader fileReader = fileTable.get((StringValue) result);
        try
        {
            fileReader.close();
        }
        catch (IOException e)
        {
            throw new InterpreterException("File cannot be closed.");
        }

        fileTable.remove((StringValue) result);

        return null;
    }

    @Override
    public DictionaryInterface<String, Type> typeCheck(DictionaryInterface<String, Type> typeEnvironment) throws InterpreterException {
        Type typeOfExpression = expression.typeCheck(typeEnvironment);
        if (typeOfExpression.equals(new StringType())){
            return typeEnvironment;
        }
        else {
            throw new InvalidTypeException("Close File: file name expression does not evaluate to string. ");
        }
    }

    @Override
    public Statement deepCopy() {
        return new CloseFileStatement(expression.deepCopy());
    }

    @Override
    public String toString() {
        return "closeReadFile( " + expression.toString() + " )";
    }
}
