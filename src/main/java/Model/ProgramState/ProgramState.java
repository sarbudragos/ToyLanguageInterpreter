package Model.ProgramState;

import Exceptions.EmptyExecutionStackException;
import Exceptions.InterpreterException;
import Model.ADTs.*;
import Model.Statements.Statement;
import Model.Values.StringValue;
import Model.Values.Value;
import Model.ADTs.SemaphoreTableInterface;
import javafx.util.Pair;

import java.io.BufferedReader;
import java.util.List;
import java.util.Map;

public class ProgramState {

    int id;

    public int getId() {
        return id;
    }

    static int staticId=1;

    synchronized static int getStaticId()
    {
        staticId+=1;
        return staticId;
    }
    StackInterface<Statement> executionStack;
    DictionaryInterface<String, Value> symbolTable;

    public HeapInterface getMemoryHeap() {
        return memoryHeap;
    }

    public void setMemoryHeap(HeapInterface memoryHeap) {
        this.memoryHeap = memoryHeap;
    }

    HeapInterface memoryHeap;
    ListInterface<Value> output;

    DictionaryInterface<StringValue, BufferedReader> fileTable;

    SemaphoreTableInterface semaphoreTable;

    public SemaphoreTableInterface getSemaphoreTable() {
        return semaphoreTable;
    }

    Statement originalProgram;

    public ProgramState(StackInterface<Statement> executionStack,
                        DictionaryInterface<String, Value> symbolTable,
                        HeapInterface memoryHeap,
                        ListInterface<Value> output,
                        DictionaryInterface<StringValue, BufferedReader> fileTable,
                        SemaphoreTableInterface semaphoreTable,
                        Statement originalProgram){
        this.id = getStaticId();
        this.executionStack = executionStack;
        this.symbolTable = symbolTable;
        this.memoryHeap = memoryHeap;
        this.output = output;
        this.fileTable = fileTable;
        this.semaphoreTable = semaphoreTable;
        this.originalProgram = originalProgram.deepCopy();
        this.executionStack.push(this.originalProgram);
    }

    public boolean isNotCompleted()
    {
        return !executionStack.empty();
    }

    public ProgramState oneStepExecution() throws InterpreterException {
        if(executionStack.empty())
            throw new EmptyExecutionStackException("Execution stack is empty.");

        Statement currentStatement = executionStack.pop();
        return currentStatement.execute(this);
    }

    public StackInterface<Statement> getExecutionStack() {
        return executionStack;
    }

    public void setExecutionStack(StackInterface<Statement> executionStack) {
        this.executionStack = executionStack;
    }

    public DictionaryInterface<String, Value> getSymbolTable() {
        return symbolTable;
    }

    public DictionaryInterface<StringValue, BufferedReader> getFileTable() {
        return fileTable;
    }

    public ListInterface<Value> getOutput() {
        return output;
    }

    public void setOutput(ListInterface<Value> output) {
        this.output = output;
    }

    public String executionStackToString()
    {
        StringBuilder stringRepresentation = new StringBuilder();
        stringRepresentation.insert(0, "Id = " + id + "\n");

        for (Statement elem : executionStack)
        {
            stringRepresentation.insert(0, elem.toString() + ";\n");
        }
        return stringRepresentation.toString();
    }

    public String symbolTableToString()
    {
        StringBuilder stringRepresentation = new StringBuilder();
        stringRepresentation.insert(0, "Id = " + id + "\n");

        for (Map.Entry<String, Value> keyValuePair : symbolTable.entrySet())
        {
            stringRepresentation.insert(0, keyValuePair.getKey() + "-->"
                    + keyValuePair.getValue().toString() + "\n");
        }
        return stringRepresentation.toString();
    }

    public String memoryHeapToString()
    {
        StringBuilder stringRepresentation = new StringBuilder();

        for (Map.Entry<Integer, Value> keyValuePair : memoryHeap.getContent().entrySet())
        {
            stringRepresentation.insert(0, keyValuePair.getKey() + "-->"
                    + keyValuePair.getValue().toString() + "\n");
        }
        return stringRepresentation.toString();
    }

    public String outputToString()
    {
        StringBuilder stringRepresentation = new StringBuilder();

        for (Value value : output)
        {
            stringRepresentation.append(value.toString()).append("\n");
        }
        return stringRepresentation.toString().toString();
    }

    public String fileTableToString()
    {
        StringBuilder stringRepresentation = new StringBuilder();

        for (Map.Entry<StringValue, BufferedReader> keyValuePair : fileTable.entrySet())
        {
            stringRepresentation.insert(0, keyValuePair.getKey() + "\n");
        }
        return stringRepresentation.toString();
    }

    public String semaphoreTableToString()
    {
        StringBuilder stringRepresentation = new StringBuilder();

        for (Map.Entry<Integer, Pair<Integer, List<Integer>>> keyValuePair : semaphoreTable.getContent().entrySet())
        {
            stringRepresentation.insert(0, keyValuePair.getKey() + "-> "
                    + keyValuePair.getValue().getKey() + ": "+ keyValuePair.getValue().getValue() +"\n");
        }
        return stringRepresentation.toString();
    }

    @Override
    public String toString() {
        return "ExecutionStack:\n" + executionStackToString() + "\n" +
                "SymbolTable:\n" + symbolTableToString() + "\n" +
                "MemoryHeap:\n" + memoryHeapToString() + "\n" +
                "Output:\n" + outputToString() + "\n" +
                "FileTable:\n" + fileTableToString() + "\n" +
                "SemaphoreTable:\n" + semaphoreTableToString();
    }
}
