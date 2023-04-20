package com.example.toylanguageinterpretergui;

import Exceptions.InterpreterException;
import GUI.ProgramSelectionWindowController;
import Model.ADTs.MyDictionary;
import Model.Expressions.*;
import Model.Statements.*;
import Model.Types.BoolType;
import Model.Types.IntType;
import Model.Types.ReferenceType;
import Model.Types.StringType;
import Model.Values.BoolValue;
import Model.Values.IntValue;
import Model.Values.StringValue;
import Model.Statements.AcquireStatement;
import Model.Statements.CreateSemaphoreStatement;
import Model.Statements.ReleaseStatement;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class Main extends Application {
    private List<Statement> generatePrograms()
    {
        List<Statement> programs = new ArrayList<>();
        programs.add(new CompoundStatement(new VariableDeclarationStatement("v",new IntType()),
                new CompoundStatement(new AssignmentStatement("v",new ValueExpression(new IntValue(2))),
                        new PrintStatement(new VariableExpression("v")))));

        programs.add(new CompoundStatement(
                new VariableDeclarationStatement("a", new IntType()),
                new CompoundStatement(
                        new VariableDeclarationStatement("b", new IntType()),
                        new CompoundStatement(
                                new VariableDeclarationStatement("c", new IntType()),
                                new CompoundStatement(
                                        new AssignmentStatement("a", new ValueExpression(new IntValue(1))),
                                        new CompoundStatement(
                                                new AssignmentStatement("b", new ValueExpression(new IntValue(2))),
                                                new CompoundStatement(
                                                        new AssignmentStatement("c", new ValueExpression(new IntValue(5))),
                                                        new CompoundStatement(
                                                                new SwitchStatement(
                                                                        new ArithmeticExpression(new VariableExpression("a"), '*', new ValueExpression(new IntValue(10))),
                                                                        new ArithmeticExpression(new VariableExpression("b"), '*', new VariableExpression("c")),
                                                                        new CompoundStatement(
                                                                                new PrintStatement(new VariableExpression("a")),
                                                                                new PrintStatement(new VariableExpression("b"))
                                                                        ),
                                                                        new ValueExpression(new IntValue(10)),
                                                                        new CompoundStatement(
                                                                                new PrintStatement(new ValueExpression(new IntValue(100))),
                                                                                new PrintStatement(new ValueExpression(new IntValue(200)))
                                                                        ),
                                                                        new PrintStatement(new ValueExpression(new IntValue(300)))
                                                                ),
                                                                new PrintStatement(new ValueExpression(new IntValue(300)))
                                                        )
                                                )
                                        )
                                )
                        )
                )
        ));

        programs.add(new CompoundStatement( new VariableDeclarationStatement("a",new IntType()),
                new CompoundStatement(new VariableDeclarationStatement("b",new IntType()),
                        new CompoundStatement(new AssignmentStatement("a",
                                new ArithmeticExpression(new ValueExpression(new IntValue(2)),'+',
                                        new ArithmeticExpression(new ValueExpression(new IntValue(3)),'*',
                                                new ValueExpression(new IntValue(5))))),
                                new CompoundStatement(new AssignmentStatement("b",
                                        new ArithmeticExpression(new VariableExpression("a"),'+',
                                                new ValueExpression(new IntValue(1)))),
                                        new PrintStatement(new VariableExpression("b")))))));

        programs.add(new CompoundStatement(new VariableDeclarationStatement("a",new BoolType()),
                new CompoundStatement(new VariableDeclarationStatement("v", new IntType()),
                        new CompoundStatement(new AssignmentStatement("a", new ValueExpression(new BoolValue(true))),
                                new CompoundStatement(new IfStatement(new RelationalExpression(new ValueExpression(new IntValue(2)),
                                        "<=", new ValueExpression(new IntValue(3))),
                                        new AssignmentStatement("v",new ValueExpression(new IntValue(2))),
                                        new AssignmentStatement("v", new ValueExpression(new IntValue(3)))),
                                        new PrintStatement(new VariableExpression("v")))))));

        programs.add(new CompoundStatement(new VariableDeclarationStatement("varf", new StringType()),
                new CompoundStatement(
                        new AssignmentStatement("varf",new ValueExpression(new StringValue("test.in"))),
                        new CompoundStatement(
                                new OpenReadFileStatement(new VariableExpression("varf")),
                                new CompoundStatement(
                                        new VariableDeclarationStatement("varc", new IntType()),
                                        new CompoundStatement(
                                                new ReadFileStatement(new VariableExpression("varf"), "varc"),
                                                new CompoundStatement(
                                                        new PrintStatement(new VariableExpression("varc")),
                                                        new CompoundStatement(
                                                                new ReadFileStatement(new VariableExpression("varf"), "varc"),
                                                                new CompoundStatement(
                                                                        new PrintStatement(new VariableExpression("varc")),
                                                                        new CloseFileStatement(new VariableExpression("varf"))
                                                                )))))))));
        programs.add(new CompoundStatement(
                new VariableDeclarationStatement("v", new ReferenceType(new IntType())),
                new CompoundStatement(
                        new HeapAllocationStatement("v", new ValueExpression(new IntValue(20))),
                        new CompoundStatement(new VariableDeclarationStatement("a",
                                new ReferenceType(new ReferenceType(new IntType()))),
                                new CompoundStatement(
                                        new HeapAllocationStatement("a", new VariableExpression("v")),
                                        new CompoundStatement(
                                                new PrintStatement(new VariableExpression("v")),
                                                new PrintStatement(new VariableExpression("a"))
                                        )
                                )
                        )
                )
        ));


        programs.add(new CompoundStatement(
                new VariableDeclarationStatement("v1", new ReferenceType(new IntType())),
                new CompoundStatement(
                        new VariableDeclarationStatement("cnt", new IntType()),
                        new CompoundStatement(
                                new HeapAllocationStatement("v1", new ValueExpression(new IntValue(1))),
                                new CompoundStatement(
                                        new CreateSemaphoreStatement("cnt", new HeapReadExpression(new VariableExpression("v1"))),
                                        new CompoundStatement(
                                                new ForkStatement(new CompoundStatement(
                                                        new AcquireStatement("cnt"),
                                                        new CompoundStatement(
                                                                new HeapWriteStatement("v1",
                                                                        new ArithmeticExpression(
                                                                                new HeapReadExpression(new VariableExpression("v1")),
                                                                                '*',
                                                                                new ValueExpression(new IntValue(10))
                                                                        )
                                                                ),
                                                                new CompoundStatement(
                                                                        new PrintStatement(new HeapReadExpression(new VariableExpression("v1"))),
                                                                        new ReleaseStatement("cnt")
                                                                )

                                                        )
                                                )
                                                ),
                                                new CompoundStatement(
                                                        new ForkStatement(new CompoundStatement(
                                                                new AcquireStatement("cnt"),
                                                                new CompoundStatement(
                                                                        new HeapWriteStatement("v1",
                                                                                new ArithmeticExpression(
                                                                                        new HeapReadExpression(new VariableExpression("v1")),
                                                                                        '*',
                                                                                        new ValueExpression(new IntValue(10))
                                                                                )
                                                                        ),
                                                                        new CompoundStatement(
                                                                                new HeapWriteStatement("v1",
                                                                                        new ArithmeticExpression(
                                                                                                new HeapReadExpression(new VariableExpression("v1")),
                                                                                                '*',
                                                                                                new ValueExpression(new IntValue(2))
                                                                                        )
                                                                                ),
                                                                                new CompoundStatement(
                                                                                        new PrintStatement(new HeapReadExpression(new VariableExpression("v1"))),
                                                                                        new ReleaseStatement("cnt")
                                                                                )
                                                                        )

                                                                )
                                                        )
                                                        ),
                                                        new CompoundStatement(
                                                                new AcquireStatement("cnt"),
                                                                new CompoundStatement(
                                                                        new PrintStatement(
                                                                                new ArithmeticExpression(
                                                                                new HeapReadExpression(new VariableExpression("v1")),
                                                                                '-',
                                                                                new ValueExpression(new IntValue(1))
                                                                        )),
                                                                        new ReleaseStatement("cnt")
                                                                )
                                                        )
                                                )
                                        )
                                )
                        )
                )
        )) ;




        for (Statement program : programs) {
            try {
                program.typeCheck(new MyDictionary<>());
            } catch (InterpreterException e) {
                System.err.println(e.getMessage());
            }
        }
        return programs;
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("ProgramSelectionWindow.fxml"));


        fxmlLoader.setControllerFactory(
                ProgramSelectionWindowController ->new ProgramSelectionWindowController(stage, generatePrograms()));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Select a program to run.");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}