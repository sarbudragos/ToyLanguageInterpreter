package Model.Statements;

import Exceptions.InterpreterException;
import Exceptions.InvalidTypeException;
import Model.ADTs.DictionaryInterface;
import Model.Expressions.Expression;
import Model.ProgramState.ProgramState;
import Model.Types.IntType;
import Model.Types.Type;
import Model.Values.IntValue;
import Model.Values.Value;
import javafx.util.Pair;

import java.util.ArrayList;

public class CreateSemaphoreStatement implements Statement {
    Expression expression;
    String variableName;

    public CreateSemaphoreStatement(String variableName, Expression expression)
    {
        this.expression = expression;
        this.variableName = variableName;
    }

    @Override
    public synchronized ProgramState execute(ProgramState programState) throws InterpreterException {
        Value number = expression.evaluate(programState.getSymbolTable(), programState.getMemoryHeap());

        if(!number.getType().equals(new IntType())) {
            throw new InvalidTypeException("Create semaphore: Expression must evaluate to int.");
        }

        if(!programState.getSymbolTable().containsKey(variableName)) {
            throw new InvalidTypeException("Create semaphore: Variable name not found.");
        }
        if(!programState.getSymbolTable().get(variableName).getType().equals(new IntType()))
        {
            throw new InvalidTypeException("Create semaphore: Variable is not of type int.");
        }

        int newFreeLocation = programState.getSemaphoreTable().put(new Pair<>(((IntValue) number).getValue(), new ArrayList<>()));
        programState.getSymbolTable().put(variableName, new IntValue(newFreeLocation));

        return null;
    }

    @Override
    public DictionaryInterface<String, Type> typeCheck(DictionaryInterface<String, Type> typeEnvironment) throws InterpreterException {
        Type expressionType = expression.typeCheck(typeEnvironment);

        if(!expressionType.equals(new IntType())) {
            throw new InvalidTypeException("Create semaphore: Expression must evaluate to int.");
        }

        if(!typeEnvironment.get(variableName).equals(new IntType()))
        {
            throw new InvalidTypeException("Create semaphore: Variable is not of type int.");
        }

        return typeEnvironment;
    }

    @Override
    public Statement deepCopy() {
        return new CreateSemaphoreStatement(variableName, expression.deepCopy());
    }

    @Override
    public String toString() {
        return "createSemaphore( " + expression + ", " + variableName + " )";
    }
}
