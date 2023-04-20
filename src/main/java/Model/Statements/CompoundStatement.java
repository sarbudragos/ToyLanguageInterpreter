package Model.Statements;

import Exceptions.InterpreterException;
import Model.ADTs.DictionaryInterface;
import Model.ADTs.StackInterface;
import Model.ProgramState.ProgramState;
import Model.Types.Type;

public class CompoundStatement implements Statement {

    Statement firstStatement;
    Statement secondStatement;

    public CompoundStatement(Statement firstStatement, Statement secondStatement){
        this.firstStatement = firstStatement;
        this.secondStatement = secondStatement;
    }

    @Override
    public ProgramState execute(ProgramState programState) throws InterpreterException {
        StackInterface<Statement> executionStack = programState.getExecutionStack();
        executionStack.push(secondStatement);
        executionStack.push(firstStatement);
        return null;
    }

    @Override
    public DictionaryInterface<String, Type> typeCheck(DictionaryInterface<String, Type> typeEnvironment) throws InterpreterException {
        return secondStatement.typeCheck(firstStatement.typeCheck(typeEnvironment));
    }

    @Override
    public Statement deepCopy() {
        return new CompoundStatement(firstStatement.deepCopy(), secondStatement.deepCopy());
    }

    @Override
    public String toString() {
        return firstStatement.toString() + ";\n" + secondStatement.toString();
    }
}
