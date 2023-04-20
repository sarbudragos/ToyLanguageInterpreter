package GUI;

import Model.Statements.Statement;
import com.example.toylanguageinterpretergui.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.Map;


public class ProgramSelectionWindowController {
    @FXML
    ListView<Statement> programListView;

    @FXML
    Button runButton;

    Stage mainStage;

    ObservableList<Statement> programList;



    Statement selectedProgram;

    public ProgramSelectionWindowController(Stage mainStage, List<Statement> programsAndProcedures)
    {
        this.programList = FXCollections.observableArrayList(programsAndProcedures);
        this.mainStage = mainStage;
    }

    @FXML
    public void initialize()
    {
        programListView.setItems(programList);
        programListView.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> selectedProgram = newValue
        );

        runButton.setOnAction(e -> {
            try {
                if(selectedProgram == null)
                    return;
                openProgramRunWindow();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
    }

    void openProgramRunWindow() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("ProgramRunWindow.fxml"));


        fxmlLoader.setControllerFactory(
                ProgramSelectionWindowController ->new ProgramRunWindowController(selectedProgram));
        Scene programRunWindowScene = new Scene(fxmlLoader.load());

        Stage newWindow = new Stage();
        newWindow.setTitle("Running program");
        newWindow.setScene(programRunWindowScene);

        // Specifies the modality for new window.
        newWindow.initModality(Modality.WINDOW_MODAL);

        // Specifies the owner Window (parent) for new window
        newWindow.initOwner(mainStage);

        // Set position of second window, related to primary window.
        newWindow.setX(mainStage.getX() + 200);
        newWindow.setY(mainStage.getY() + 100);

        newWindow.show();
    }

}
