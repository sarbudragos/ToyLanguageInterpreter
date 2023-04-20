package Repository;

import Exceptions.InterpreterException;
import Model.ADTs.ListInterface;
import Model.ProgramState.ProgramState;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class Repository implements RepositoryInterface{
    List<ProgramState> programStates;
    String logFilePath;
    PrintWriter logFile;

    public Repository(ProgramState programState, String logFilePath){
        programStates = new ArrayList<>();
        try {
            this.programStates.add(programState);
        }
        catch (NullPointerException nullPointerException)
        {
            throw new RuntimeException("Program state is null");
        }
        this.logFilePath = logFilePath;

    }

    @Override
    public List<ProgramState> getProgramList() {
        return programStates;
    }

    @Override
    public void setProgramList(List<ProgramState> programStates) {
        this.programStates = programStates;
    }

    @Override
    public void logProgramStateExecution(ProgramState programState){
        try
        {
            logFile= new PrintWriter(new BufferedWriter(new FileWriter(logFilePath, true)));
            logFile.append(programState.toString()).append("\n").append("\n");
            logFile.close();
        }
        catch (IOException ignored)
        {

        }
    }
}
