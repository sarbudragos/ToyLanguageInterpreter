package Model.Statements;

import Exceptions.InterpreterException;
import Exceptions.InvalidTypeException;
import Model.ADTs.DictionaryInterface;
import Model.ProgramState.ProgramState;
import Model.Types.IntType;
import Model.Types.Type;
import Model.Values.IntValue;
import javafx.util.Pair;

import java.util.List;

public class AcquireStatement implements Statement {

    String semaphoreName;

    public AcquireStatement(String semaphoreName){
        this.semaphoreName = semaphoreName;
    }

    @Override
    public synchronized ProgramState execute(ProgramState programState) throws InterpreterException {

        if(!programState.getSymbolTable().containsKey(semaphoreName))
        {
            throw new InvalidTypeException("Acquire semaphore: semaphore name not found.");
        }
        int index = ((IntValue) programState.getSymbolTable().get(semaphoreName)).getValue();

        Pair<Integer, List<Integer>> semaphore = programState.getSemaphoreTable().get(index);

        if(semaphore == null){
            throw new InvalidTypeException("Acquire semaphore: index not found in semaphore table.");
        }

        if(semaphore.getValue().size() < semaphore.getKey())
        {
            if (!semaphore.getValue().contains(programState.getId()))
            {
                semaphore.getValue().add(programState.getId());
            }
        }
        else
        {
            programState.getExecutionStack().push(this);
        }

        return null;
    }

    @Override
    public DictionaryInterface<String, Type> typeCheck(DictionaryInterface<String, Type> typeEnvironment) throws InterpreterException {
        if(!typeEnvironment.get(semaphoreName).equals(new IntType()))
        {
            throw new InvalidTypeException("Create semaphore: Variable is not of type int.");
        }

        return typeEnvironment;
    }

    @Override
    public Statement deepCopy() {
        return new AcquireStatement(semaphoreName);
    }

    @Override
    public String toString() {
        return "acquire( " + semaphoreName + " )";
    }
}
