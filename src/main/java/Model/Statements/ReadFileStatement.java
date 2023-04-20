package Model.Statements;

import Exceptions.IDNotFoundInSymbolTableException;
import Exceptions.InterpreterException;
import Exceptions.InvalidTypeException;
import Model.ADTs.DictionaryInterface;
import Model.Expressions.Expression;
import Model.ProgramState.ProgramState;
import Model.Types.IntType;
import Model.Types.StringType;
import Model.Types.Type;
import Model.Values.IntValue;
import Model.Values.StringValue;
import Model.Values.Value;

import java.io.BufferedReader;
import java.io.IOException;

public class ReadFileStatement implements Statement{
    Expression expression;
    String variableName;

    public ReadFileStatement(Expression expression, String variableName){
        this.expression = expression;
        this.variableName = variableName;
    }

    @Override
    public ProgramState execute(ProgramState programState) throws InterpreterException {
        DictionaryInterface<StringValue, BufferedReader> fileTable = programState.getFileTable();
        DictionaryInterface<String, Value> symbolTable = programState.getSymbolTable();

        if(!symbolTable.containsKey(variableName)){
            throw new IDNotFoundInSymbolTableException("Variable not found.");
        }
        if(!symbolTable.get(variableName).equals(new IntValue(0))){
            throw new InvalidTypeException("Variable type must be int.");
        }
        Value result = expression.evaluate(programState.getSymbolTable(), programState.getMemoryHeap());

        if(!result.getType().equals(new StringType()))
        {
            throw new InvalidTypeException("File name must be a string.");
        }

        if(!fileTable.containsKey((StringValue) result))
        {
            throw new InterpreterException("File not opened.");
        }

        BufferedReader file = fileTable.get((StringValue) result);

        try
        {
            String readLine = file.readLine();
            IntValue readValue;
            if(readLine == null || readLine.isBlank())
            {
                readValue = new IntValue(0);
            }
            else
            {
                readValue = new IntValue(Integer.parseInt(readLine));
            }

            symbolTable.put(variableName, readValue);
        }
        catch (IOException e)
        {
            throw new InterpreterException("Read from file error.");
        }
        catch (NumberFormatException e)
        {
            throw new InvalidTypeException("The read value is not an integer.");
        }

        return null;
    }

    @Override
    public DictionaryInterface<String, Type> typeCheck(DictionaryInterface<String, Type> typeEnvironment) throws InterpreterException {
        Type typeOfVariable = typeEnvironment.get(variableName);
        Type typeOfExpression = expression.typeCheck(typeEnvironment);

        if (!typeOfVariable.equals(new IntType())) {
            throw new InvalidTypeException("Read file statement: variable is not an int.");
        }

        if (typeOfExpression.equals(new StringType())) {
            return typeEnvironment;
        }
        else
            throw new InvalidTypeException("Read file statement: file name is not a string.");

    }

    @Override
    public Statement deepCopy() {
        return new ReadFileStatement(expression.deepCopy(), variableName);
    }

    @Override
    public String toString() {
        return "readFile( " + expression.toString() + ", " + variableName + " )";
    }
}
