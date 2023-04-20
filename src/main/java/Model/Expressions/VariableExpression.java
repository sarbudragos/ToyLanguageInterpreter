package Model.Expressions;

import Exceptions.IDNotFoundInSymbolTableException;
import Exceptions.InterpreterException;
import Model.ADTs.DictionaryInterface;
import Model.ADTs.HeapInterface;
import Model.Types.Type;
import Model.Values.Value;

public class VariableExpression implements Expression{
    String variableName;

    public VariableExpression(String variableName){
        this.variableName = variableName;
    }

    @Override
    public Value evaluate(DictionaryInterface<String, Value> table, HeapInterface heap) throws InterpreterException {
        if(!table.containsKey(variableName))
            throw new IDNotFoundInSymbolTableException("Variable name not found");
        return table.get(variableName);
    }

    @Override
    public Type typeCheck(DictionaryInterface<String, Type> typeEnvironment) throws InterpreterException {
        return typeEnvironment.get(variableName);
    }

    @Override
    public Expression deepCopy() {
        return new VariableExpression(variableName);
    }

    @Override
    public String toString() {
        return variableName;
    }
}
