package Model.Expressions;

import Exceptions.InterpreterException;
import Exceptions.InvalidTypeException;
import Model.ADTs.DictionaryInterface;
import Model.ADTs.HeapInterface;
import Model.Types.BoolType;
import Model.Types.IntType;
import Model.Types.Type;
import Model.Values.BoolValue;
import Model.Values.IntValue;
import Model.Values.Value;

import java.util.Objects;

public class RelationalExpression implements Expression{
    Expression expression1;
    Expression expression2;
    String operation;

    public RelationalExpression(Expression expression1, String operation, Expression expression2)
    {
        this.expression1 = expression1;
        this.expression2 = expression2;
        this.operation = operation;
    }

    @Override
    public Value evaluate(DictionaryInterface<String, Value> table, HeapInterface heap) throws InterpreterException {
        Value value1, value2;
        value1 = expression1.evaluate(table, heap);
        if (value1.getType().equals(new IntType())) {
            value2 = expression2.evaluate(table, heap);
            if (value2.getType().equals(new IntType())) {
                IntValue integer1 = (IntValue) value1;
                IntValue integer2 = (IntValue) value2;
                int number1, number2;
                number1 = integer1.getValue();
                number2 = integer2.getValue();
                if (Objects.equals(operation, "<")) {
                    return new BoolValue(number1 < number2);
                }
                else if (Objects.equals(operation, "<=")) {
                    return new BoolValue(number1 <= number2);
                }
                else if (Objects.equals(operation, "==")) {
                    return new BoolValue(number1 == number2);
                }
                else if (Objects.equals(operation, "!=")) {
                    return new BoolValue(number1 != number2);
                }
                else if (Objects.equals(operation, ">")) {
                    return new BoolValue(number1 > number2);
                }
                else if (Objects.equals(operation, ">=")) {
                    return new BoolValue(number1 >= number2);
                }
                else
                {
                    throw new InterpreterException("Invalid operation in expression.");
                }

            } else {
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
                return new BoolType();
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
        return new RelationalExpression(expression1.deepCopy(), operation, expression2.deepCopy());
    }

    @Override
    public String toString() {
        return expression1.toString() + " " + operation + " " + expression2.toString();
    }
}
