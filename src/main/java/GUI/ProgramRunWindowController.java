package GUI;

import Controller.Controller;
import Model.ADTs.*;
import Model.ProgramState.ProgramState;
import Model.Statements.Statement;
import Model.Values.StringValue;
import Model.Values.Value;
import Repository.Repository;
import Repository.RepositoryInterface;
import Model.ADTs.SemaphoreTable;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Callback;
import javafx.util.Pair;

import java.util.List;
import java.util.Map;

public class ProgramRunWindowController {

    @FXML
    Text programStateCounter;
    @FXML
    ListView<ProgramState> programStateListView;

    @FXML
    TableView<Map.Entry<Integer, Value>> heapTableView;

    @FXML
    TableColumn<Map.Entry<Integer, Value>, Integer> idColumn;

    @FXML
    TableColumn<Map.Entry<Integer, Value>, Value> valueColumn;

    @FXML
    TableView<Map.Entry<String, Value>> symbolTableView;

    @FXML
    TableColumn<Map.Entry<String, Value>, String> nameColumn;

    @FXML
    TableColumn<Map.Entry<String, Value>, Value> variableValueColumn;

    @FXML
    ListView<Statement> stackListView;

    @FXML
    ListView<Value> outputListView;

    @FXML
    ListView<StringValue> fileTableListView;

    @FXML
    TableView<Map.Entry<Integer, Pair<Integer, List<Integer>>>> semaphoreTableView;

    @FXML
    TableColumn<Map.Entry<Integer, Pair<Integer, List<Integer>>>, Integer> indexColumn;

    @FXML
    TableColumn<Map.Entry<Integer, Pair<Integer, List<Integer>>>, Integer> capacityColumn;

    @FXML
    TableColumn<Map.Entry<Integer, Pair<Integer, List<Integer>>>, List<Integer>> currentHoldersColumn;

    @FXML
    Button runOneStepButton;

    ProgramState selectedProgramState;

    Statement initialProgram;

    Controller programController;

    ProgramRunWindowController(Statement initialProgram)
    {
        this.initialProgram = initialProgram;

        DictionaryInterface<String,Value> symbolTable = new MyDictionary<>();


        ProgramState programState = new ProgramState(new MyStack<>(), symbolTable, new MyHeap(),
                new MyList<>(), new MyDictionary<>(), new SemaphoreTable(), initialProgram);
        RepositoryInterface repository9 = new Repository(programState, "log.txt");
        this.programController = new Controller(repository9);
        programController.setProgramRunWindowController(this);

        selectedProgramState = programController.getListOfProgramStates().get(0);
    }

    public void update()
    {
        if (selectedProgramState == null)
            return;

        programStateCounter.setText("Nr of program states: " + programController.getListOfProgramStates().size());

        programStateListView.setItems(
                FXCollections.observableArrayList(programController.getListOfProgramStates())
        );

        //programStateListView.refresh();

        heapTableView.setItems(
                FXCollections.observableArrayList(
                        selectedProgramState.getMemoryHeap().getContent().entrySet()
                )
        );
        heapTableView.refresh();

        DictionaryInterface<String, Value> currentSymbolTable = selectedProgramState.getSymbolTable();



        symbolTableView.setItems(
                FXCollections.observableArrayList(
                        selectedProgramState.getSymbolTable().getContent().entrySet()
                )
        );

        symbolTableView.refresh();

        stackListView.setItems(FXCollections.observableArrayList(
                selectedProgramState.getExecutionStack().toList()
        ));

        outputListView.setItems(
                FXCollections.observableArrayList(
                        selectedProgramState.getOutput().getInternalList()
                )
        );

        fileTableListView.setItems(
                FXCollections.observableArrayList(
                        selectedProgramState.getFileTable().getContent().keySet()
                )
        );

        semaphoreTableView.setItems(
                FXCollections.observableArrayList(
                        selectedProgramState.getSemaphoreTable().getContent().entrySet()
                )
        );

        semaphoreTableView.refresh();
    }

    @FXML
    public void initialize()
    {
        programStateListView.setCellFactory(new Callback<>() {

            @Override
            public ListCell<ProgramState> call(ListView<ProgramState> p) {

                return new ListCell<>() {

                    @Override
                    protected void updateItem(ProgramState t, boolean bln) {
                        super.updateItem(t, bln);
                        if (t != null) {
                            setText(String.valueOf(t.getId()));
                        }
                        else
                        {
                            setText(null);
                        }
                    }

                };
            }
        });

        programStateListView.setOnMouseClicked(
                event -> {
                    selectedProgramState = programStateListView.getSelectionModel().getSelectedItem();
                    update();
                }
        );

        idColumn.setCellValueFactory(p -> new ReadOnlyObjectWrapper<>(p.getValue().getKey()));
        valueColumn.setCellValueFactory(p -> new ReadOnlyObjectWrapper<>(p.getValue().getValue()));

        nameColumn.setCellValueFactory(p -> new ReadOnlyObjectWrapper<>(p.getValue().getKey()));
        variableValueColumn.setCellValueFactory(p -> new ReadOnlyObjectWrapper<>(p.getValue().getValue()));

        indexColumn.setCellValueFactory(p -> new ReadOnlyObjectWrapper<>(p.getValue().getKey()));
        capacityColumn.setCellValueFactory(p -> new ReadOnlyObjectWrapper<>(p.getValue().getValue().getKey()));
        currentHoldersColumn.setCellValueFactory(p-> new ReadOnlyObjectWrapper<>(p.getValue().getValue().getValue()));

        runOneStepButton.setOnAction(e -> {
            try {
                if(selectedProgramState == null)
                    return;
                programController.executeOneProgramStep();

            } catch (Exception ex) {
                System.err.println(ex.getMessage());
                Window window =   this.runOneStepButton.getScene().getWindow();
                if (window instanceof Stage){
                    ((Stage) window).close();
                }
            }
        });

        update();
    }

}
