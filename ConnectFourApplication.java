/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.Observable;
import java.util.Observer;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 *
 * @author alden
 */
public class ConnectFourApplication extends Application implements Observer{

    public static final int NUM_COLUMNS = 8;
    public static final int NUM_ROWS = 8;
    public static final int NUM_TO_WIN = 4;
    public static final int BUTTON_SIZE = 20;
    ConnectFourGame gameEngine;
    ConnectButton[][] buttons;
    int row;
    int column;
    /** 
     * 
     * @param primaryStage contains and displays the red, black and empty pieces for the connect four game
     */
    @Override
    public void start(Stage primaryStage){
        gameEngine = new ConnectFourGame(NUM_ROWS, NUM_COLUMNS, NUM_TO_WIN,ConnectFourEnum.BLACK);
        buttons = new ConnectButton[NUM_ROWS][NUM_COLUMNS];
        gameEngine.addObserver(this);
        
        Alert alert = new Alert(AlertType.INFORMATION);
        //making top
        TextField top = new TextField(ConnectFourEnum.BLACK+" begins.");
        top.setEditable(false);
        
        //making mid
        GridPane mid = new GridPane();
        EventHandler<ActionEvent> sharedHandler = new ButtonHandler();      
        for(int i = NUM_ROWS-1 ; i >=0 ; i--){
            for(int j = 0; j < NUM_COLUMNS; j++){
              buttons[i][j] = new ConnectButton(ConnectFourEnum.EMPTY.toString(),i,j);
              buttons[i][j].setPrefHeight(BUTTON_SIZE*100);
              buttons[i][j].setPrefWidth(BUTTON_SIZE*100);
              buttons[i][j].setOnAction(sharedHandler);
              mid.add(buttons[i][j], j, NUM_ROWS - 1 - i);
            }
        }
        
        //making bot
        Button bot = new Button("Take my turn");
        bot.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
               System.out.println("Drop the Checker");
               gameEngine.takeTurn(row, column);
               top.setText("It's " + gameEngine.getTurn()+ " turn");
               if(gameEngine.getGameState() != ConnectFourEnum.IN_PROGRESS){
                   if(gameEngine.getGameState() == ConnectFourEnum.DRAW){
                       alert.setContentText("It is a draw game");
                   }
                   else{
                       alert.setContentText(gameEngine.getGameState() + " Won!");
                   }
                   alert.showAndWait();
                   gameEngine.reset(gameEngine.getTurn());
                   for (int i = NUM_ROWS - 1; i >= 0; i--){
                       for(int j = 0; j < NUM_COLUMNS; j++){
                           buttons[i][j].setText(ConnectFourEnum.EMPTY.toString());
                       }
               }
               }
            }
        });

        
        //forming everything into the root
        BorderPane root = new BorderPane();
        
        root.setTop(top);
        root.setCenter(mid);
        root.setBottom(bot);
        
        Scene scene = new Scene(root, 510, 380);
        
        primaryStage.setTitle("Connect Four Game");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

   @Override
    public void update(Observable o, Object arg) {
        ConnectMove cm = (ConnectMove) arg;
        buttons[cm.getRow()][cm.getColumn()].setText(cm.colour.toString());
        buttons[cm.getRow()][cm.getColumn()].setDisable(true);
    }
    
    class ButtonHandler implements EventHandler<ActionEvent>{   
    @Override
    public void handle(ActionEvent event) {
       Object source = event.getSource();
       ConnectButton clickedButton = (ConnectButton)source;
       row = clickedButton.getRow();
       column = clickedButton.getColumn();
       System.out.println("Event" + event.getSource());
    
        }
    }
}

