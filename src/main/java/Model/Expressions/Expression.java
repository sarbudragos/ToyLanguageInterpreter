package Model.Expressions;

import Exceptions.InterpreterException;
import Model.ADTs.DictionaryInterface;
import Model.ADTs.HeapInterface;
import Model.Types.Type;
import Model.Values.Value;

public interface Expression {
    Value evaluate(DictionaryInterface<String, Value> table, HeapInterface heap) throws InterpreterException;

    Type typeCheck(DictionaryInterface<String,Type> typeEnvironment) throws InterpreterException;
    Expression deepCopy();
}
