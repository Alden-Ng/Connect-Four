
import javafx.scene.control.Button;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author alden
 */
public class ConnectButton extends Button{
    private int row;
    private int column;
    /**
     * 
     * @param label sets the button label to either red, black, or empty
     * @param row represents the row where the button exist
     * @param column represents the column where the button exist
     */
    public ConnectButton(String label,int row, int column){
        super();
        this.setText(label);
        this.row = row;
        this.column = column;
    }
    /**
     * 
     * @return the row where the button exist
     */
    public int getRow(){
        return this.row;
    }
    /**
     * 
     * @return the column where the button exist
     */
    public int getColumn(){
        return this.column;
    }
    /**
     * 
     * @return the location of the button as a string
     */
    @Override
    public String toString(){
        return "(<"+this.row+">,<"+this.column+">)";
    }
}
