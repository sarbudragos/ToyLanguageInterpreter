package Model.Statements;

import Exceptions.InterpreterException;
import Exceptions.InvalidTypeException;
import Model.ADTs.DictionaryInterface;
import Model.ADTs.HeapInterface;
import Model.Expressions.Expression;
import Model.ProgramState.ProgramState;
import Model.Types.ReferenceType;
import Model.Types.Type;
import Model.Values.ReferenceValue;
import Model.Values.Value;

public class HeapAllocationStatement implements Statement{
    String variableName;
    Expression expression;

    public HeapAllocationStatement(String variableName, Expression expression)
    {
        this.variableName = variableName;
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState programState) throws InterpreterException {
        DictionaryInterface<String, Value> symbolTable = programState.getSymbolTable();
        HeapInterface heap = programState.getMemoryHeap();

        if(symbolTable.containsKey(variableName)){
            Value expressionValue = expression.evaluate(symbolTable,programState.getMemoryHeap());
            Type variableType = symbolTable.get(variableName).getType();

            if(!(variableType instanceof ReferenceType))
            {
                throw new InvalidTypeException("Variable must be a reference type.");
            }
            if(!(expressionValue.getType().equals(((ReferenceType) variableType).getInnerType())))
            {
                throw new InvalidTypeException("Inner type of reference variable and expression do not match.");
            }

            int address = heap.insert(expressionValue);

            symbolTable.put(variableName, new ReferenceValue(address, ((ReferenceType) variableType).getInnerType()));
        }
        else
            throw new InvalidTypeException("The used variable " + variableName + " was not declared before.");

        return null;
    }

    @Override
    public DictionaryInterface<String, Type> typeCheck(DictionaryInterface<String, Type> typeEnvironment) throws InterpreterException {
        Type typeOfVariable = typeEnvironment.get(variableName);
        Type typeOfExpression = expression.typeCheck(typeEnvironment);
        if (typeOfVariable.equals(new ReferenceType(typeOfExpression))) {
            return typeEnvironment;
        }
        else
            throw new InvalidTypeException("NEW statement: right hand side and left hand side have different types ");
    }

    @Override
    public String toString() {
        return "new( " + variableName + ", " + expression.toString() + " )";
    }

    @Override
    public Statement deepCopy() {
        return new HeapAllocationStatement(variableName, expression.deepCopy());
    }
}
