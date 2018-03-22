package com.example.cosine.myapplication.g2048;

public enum Direction {
    UP (1),
    RIGHT (2),
    DOWN (3),
    LEFT (4);

    private int n;

    Direction(int n) {
        this.n = n;
    }

    public int getN() {
        return n;
    }

    public int rightRotationsTo(Direction dir2) {
        return ((dir2.getN() - n) + 4) % 4; // ensure positive result
    }

}
