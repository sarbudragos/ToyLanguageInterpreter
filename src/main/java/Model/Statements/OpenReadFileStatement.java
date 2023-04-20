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
import java.io.FileReader;

public class OpenReadFileStatement implements Statement{

    Expression expression;

    public OpenReadFileStatement(Expression expression)
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

        String fileName = ((StringValue) result).getValue();

        if(fileTable.containsKey((StringValue) result))
        {
            throw new InterpreterException("File already open.");
        }

        BufferedReader file = null;
        try {
            file = new BufferedReader(new FileReader(fileName));
        }
        catch (Exception e)
        {
            throw new InterpreterException("File does not exist.");
        }

        fileTable.put((StringValue) result, file);

        return null;
    }

    @Override
    public DictionaryInterface<String, Type> typeCheck(DictionaryInterface<String, Type> typeEnvironment) throws InterpreterException {
        Type typeOfExpression = expression.typeCheck(typeEnvironment);
        if (typeOfExpression.equals(new StringType())) {
            return typeEnvironment;
        }
        else
            throw new InvalidTypeException("Open file statement: argument is not a string.");
    }

    @Override
    public Statement deepCopy() {
        return new OpenReadFileStatement(expression.deepCopy());
    }

    @Override
    public String toString() {
        return "openReadFile( " + expression.toString() + " )";
    }
}
