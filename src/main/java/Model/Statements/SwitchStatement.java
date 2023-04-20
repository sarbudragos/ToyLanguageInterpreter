package Model.Statements;

import Exceptions.InterpreterException;
import Exceptions.InvalidTypeException;
import Model.ADTs.DictionaryInterface;
import Model.Expressions.Expression;
import Model.Expressions.RelationalExpression;
import Model.ProgramState.ProgramState;
import Model.Types.Type;

public class SwitchStatement implements Statement {

    Expression expression;
    Expression caseOneExpression;
    Expression caseTwoExpression;

    Statement caseOneStatement;
    Statement caseTwoStatement;
    Statement defaultCaseStatement;

    public SwitchStatement(Expression expression, Expression caseOneExpression, Statement caseOneStatement,
                           Expression caseTwoExpression, Statement caseTwoStatement, Statement defaultCaseStatement)
    {
        this.expression = expression;
        this.caseOneExpression = caseOneExpression;
        this.caseOneStatement = caseOneStatement;
        this.caseTwoExpression = caseTwoExpression;
        this.caseTwoStatement = caseTwoStatement;
        this.defaultCaseStatement = defaultCaseStatement;
    }

    @Override
    public ProgramState execute(ProgramState programState) throws InterpreterException {
        Statement equivalentIfStatement = new IfStatement(
                new RelationalExpression(expression, "==", caseOneExpression),
                caseOneStatement,
                new IfStatement(
                        new RelationalExpression(expression, "==", caseTwoExpression),
                        caseTwoStatement,
                        defaultCaseStatement
                )
        );

        programState.getExecutionStack().push(equivalentIfStatement);

        return null;
    }

    @Override
    public DictionaryInterface<String, Type> typeCheck(DictionaryInterface<String, Type> typeEnvironment) throws InterpreterException {
        Type typeOfExpression = expression.typeCheck(typeEnvironment);
        Type typeOfCaseOneExpression = caseOneExpression.typeCheck(typeEnvironment);
        Type typeOfCaseTwoExpression = caseTwoExpression.typeCheck(typeEnvironment);

        if(!typeOfExpression.equals(typeOfCaseOneExpression) || !typeOfExpression.equals(typeOfCaseTwoExpression))
            throw new InvalidTypeException("Switch expressions must all have the same type.");

        try {
            //noinspection unchecked
            caseOneStatement.typeCheck((DictionaryInterface<String, Type>) typeEnvironment.clone());
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
        try {
            //noinspection unchecked
            caseTwoStatement.typeCheck((DictionaryInterface<String, Type>) typeEnvironment.clone());

        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
        try {
            //noinspection unchecked
            defaultCaseStatement.typeCheck((DictionaryInterface<String, Type>) typeEnvironment.clone());

        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
        return typeEnvironment;
    }

    @Override
    public Statement deepCopy() {
        return new SwitchStatement(expression.deepCopy(), caseOneExpression.deepCopy(),
                caseOneStatement.deepCopy(), caseTwoExpression.deepCopy(), caseTwoStatement.deepCopy(),
                defaultCaseStatement.deepCopy());
    }

    @Override
    public String toString() {
        return "( switch( " + expression + " )\n" +
                "( case( "+ caseOneExpression + " ) : " + caseOneStatement + " )\n" +
                "( case( "+ caseTwoExpression + " ) : " + caseTwoStatement + " )\n" +
                "( default : " + defaultCaseStatement + " ))";
    }
}
