package com.example.cosine.myapplication.g2048;

public class Cell {
    private int num;
    private int x;
    private int y;

    Cell(int a, int b){
        x = a;
        y = b;
        num = 0;
    }

    public int getNum() {
        return num;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setNum(Cell cell) { num = cell.getNum(); }

    public void set2(){ num = 2; }

    public void set4(){ num = 4; }

    public void doubleNum() { num *= 2; }

    public void clearCell() { num = 0; }
}
