package com.example.cosine.myapplication.g2048;

import com.example.cosine.myapplication.GameState;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Board {
    public static final int BOARD_SIZE = 4;
    private Cell cells[][];
    private Random rng = new Random();
    private int score = 0;
    private GameState gameState = GameState.ONGOING;
    private boolean moved = false;

    private boolean scoredMode = false;
    private int scoreThreshold = 0;

    Board(){
        cells = new Cell[BOARD_SIZE][BOARD_SIZE];
        for (int x = 0; x < BOARD_SIZE; ++x){
            for (int y = 0; y < BOARD_SIZE; ++y){
                cells[x][y] = new Cell(x, y);
            }
        }

        initializeBoard();
    }

    private void initializeBoard() {
        for (int i = 0; i < 2; ++i) { // place 2 blocks
            this.genNewCell();
        }
    }

    public void genNewCell(){
        int index = findRandomClearCell();
        cells[index/4][index%4].setNum(nextCellNum());
    }

    public int getValueAt(int x, int y){
        return cells[x][y].getNum();
    }

    // returns index of a random empty cell, returns -1 if all cells are full
    private int findRandomClearCell(){
        List<Integer> emptyIndices = new ArrayList<>();

        for (int i = 0; i < 16; ++i){
            if (cells[i/4][i%4].isClear()) {
                emptyIndices.add(i);
            }
        }
        if (emptyIndices.isEmpty()) {
            return -1;
        }
        return emptyIndices.get(rng.nextInt(emptyIndices.size()));
    }

    private int nextCellNum(){
        int num = rng.nextInt(4);
        if (num == 0) { // 1 in 4 chance to spawn a 4 block
            return 4;
        }
        else {
            return 2;
        }
    }

    public void makeMove(Direction dir) {
        moved = false;
        rotate(dir.rightRotationsTo(Direction.RIGHT)); // rotate
        slide();
        merge();
        slide();
        rotate(4 - dir.rightRotationsTo(Direction.RIGHT)); // rotate back

        if (moved) {
            genNewCell();
        }
        if ((scoredMode && score > scoreThreshold) || onBoard(2048)) {
            gameState = GameState.WON;
        } else if (!canMove()) {
            gameState = GameState.LOST;
        }
    }

    // handle only right direction, rotating the g2048_board as necessary
    private void slide() {
        for (int x = 0; x < BOARD_SIZE; ++x){
            for (int z = 1; z < BOARD_SIZE; ++z) { // repeat for each cell of the row
                for (int y = BOARD_SIZE - 1; y > 0; --y){
                    if (cells[x][y - 1].getNum() != 0 && cells[x][y].getNum() == 0) {
                        cells[x][y].copyNum(cells[x][y - 1]);
                        cells[x][y - 1].setNum(0);
                        moved = true;
                    }
                }
            }
        }
    }

    private void merge() {
        for (int x = 0; x < BOARD_SIZE; ++x){
            for (int y = BOARD_SIZE - 1; y > 0; --y){
                if (cells[x][y].getNum() == cells[x][y-1].getNum()){
                    cells[x][y].doubleNum();
                    score = score + cells[x][y].getNum();
                    cells[x][y-1].setNum(0);
                    moved = true;
                }
            }
        }
    }

    private boolean boardFull() {
        for (Cell[] row: cells) {
            for (Cell cell: row) {
                if (cell.isClear()) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean canMove(){
        if (!boardFull()) {
            return true;
        }
        for (int i = 0; i < BOARD_SIZE; ++i){
            for (int j = 0; j < BOARD_SIZE; ++j){
                int currNum = cells[i][j].getNum();
                if (((i > 0) && (currNum == cells[i - 1][j].getNum())) ||
                    ((i < BOARD_SIZE - 1) && (currNum == cells[i + 1][j].getNum())) ||
                    ((j > 0) && (currNum == cells[i][j - 1].getNum())) ||
                    ((j < BOARD_SIZE - 1) && (currNum == cells[i][j + 1].getNum()))) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean onBoard(int n) {
        for (Cell[] row: cells) {
            for (Cell cell: row) {
                if (cell.getNum() == n) {
                    return true;
                }
            }
        }
        return false;
    }

    // rotates the g2048_board 90 degrees right n times
    private void rotate(int n) {
        switch(n % 4) {
            case 0:
                break;
            case 1:
                reverseRows();
                transpose();
                break;
            case 2:
                reverseCols();
                reverseRows();
                break;
            case 3:
                transpose();
                reverseRows();
                break;
        }
    }

    // utility function, transposes cells
    private void transpose() {
        for (int i = 0; i < BOARD_SIZE; ++i) {
            for (int j = i; j < BOARD_SIZE; ++j) {
                Cell c = cells[i][j];
                cells[i][j] = cells[j][i];
                cells[j][i] = c;
            }
        }
    }

    // flips the g2048_board vertically
    private void reverseRows() {
        for (int i = 0, j = BOARD_SIZE - 1; i < j; ++i, --j) {
            Cell[] row = cells[i];
            cells[i] = cells[j];
            cells[j] = row;
        }
    }

    // flips the g2048_board horizontally
    private void reverseCols() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0, k = BOARD_SIZE - 1; j < k; ++j, --k) {
                Cell c = cells[i][j];
                cells[i][j] = cells[i][k];
                cells[i][k] = c;
            }
        }
    }

    public int getScore() {
        return score;
    }

    public GameState getGameState() {
        return gameState;
    }

    public void setScoredMode(int scoreThreshold) {
        scoredMode = true;
        this.scoreThreshold = scoreThreshold;
    }
}
