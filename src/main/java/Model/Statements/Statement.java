package Model.Statements;

import Exceptions.InterpreterException;
import Model.ADTs.DictionaryInterface;
import Model.ProgramState.ProgramState;
import Model.Types.Type;

public interface Statement {
    ProgramState execute(ProgramState programState) throws InterpreterException;

    DictionaryInterface<String, Type> typeCheck(DictionaryInterface<String,Type> typeEnvironment) throws InterpreterException;

    Statement deepCopy();
}
