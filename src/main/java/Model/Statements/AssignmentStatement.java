package Model.Statements;

import Exceptions.InterpreterException;
import Exceptions.InvalidTypeException;
import Model.ADTs.DictionaryInterface;
import Model.Expressions.Expression;
import Model.ProgramState.ProgramState;
import Model.Types.Type;
import Model.Values.Value;

public class AssignmentStatement implements Statement{
    String variableName;
    Expression expression;

    public AssignmentStatement(String variableName, Expression expression){
        this.variableName = variableName;
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState programState) throws InterpreterException {
        DictionaryInterface<String, Value> symbolTable = programState.getSymbolTable();

        if(symbolTable.containsKey(variableName)){
            Value expressionValue = expression.evaluate(symbolTable, programState.getMemoryHeap());
            Type variableType = symbolTable.get(variableName).getType();

            if(expressionValue.getType().equals((variableType))){
                symbolTable.put(variableName, expressionValue);
            }
            else
                throw new InvalidTypeException("Declared type of variable " + variableName +
                        " and type of the assigned expression do not match.");
        }
        else
            throw new InvalidTypeException("The used variable " + variableName + " was not declared before.");

        return null;
    }

    @Override
    public DictionaryInterface<String, Type> typeCheck(DictionaryInterface<String, Type> typeEnvironment) throws InterpreterException {
        Type typeOfVariable = typeEnvironment.get(variableName);
        Type typeOfExpression = expression.typeCheck(typeEnvironment);
        if (typeOfVariable.equals(typeOfExpression)){
            return typeEnvironment;
        }
        else {
            throw new InvalidTypeException("Assignment: right hand side and left hand side have different types ");
        }
    }

    @Override
    public Statement deepCopy() {
        return new AssignmentStatement(variableName, expression.deepCopy());
    }

    @Override
    public String toString() {
        return variableName + " = " + expression.toString();
    }
}
