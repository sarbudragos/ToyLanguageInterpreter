package Model.Expressions;

import Exceptions.InterpreterException;
import Exceptions.InvalidTypeException;
import Model.ADTs.DictionaryInterface;
import Model.ADTs.HeapInterface;
import Model.Types.BoolType;
import Model.Types.IntType;
import Model.Types.Type;
import Model.Values.BoolValue;
import Model.Values.Value;

public class LogicExpression implements Expression{
    Expression expression1;
    Expression expression2;
    char operation;

    LogicExpression(Expression expression1, Expression expression2, char operation){
        this.expression1 = expression1;
        this.expression2 = expression2;
        this.operation = operation;
    }

    @Override
    public Value evaluate(DictionaryInterface<String, Value> table, HeapInterface heap) throws InterpreterException {
        Value result1 = expression1.evaluate(table, heap);
        if(result1.getType().equals(new BoolType())){
            Value result2 = expression2.evaluate(table, heap);
            if(result2.getType().equals(new BoolType())){
                BoolValue boolResult1 = (BoolValue) result1;
                BoolValue boolResult2 = (BoolValue) result2;
                if(operation == '&') {
                    return new BoolValue(boolResult1.getValue() && boolResult2.getValue());
                }
                else if(operation == '|'){
                    return new BoolValue(boolResult1.getValue() || boolResult2.getValue());
                }
                else
                {
                    throw new InterpreterException("Invalid operation in expression.");
                }
            }
            else
                throw new InvalidTypeException("Second operand is not a boolean");
        }
        throw new InvalidTypeException("First operand is not a boolean");
    }

    @Override
    public Type typeCheck(DictionaryInterface<String, Type> typeEnvironment) throws InterpreterException {
        Type type1, type2;
        type1 = expression1.typeCheck(typeEnvironment);
        type2 = expression2.typeCheck(typeEnvironment);
        if (type1.equals(new BoolType())) {
            if (type2.equals(new BoolType())) {
                return new BoolType();
            }
            else {
                throw new InvalidTypeException("second operand is not a boolean");
            }
        }
        else {
            throw new InvalidTypeException("first operand is not a boolean");
        }
    }

    @Override
    public Expression deepCopy() {
        return new LogicExpression(expression1.deepCopy(),expression2.deepCopy(),operation);
    }

    @Override
    public String toString() {
        return expression1.toString() + " " + operation + " " + expression2.toString();
    }
}
