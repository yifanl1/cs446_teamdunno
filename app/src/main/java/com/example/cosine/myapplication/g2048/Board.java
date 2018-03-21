package com.example.cosine.myapplication.g2048;

import java.util.Random;

public class Board {
    private int dimension = 4;
    private Cell cells[][];
    private Cell backup[][];
    private Random rn = new Random();
    private int score = 0;
    private Display display;

    Board(){
        cells = new Cell[dimension][dimension];
        backup = new Cell[dimension][dimension];
        for (int x = 0; x < dimension; ++x){
            for (int y = 0; y < dimension; ++y){
                cells[x][y] = new Cell(x, y);
                backup[x][y] = new Cell(x, y);
            }
        }
    }

    public void addDisplay(Display display){
        this.display = display;
        for (int x = 0; x < 2; ++x){
            this.gen_block();
        }
    }

    public void gen_block(){
        int num = ran_num();
        int position = ran_find_empty_block();
        if ((position == -1) && (All_merge() == false)){
            //end the game
        }
        if (num == 2){
            cells[position/4][position%4].set2();
        }
        else if (num == 4){
            cells[position/4][position%4].set4();
        }
        display.updateDisplay();
    }

    public int getValue(int x, int y){
        return cells[x][y].getNum();
    }

    public int getMax(){
        int i, j;
        int ans=0;
        for (i=0;i<dimension;i++){
            for (j=0;j<dimension;j++){
                if (getValue(i,j)>ans){
                    ans=getValue(i,j);
                }
            }
        }
        return ans;
    }

    public void merge_right(){
        copyBoard();
        R_skip_space();
        R_merge();
        R_skip_space();
        if (changed()) {
            gen_block();
            display.updateDisplay();
        }
    }

    public void merge_left(){
        copyBoard();
        L_skip_space();
        L_merge();
        L_skip_space();
        if (changed()) {
            gen_block();
            display.updateDisplay();
        }
    }

    public void merge_down(){
        copyBoard();
        D_skip_space();
        D_merge();
        D_skip_space();
        if (changed()) {
            gen_block();
            display.updateDisplay();
        }
    }

    public void merge_up(){
        copyBoard();
        U_skip_space();
        U_merge();
        U_skip_space();
        if (changed()) {
            gen_block();
            display.updateDisplay();
        }
    }

    private int ran_num(){
        int num = rn.nextInt(32) + 1;
        if (num % 4 == 0) {
            return 4;
        }
        else {
            return 2;
        }
    }

    private int ran_find_empty_block (){
        int start_block = 0;
        start_block = rn.nextInt(16) ;

        for (int i = 0; i < 50; ++i){
            start_block = rn.nextInt(16) ;
            if (cells[start_block/4][start_block%4].getNum() == 0 ) return start_block;
        }

        for (int i = start_block; i < 16; ++i){
            if (cells[start_block/4][start_block%4].getNum() == 0) return i;
        }
        for (int i = 0; i < start_block; ++i){
            if (cells[start_block/4][start_block%4].getNum() == 0) return i;
        }
        return -1;
    }

    private void R_skip_space(){
        for (int x = 0; x < dimension; ++x){
            for (int z = 1; z < dimension; ++z){
                for (int y = dimension - 1; y > 0; --y){
                    if (cells[x][y].getNum() == 0) {
                        cells[x][y].setNum(cells[x][y-1]);
                        cells[x][y-1].clearCell();
                    }
                }
            }
        }
    }

    private void R_merge(){
        for (int x = 0; x < dimension; ++x){
            for (int y = dimension - 1; y > 0; --y){
                if (cells[x][y].getNum() == cells[x][y-1].getNum()){
                    cells[x][y].doubleNum();
                    score = score + cells[x][y].getNum();
                    cells[x][y-1].clearCell();
                }
            }
        }
    }


    private void L_skip_space(){
        for (int x = 0; x < dimension; ++x){
            for (int z = 1; z < dimension; ++z){
                for (int y = 0; y < dimension - 1; ++y){
                    if (cells[x][y].getNum() == 0) {
                        cells[x][y].setNum(cells[x][y+1]);
                        cells[x][y+1].clearCell();
                    }
                }
            }
        }
    }

    private void L_merge(){
        for (int x = 0; x < dimension; ++x){
            for (int y = 0; y < dimension - 1; ++y){
                if (cells[x][y].getNum() == cells[x][y+1].getNum()){
                    cells[x][y].doubleNum();
                    score = score + cells[x][y].getNum();
                    cells[x][y+1].clearCell();
                }
            }
        }
    }

    private void U_skip_space(){
        for (int y = 0; y < dimension; ++y){
            for (int z = 1; z < dimension; ++z){
                for (int x = 0; x < dimension - 1; ++x){
                    if (cells[x][y].getNum() == 0) {
                        cells[x][y].setNum(cells[x+1][y]);
                        cells[x+1][y].clearCell();
                    }
                }
            }
        }
    }

    private void U_merge(){
        for (int y = 0; y < dimension; ++y){
            for (int x = 0; x < dimension - 1; ++x){
                if (cells[x][y].getNum() == cells[x+1][y].getNum()){
                    cells[x][y].doubleNum();
                    score = score + cells[x][y].getNum();
                    cells[x+1][y].clearCell();
                }
            }
        }
    }

    private void D_skip_space(){
        for (int y = 0; y < dimension; ++y){
            for (int z = 1; z < dimension; ++z){
                for (int x = dimension - 1; x > 0;  --x){
                    if (cells[x][y].getNum() == 0) {
                        cells[x][y].setNum(cells[x-1][y]);
                        cells[x-1][y].clearCell();
                    }
                }
            }
        }
    }

    private void D_merge(){
        for (int y = 0; y < dimension; ++y){
            for (int x = dimension - 1; x > 0; --x){
                if (cells[x][y].getNum() == cells[x-1][y].getNum()){
                    cells[x][y].doubleNum();
                    score = score + cells[x][y].getNum();
                    cells[x-1][y].clearCell();
                }
            }
        }
    }

    private boolean All_merge(){
        for (int x = 0; x < dimension; ++x){                //Right
            for (int y = dimension - 1; y > 0; --y){
                if (cells[x][y].getNum() == cells[x][y-1].getNum()){
                    return true;
                }
            }
        }

        for (int x = 0; x < dimension; ++x){
            for (int y = dimension - 1; y > 0; --y){        //left
                if (cells[x][y].getNum() == cells[x][y+1].getNum()){
                    return true;
                }
            }
        }

        for (int y = 0; y < dimension; ++y){
            for (int x = 0; x < dimension - 1; ++x){        //up
                if (cells[x][y].getNum() == cells[x+1][y].getNum()){
                    return true;
                }
            }
        }

        for (int y = 0; y < dimension; ++y){
            for (int x = 0; x < dimension - 1; ++x){        //Down
                if (cells[x][y].getNum() == cells[x-1][y].getNum()){
                    return true;
                }
            }
        }
        return false;
    }
    private void copyBoard(){
        for (int x = 0; x < dimension; ++x){
            for (int y = 0; y < dimension; ++y){
                backup[x][y].setNum(cells[x][y]);
            }
        }
    }

    private boolean changed(){
        for (int x = 0; x < dimension; ++x) {
            for (int y = 0; y < dimension; ++y) {
                if (backup[x][y].getNum() != cells[x][y].getNum()){
                    return true;
                }
            }
        }
        return false;
    }
}
