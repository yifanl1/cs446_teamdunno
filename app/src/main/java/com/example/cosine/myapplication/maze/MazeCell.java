package com.example.cosine.myapplication.maze;


import java.util.ArrayList;
import java.util.List;

class MazeCell {

    // only keep track of the north and west wall,
    // other cells will track the south and east walls
    private Wall northWall;
    private Wall westWall;
    private List<MazeCell> neighbours = new ArrayList<>();
    private int xIndex;
    private int yIndex;
    private int index;
    private float cellSize;
    private float x, y;
    private boolean isGoal;

    MazeCell(int xIndex, int yIndex, int index, float cellSize, float wallThickness) {
        this.xIndex = xIndex;
        this.yIndex = yIndex;
        this.index = index;
        this.cellSize = cellSize;

        x = xIndex * cellSize;
        y = yIndex * cellSize;

        northWall = new Wall(x, y, cellSize + wallThickness, wallThickness, this);
        westWall = new Wall(x, y, wallThickness, cellSize + wallThickness, this);
        isGoal = false;
    }

    void addNeighbour(MazeCell neighbour) {
        neighbours.add(neighbour);
    }

    int neighbourType(MazeCell neighbour) {
        int dx = this.xIndex - neighbour.getXIndex();
        int dy = this.yIndex - neighbour.getYIndex();
        // 1 = neighbour is north of this cell, 2 = south, 3 = west, 4 = east, 0 = not a neighbour
        if (dx == 0 && dy == 1) {
            return 1;
        }
        if (dx == 0 && dy == -1) {
            return 2;
        }
        if (dx == 1 && dy == 0) {
            return 3;
        }
        if (dx == -1 && dy == 0) {
            return 4;
        }
        return 0;
    }

    boolean hasNorthWall() {
        return northWall != null;
    }

    boolean hasWestWall() {
        return westWall != null;
    }

    void removeNorthWall() {
        this.northWall = null;
    }

    void removeWestWall() {
        this.westWall = null;
    }

    List<MazeCell> getNeighbours() {
        return neighbours;
    }

    float getX() {
        return x;
    }

    float getY() {
        return y;
    }

    int getXIndex() {
        return xIndex;
    }

    int getYIndex() {
        return yIndex;
    }

    int getIndex() {
        return index;
    }

    boolean contains(Ball ball) {
        float ballRad = ball.getSize() / 2;
        float ballXCentrePos = ball.getX() + ballRad;
        float ballYCentrePos = ball.getY() + ballRad;
        float halfCellSize = cellSize / 2;
        return MazeUtil.circleRectCollision(ballXCentrePos, ballYCentrePos, ballRad,
                x + halfCellSize, y + halfCellSize, halfCellSize, halfCellSize);
    }

    boolean collidesWithNorthWall(Ball ball) {
        return hasNorthWall()&& northWall.collidesWithBall(ball);
    }

    boolean collidesWithWestWall(Ball ball) {
        return hasWestWall() && westWall.collidesWithBall(ball);
    }

    void setGoal() {
        isGoal = true;
    }

    boolean isGoalCell() {
        return isGoal;
    }
}
