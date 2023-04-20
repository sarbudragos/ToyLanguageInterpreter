package Model.Expressions;

import Exceptions.InterpreterException;
import Exceptions.InvalidTypeException;
import Model.ADTs.DictionaryInterface;
import Model.ADTs.HeapInterface;
import Model.Types.ReferenceType;
import Model.Types.Type;
import Model.Values.ReferenceValue;
import Model.Values.Value;

public class HeapReadExpression implements Expression{
    Expression expression;

    public HeapReadExpression(Expression expression)
    {
        this.expression = expression;
    }

    @Override
    public Value evaluate(DictionaryInterface<String, Value> table, HeapInterface heap) throws InterpreterException {
        Value expressionResult = expression.evaluate(table, heap);

        if(!(expressionResult.getType() instanceof ReferenceType))
        {
            throw new InvalidTypeException("Expression must evaluate to reference type.");
        }

        int address = ((ReferenceValue)expressionResult).getAddress();

        return heap.getContent().get(address);
    }

    @Override
    public Type typeCheck(DictionaryInterface<String, Type> typeEnvironment) throws InterpreterException {
        Type type=expression.typeCheck(typeEnvironment);
        if (type instanceof ReferenceType referenceType) {
            return referenceType.getInnerType();
        }
        else{
            throw new InvalidTypeException("the rH argument is not a Ref Type");
        }
    }

    @Override
    public String toString() {
        return "heapRead( " + expression.toString() + " )";
    }

    @Override
    public Expression deepCopy() {
        return new HeapReadExpression(expression.deepCopy());
    }
}
