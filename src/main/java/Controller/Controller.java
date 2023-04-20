package Controller;

import Exceptions.InterpreterException;
import GUI.ProgramRunWindowController;
import Model.ADTs.*;
import Model.ProgramState.ProgramState;
import Model.Values.ReferenceValue;
import Model.Values.Value;
import Repository.RepositoryInterface;

import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Controller {
    RepositoryInterface repository;
    ExecutorService executor;

    ProgramRunWindowController programRunWindowController;

    public void setProgramRunWindowController(ProgramRunWindowController programRunWindowController) {
        this.programRunWindowController = programRunWindowController;
    }

    public Controller(RepositoryInterface repository) {
        this.repository = repository;
    }

    List<ProgramState> removeCompletedPrg(List<ProgramState> inPrgList) {
        return inPrgList.stream().filter(ProgramState::isNotCompleted)
                .collect(Collectors.toList());
    }

    List<Integer> getAddressFromSymbolTable(Collection<Value> symTableValues, Collection<Value> heap) {
        return Stream.concat(
                        heap.stream()
                                .filter(v -> v instanceof ReferenceValue)
                                .map(v -> {
                                    ReferenceValue v1 = (ReferenceValue) v;
                                    return v1.getAddress();
                                }),
                        symTableValues.stream()
                                .filter(v -> v instanceof ReferenceValue)
                                .map(v -> {
                                    ReferenceValue v1 = (ReferenceValue) v;
                                    return v1.getAddress();
                                })
                )
                .collect(Collectors.toList());
    }

    Map<Integer, Value> garbageCollector(List<Integer> symbolTable, HeapInterface heap) {
        return heap.getContent().entrySet().stream().filter(e -> symbolTable.contains(e.getKey())).
                collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private void oneStepForAllPrograms(List<ProgramState> programStates) {
        programStates.forEach(prg -> repository.logProgramStateExecution(prg));
        //prepare the list of callables
        List<Callable<ProgramState>> callList = programStates.stream()
                .map((ProgramState p) -> (Callable<ProgramState>) (p::oneStepExecution))
                .collect(Collectors.toList());
        //start the execution of the callables
        // it returns the list of new created PrgStates (namely threads)
        List<ProgramState> newPrgList = null;
        try {
            newPrgList = executor.invokeAll(callList).stream()
                    .map(future -> {
                        try {
                            return future.get();
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }

                    })
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        //add the new created threads to the list of existing threads
        programStates.addAll(newPrgList);
        //------------------------------------------------------------------------------
        // after the execution, print the PrgState List into the log file
        programStates.forEach(program -> repository.logProgramStateExecution(program));
        //Save the current programs in the repository
        repository.setProgramList(programStates);
    }

    public void executeOneProgramStep() throws InterpreterException{
        executor = Executors.newFixedThreadPool(2);
        //remove the completed programs
        List<ProgramState> programList = removeCompletedPrg(repository.getProgramList());

        programList.forEach(programState -> programState.getMemoryHeap().setContent(
                (HashMap<Integer, Value>) garbageCollector(
                        getAddressFromSymbolTable(programState.getSymbolTable().getContent().values(), programState.getMemoryHeap().getContent().values()),
                        programState.getMemoryHeap()
                )
        ));
        oneStepForAllPrograms(programList);
        //remove the completed programs
        programList = removeCompletedPrg(repository.getProgramList());

        executor.shutdownNow();

        programRunWindowController.update();

        repository.setProgramList(programList);
    }

    public void executeAllProgramSteps() throws InterpreterException {
        executor = Executors.newFixedThreadPool(2);
        //remove the completed programs
        List<ProgramState> programList = removeCompletedPrg(repository.getProgramList());
        while (programList.size() > 0) {
            programList.forEach(programState -> programState.getMemoryHeap().setContent(
                    (HashMap<Integer, Value>) garbageCollector(
                            getAddressFromSymbolTable(programState.getSymbolTable().getContent().values(), programState.getMemoryHeap().getContent().values()),
                            programState.getMemoryHeap()
                    )
            ));
            oneStepForAllPrograms(programList);
            //remove the completed programs
            programList = removeCompletedPrg(repository.getProgramList());
        }
        executor.shutdownNow();
        //HERE the repository still contains at least one Completed Prg
        // and its List<PrgState> is not empty. Note that oneStepForAllPrg calls the method
        // setPrgList of repository in order to change the repository
        // update the repository state
        repository.setProgramList(programList);

    }

    public List<ProgramState> getListOfProgramStates()
    {
        return repository.getProgramList();
    }

}