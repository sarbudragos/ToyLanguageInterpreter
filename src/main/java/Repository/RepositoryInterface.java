package Repository;

import Exceptions.InterpreterException;
import Model.ProgramState.ProgramState;

import java.util.List;

public interface RepositoryInterface {

    List<ProgramState> getProgramList();

    void setProgramList(List<ProgramState> programStates);


    void logProgramStateExecution(ProgramState programState);
}
