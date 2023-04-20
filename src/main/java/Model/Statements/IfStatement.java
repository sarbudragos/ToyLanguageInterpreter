package Model.Statements;

import Exceptions.InterpreterException;
import Exceptions.InvalidTypeException;
import Model.ADTs.DictionaryInterface;
import Model.ADTs.StackInterface;
import Model.Expressions.Expression;
import Model.ProgramState.ProgramState;
import Model.Types.BoolType;
import Model.Types.Type;
import Model.Values.BoolValue;
import Model.Values.Value;

public class IfStatement implements Statement{
    Expression conditionalExpression;
    Statement thenStatement;
    Statement elseStatement;

    public IfStatement(Expression conditionalExpression, Statement thenStatement, Statement elseStatement){
        this.conditionalExpression = conditionalExpression;
        this.thenStatement = thenStatement;
        this.elseStatement = elseStatement;
    }

    @Override
    public ProgramState execute(ProgramState programState) throws InterpreterException {
        Value condition = conditionalExpression.evaluate(programState.getSymbolTable(), programState.getMemoryHeap());
        StackInterface<Statement> executionStack = programState.getExecutionStack();
        if (!condition.getType().equals(new BoolType())){
            throw new InvalidTypeException("Conditional expression is not a boolean.");
        }
        if(((BoolValue)condition).getValue()){
            executionStack.push(thenStatement);
        }
        else {
            executionStack.push(elseStatement);
        }

        return null;
    }

    @Override
    public DictionaryInterface<String, Type> typeCheck(DictionaryInterface<String, Type> typeEnvironment) throws InterpreterException {
        Type typeOfExpression = conditionalExpression.typeCheck(typeEnvironment);
        if (typeOfExpression.equals(new BoolType())) {
            try {
                //noinspection unchecked
                thenStatement.typeCheck((DictionaryInterface<String, Type>) typeEnvironment.clone());
            } catch (CloneNotSupportedException e) {
                throw new RuntimeException(e);
            }
            try {
                //noinspection unchecked
                elseStatement.typeCheck((DictionaryInterface<String, Type>) typeEnvironment.clone());

            } catch (CloneNotSupportedException e) {
                throw new RuntimeException(e);
            }
            return typeEnvironment;
        }
        else {
            throw new InvalidTypeException("The conditional expression does not evaluate to bool.");
        }
    }

    @Override
    public Statement deepCopy() {
        return new IfStatement(conditionalExpression.deepCopy(), thenStatement.deepCopy(), elseStatement.deepCopy());
    }

    @Override
    public String toString() {
        return "if ( " + conditionalExpression.toString() + " ) then ( " + thenStatement.toString()
                + " ) else ( " + elseStatement.toString() + " )";
    }
}
