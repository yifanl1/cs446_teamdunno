package com.example.cosine.myapplication.maze;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Stack;

class MazeGrid {
    private Random rng;

    private Ball ball;
    private List<MazeCell> cells;
    private MazeCell goalCell;

    private final float BALL_RATIO = 16;
    private final float CELL_RATIO = 8;
    private final float WALL_RATIO = 10;

    private float cellSize;
    private float ballSize;
    private float wallThickness;

    private float xBound;
    private float yBound;

    MazeGrid(float xBound, float yBound) {
        float bound = Math.min(xBound, yBound);
        ballSize = bound / BALL_RATIO;
        cellSize = bound / CELL_RATIO;
        wallThickness = cellSize / WALL_RATIO;

        rng = new Random();

        this.xBound = xBound - ballSize;
        this.yBound = yBound - ballSize;
        generateMaze();
    }

    private void generateMaze() {
        cells = new ArrayList<>();
        // divide into grid
        int gridXMax = (int) Math.ceil(xBound / cellSize); // fine to go a little past the edges
        int gridYMax = (int) Math.ceil(yBound / cellSize);

        for (int i = 0; i < gridXMax; i++) {
            for (int j = 0; j < gridYMax; j++) {
                cells.add(new MazeCell(i, j, i * gridYMax + j, cellSize, wallThickness));
            }
        }

        for (int i = 0; i < gridXMax; i++) {
            for (int j = 0; j < gridYMax; j++){
                int index = (i*gridYMax) + j;
                if (i > 0) {
                    cells.get(index).addNeighbour(cells.get(index - gridYMax));
                } else {
                    cells.get(index).removeWestWall();
                }
                if (i < gridXMax - 1) {
                    cells.get(index).addNeighbour(cells.get(index + gridYMax));
                }
                if (j > 0) {
                    cells.get(index).addNeighbour(cells.get(index - 1));
                } else {
                    cells.get(index).removeNorthWall();
                }
                if (j < gridYMax - 1) {
                    cells.get(index).addNeighbour(cells.get(index + 1));
                }
            }
        }

        removeWalls();
        chooseStartAndGoal(gridXMax, gridYMax);
    }

    private void removeWalls(){
        // recursively backtrack through the grid to generate a proper maze
        Stack<MazeCell> st = new Stack<>();
        int n = cells.size();
        List<Integer> visited = new ArrayList<>(Collections.nCopies(n, 0));
        MazeCell currCell = cells.get(rng.nextInt(n));
        visited.set(currCell.getIndex(), 1);

        while (Collections.min(visited) == 0) {
            List<MazeCell> unvisitedNeighbours = new ArrayList<>();
            for (MazeCell cell: currCell.getNeighbours()) {
                if (visited.get(cell.getIndex()) == 0) {
                    unvisitedNeighbours.add(cell);
                }
            }

            if (!unvisitedNeighbours.isEmpty()) {
                Collections.shuffle(unvisitedNeighbours);
                MazeCell nextCell = unvisitedNeighbours.get(0);
                st.push(currCell);
                int neighbourType = currCell.neighbourType(nextCell);
                switch(neighbourType) {
                    case 1:
                        currCell.removeNorthWall();
                        break;
                    case 2:
                        nextCell.removeNorthWall();
                        break;
                    case 3:
                        currCell.removeWestWall();
                        break;
                    case 4:
                        nextCell.removeWestWall();
                        break;
                    default:
                        break;
                }
                currCell = nextCell;
                visited.set(currCell.getIndex(), 1);
            } else {
                currCell = st.pop();
            }
        }
    }

    private void chooseStartAndGoal(int gridXMax, int gridYMax) {
        // ensure start and goal points are not close to each other
        int i = rng.nextInt(gridXMax/3);
        int j = rng.nextInt(gridYMax/3);
        float xStart = i * cellSize + cellSize /2;
        float yStart = j * cellSize + cellSize /2;
        ball = new Ball(xStart, yStart, ballSize);

        i = rng.nextInt(gridXMax/3) + (gridXMax / 2);
        j = rng.nextInt(gridYMax/3) + (gridYMax / 2);
        goalCell = cells.get(i*gridYMax + j);
        goalCell.setGoal();
    }

    void update(float ddx, float ddy) {
        ball.update(ddx, ddy);
        ball.checkCollisions(cells, xBound, yBound);
    }

    boolean checkWin() {
        return goalCell.contains(ball);
    }

    List<MazeCell> getCells() {
        return cells;
    }

    Ball getBall() {
        return ball;
    }

    float getCellSize() {
        return cellSize;
    }

    float getBallSize() {
        return ballSize;
    }

    float getWallThickness() {
        return wallThickness;
    }
}
