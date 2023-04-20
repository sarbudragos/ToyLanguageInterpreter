package Model.Statements;

import Exceptions.InterpreterException;
import Model.ADTs.DictionaryInterface;
import Model.ADTs.MyDictionary;
import Model.ADTs.MyStack;
import Model.ADTs.StackInterface;
import Model.ProgramState.ProgramState;
import Model.Types.Type;
import Model.Values.Value;

public class ForkStatement implements Statement{
    Statement internalStatement;

    public ForkStatement(Statement internalStatement)
    {
        this.internalStatement = internalStatement;
    }

    @Override
    public ProgramState execute(ProgramState programState) throws InterpreterException {
        MyStack<Statement> newExecutionStack = null;
        DictionaryInterface<String, Value> newSymbolTable = null;

        try {
            //noinspection unchecked
            newSymbolTable = (DictionaryInterface<String, Value>) programState.getSymbolTable().clone();
        }
        catch (CloneNotSupportedException ignored)
        {
        }

        return new ProgramState(new MyStack<>(), newSymbolTable,
                programState.getMemoryHeap(), programState.getOutput(), programState.getFileTable(), programState.getSemaphoreTable(), internalStatement);
    }

    @Override
    public DictionaryInterface<String, Type> typeCheck(DictionaryInterface<String, Type> typeEnvironment) throws InterpreterException {
        try {
            //noinspection unchecked
            return internalStatement.typeCheck((DictionaryInterface<String, Type>) typeEnvironment.clone());
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String toString() {
        return "fork( " + internalStatement.toString() + " )";
    }

    @Override
    public Statement deepCopy() {
        return new ForkStatement(internalStatement.deepCopy());
    }
}
