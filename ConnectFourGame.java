
import java.util.Observable;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author alden
 */
public class ConnectFourGame extends Observable {

    private int nColumns;
    private int nRows;
    private int numToWin;
    private int nMarks;
    ConnectFourEnum[][] grid;
    ConnectFourEnum gameState;
    ConnectFourEnum turn;

    /**
     *
     * @param initialTurn color of piece that is taking the first turn
     */
    public ConnectFourGame(ConnectFourEnum initialTurn) {
        this(8, 8, 4, initialTurn);
    }

    /**
     *
     * @param nRows how many rows the board will have
     * @param nColumns how many columns the board will have
     * @param numToWin the requirement of the having the same pieces in a row to
     * win the game
     * @param initialTurn color of piece that is taking the first turn
     */
    public ConnectFourGame(int nRows, int nColumns, int numToWin, ConnectFourEnum initialTurn) {
        if (nRows < 0 || nColumns < 0 || numToWin < 0) {
            throw new IllegalArgumentException("Must have positive values for Rows, Columns, and Number of Wins.");
        }
        this.nRows = nRows;
        this.nColumns = nColumns;
        this.numToWin = numToWin;
        this.nMarks = 0;
        this.grid = new ConnectFourEnum[nRows][nColumns];
        this.gameState = ConnectFourEnum.IN_PROGRESS;
        this.turn = initialTurn;
        reset(initialTurn);
    }

    /**
     *
     * @param initialTurn color of piece that is taking the first turn
     */
    public void reset(ConnectFourEnum initialTurn) {
        for (int i = 0; i < nRows; i++) {
            for (int j = 0; j < nColumns; j++) {
                grid[i][j] = ConnectFourEnum.EMPTY;
            }
        }

    }

    /**
     *
     * @param row is the row that the user selected to place the piece
     * @param column is the column that the user selected to place the piece
     * @return whether the game is still in progress or someone won
     */
    public ConnectFourEnum takeTurn(int row, int column) {
        if (grid[row][column] != ConnectFourEnum.EMPTY) {
            System.out.println("The spot that has been sleected is already taken");
            return gameState;
        } else if (row > 0) {
            if (grid[row - 1][column] == ConnectFourEnum.EMPTY) {
                throw new IllegalArgumentException("Must be placed on top of another piece");
            }
        }
        grid[row][column] = turn;

        ConnectMove cm = new ConnectMove(row, column, turn);
        setChanged();
        notifyObservers(cm);

        nMarks++;
        findWinner(row, column);
        if (this.turn == ConnectFourEnum.BLACK) {
            this.turn = ConnectFourEnum.RED;
        } else if (this.turn == ConnectFourEnum.RED) {
            this.turn = ConnectFourEnum.BLACK;
        }
        return gameState;
    }

    private ConnectFourEnum findWinner(int row, int column) {
        int winC = 0;

        //checking horizontal
        for (int i = 0; i < this.nColumns; i++) {
            if (grid[row][i] == turn) {
                winC++;
                if (winC == numToWin) {
                    gameState = turn;
                    return gameState;
                }
            } else {
                winC = 0;
            }
        }
        //checking veritcal
        if (row + 1 >= numToWin) {
            for (int i = 0; i < row; i++) {
                if (grid[i][column] == turn) {
                    winC++;
                    if (winC + 1 == numToWin) {
                        gameState = turn;
                        return gameState;
                    }
                } else {
                    winC = 0;
                }
            }
        }

        int currRow = row;
        int currColumn = column;
        //checking top right
        for (int i = 0; currRow + i < this.nRows && currColumn + i < this.nColumns && winC == i; i++) {
            if (grid[currRow + i][currColumn + i] == turn) {
                winC++;
                if (winC == numToWin) {
                    gameState = turn;
                    return gameState;
                }
            }
        }
        //checking bottom left
        if (currRow + 1 >= numToWin - winC && currColumn + 1 >= numToWin - winC) {
            for (int i = 1; currRow - i >= 0 && currColumn - i >= 0; i++) {
                if(grid[currRow - i][currColumn - i] == turn){
                    winC++;
                }
                if(winC == numToWin){
                    gameState = turn;
                    return gameState;
                }
            }
        }
        winC = 0;
        
        //checking top left
        for (int i = 0; currRow + i < this.nRows && currColumn - i >= 0 && winC == i; i++) {
            if (grid[currRow + i][currColumn - i] == turn) {
                winC++;
                if (winC == numToWin) {
                    gameState = turn;
                    return gameState;
                }
            }
        }
        //checking bottom right
        if (currRow + 1 >= numToWin - winC && this.nColumns - currColumn + 1 >= numToWin - winC) {
            for (int i = 1; currRow - i >= 0 && currColumn + i < this.nColumns; i++) {
                if(grid[currRow - i][currColumn + i] == turn){
                    winC++;
                }
                if(winC == numToWin){
                    gameState = turn;
                    return gameState;
                }
            }
        }
        
        //checking if the board is full
        if (this.nRows * this.nColumns == nMarks) {
            gameState = ConnectFourEnum.DRAW;
        }
        return gameState;
    }

    /**
     *
     * @return whether someone has won or if the game is still in progress
     */
    public ConnectFourEnum getGameState() {
        return gameState;

    }

    /**
     *
     * @return which color's turn it is
     */
    public ConnectFourEnum getTurn() {
        return turn;
    }

    /**
     *
     * @return prints the board and whether each spot on the board is empty,
     * red, or black
     */
    @Override
    public String toString() {
        String sGrid = "";
        for (int i = nRows - 1; i >= 0; i--) {
            for (int j = 0; j < nColumns; j++) {
                sGrid += grid[i][j] + " | ";
            }
            sGrid += "\n";
        }
        return sGrid;
    }
}
