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

import java.util.HashMap;
import java.util.Map;

public class HeapWriteStatement implements Statement{
    String variableName;
    Expression expression;

    public HeapWriteStatement(String variableName, Expression expression)
    {
        this.variableName = variableName;
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState programState) throws InterpreterException {

        DictionaryInterface<String, Value> symbolTable = programState.getSymbolTable();
        HeapInterface memoryHeap = programState.getMemoryHeap();

        if(symbolTable.containsKey(variableName)){
            Value expressionValue = expression.evaluate(symbolTable, programState.getMemoryHeap());
            Type variableType = symbolTable.get(variableName).getType();

            if(!(variableType instanceof ReferenceType))
            {
                throw new InvalidTypeException("Variable must have reference type.");
            }

            int address = ((ReferenceValue)symbolTable.get(variableName)).getAddress();

            if(!memoryHeap.getContent().containsKey(address))
            {
                throw new InterpreterException("Address not found in heap.");
            }

            Type innerType = ((ReferenceType) variableType).getInnerType();
            if(!(expressionValue.getType().equals(innerType)))
            {
                throw new InvalidTypeException("Expression type does not match reference type.");
            }

            Map<Integer, Value> memoryHeapContent = memoryHeap.getContent();
            memoryHeapContent.put(address, expressionValue);
            memoryHeap.setContent((HashMap<Integer, Value>) memoryHeapContent);
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
            throw new InvalidTypeException("heapWrite statement: right hand side and left hand side have different types ");
    }

    @Override
    public String toString() {
        return "writeHeap( " + variableName + ", " + expression.toString() + " )";
    }

    @Override
    public Statement deepCopy() {
        return new HeapWriteStatement(variableName, expression.deepCopy());
    }
}
