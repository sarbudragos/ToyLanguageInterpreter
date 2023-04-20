package Model.Statements;

import Exceptions.InterpreterException;
import Model.ADTs.DictionaryInterface;
import Model.ProgramState.ProgramState;
import Model.Types.Type;
import Model.Values.Value;

public class VariableDeclarationStatement implements Statement{
    String variableName;
    Type type;

    public VariableDeclarationStatement(String variableName, Type type){
        this.variableName = variableName;
        this.type = type;
    }

    @Override
    public ProgramState execute(ProgramState programState) throws InterpreterException {
        DictionaryInterface<String, Value> symbolTable = programState.getSymbolTable();
        if(symbolTable.containsKey(variableName)){
            throw new InterpreterException("Variable " + variableName + " is already declared");
        }

        symbolTable.put(variableName,type.defaultValue());

        return null;
    }

    @Override
    public DictionaryInterface<String, Type> typeCheck(DictionaryInterface<String, Type> typeEnvironment) throws InterpreterException {
        typeEnvironment.put(variableName,type);
        return typeEnvironment;
    }

    @Override
    public Statement deepCopy() {
        return new VariableDeclarationStatement(variableName, type.deepCopy());
    }

    @Override
    public String toString() {
        return type.toString() + " " + variableName;
    }
}
