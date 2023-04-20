package Model.Statements;

import Exceptions.InterpreterException;
import Model.ADTs.DictionaryInterface;
import Model.ADTs.ListInterface;
import Model.Expressions.Expression;
import Model.ProgramState.ProgramState;
import Model.Types.Type;
import Model.Values.Value;

public class PrintStatement implements Statement{
    Expression expression;

    public PrintStatement(Expression expression){
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState programState) throws InterpreterException {
        ListInterface<Value> programOutput = programState.getOutput();
        programOutput.add(expression.evaluate(programState.getSymbolTable(), programState.getMemoryHeap()));
        return null;
    }

    @Override
    public DictionaryInterface<String, Type> typeCheck(DictionaryInterface<String, Type> typeEnvironment) throws InterpreterException {
        expression.typeCheck(typeEnvironment);
        return typeEnvironment;
    }

    @Override
    public Statement deepCopy() {
        return new PrintStatement(expression.deepCopy());
    }

    @Override
    public String toString() {
        return "print( " + expression.toString() + " )";
    }
}
