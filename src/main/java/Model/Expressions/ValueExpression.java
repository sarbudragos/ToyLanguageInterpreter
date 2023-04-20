package Model.Expressions;

import Exceptions.InterpreterException;
import Model.ADTs.DictionaryInterface;
import Model.ADTs.HeapInterface;
import Model.Types.Type;
import Model.Values.Value;

public class ValueExpression implements Expression{
    Value value;

    public ValueExpression(Value value){
        this.value = value;
    }

    @Override
    public Value evaluate(DictionaryInterface<String, Value> table, HeapInterface heap) throws InterpreterException {
        return value;
    }

    @Override
    public Type typeCheck(DictionaryInterface<String, Type> typeEnvironment) throws InterpreterException {
        return value.getType();
    }

    @Override
    public Expression deepCopy() {
        return new ValueExpression(value.deepCopy());
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
