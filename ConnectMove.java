/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author alden
 */
public class ConnectMove {
    private int row;
    private int column;
    ConnectFourEnum colour;
    
    /**
     * 
     * @param row
     * @param column
     * @param colour 
     */
    public ConnectMove(int row, int column, ConnectFourEnum colour){
        this.row = row;
        this.column = column;
        this.colour = colour;
    }
    public int getRow(){
        return this.row;
    }
    public int getColumn(){
        return this.column;
    }
    public ConnectFourEnum getColour(){
        return this.colour;
    }
}
