package Model.Expressions;

import Exceptions.DivisionByZeroException;
import Exceptions.InterpreterException;
import Exceptions.InvalidTypeException;
import Model.ADTs.DictionaryInterface;
import Model.ADTs.HeapInterface;
import Model.Types.IntType;
import Model.Types.Type;
import Model.Values.IntValue;
import Model.Values.Value;

public class ArithmeticExpression implements Expression{
    private Expression expression1, expression2;
    private char operation;

    public ArithmeticExpression(Expression expression1,char operation, Expression expression2) {
        this.expression1 = expression1;
        this.expression2 = expression2;
        this.operation = operation;
    }

    @Override
    public Value evaluate(DictionaryInterface<String, Value> table, HeapInterface heap) throws InterpreterException {
        Value value1, value2;
        value1 = expression1.evaluate(table, heap);
        if (value1.getType().equals(new IntType()))
        {
            value2 = expression2.evaluate(table, heap);
            if (value2.getType().equals(new IntType()))
            {
                IntValue integer1 = (IntValue) value1;
                IntValue integer2 = (IntValue) value2;
                int number1, number2;
                number1 = integer1.getValue();
                number2 = integer2.getValue();
                if (operation == '+')
                {
                    return new IntValue(number1 + number2);
                }
                else if (operation == '-')
                {
                    return new IntValue(number1 - number2);
                }
                else if (operation == '*')
                {
                    return new IntValue(number1 * number2);
                }
                else if (operation == '/')
                {
                    if (number2 == 0) throw new DivisionByZeroException("ERROR: Division by zero");
                    return new IntValue(number1 / number2);
                }
                else
                {
                    throw new InterpreterException("Invalid operation in expression.");
                }
            }
            else
            {
                throw new InvalidTypeException("ERROR: second operand is not an integer");
            }
        }
        throw new InvalidTypeException("ERROR: first operand is not an integer");
    }

    @Override
    public Type typeCheck(DictionaryInterface<String, Type> typeEnvironment) throws InterpreterException {
        Type type1, type2;
        type1 = expression1.typeCheck(typeEnvironment);
        type2 = expression2.typeCheck(typeEnvironment);
        if (type1.equals(new IntType())) {
            if (type2.equals(new IntType())) {
                return new IntType();
            }
            else {
                throw new InvalidTypeException("second operand is not an integer");
            }
        }
        else {
            throw new InvalidTypeException("first operand is not an integer");
        }
    }

    @Override
    public Expression deepCopy() {
        return new ArithmeticExpression(expression1.deepCopy(), operation, expression2.deepCopy());
    }

    public Expression getExpression1() {
        return expression1;
    }

    public void setExpression1(Expression expression1) {
        this.expression1 = expression1;
    }

    public Expression getExpression2() {
        return expression2;
    }

    public void setExpression2(Expression expression2) {
        this.expression2 = expression2;
    }

    public int getOperation() {
        return operation;
    }

    public void setOperation(char operation) {
        this.operation = operation;
    }

    @Override
    public String toString() {
        return expression1.toString() + " " + operation + " " + expression2.toString();
    }
}
