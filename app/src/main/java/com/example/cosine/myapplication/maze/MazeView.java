package com.example.cosine.myapplication.maze;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.View;

import com.example.cosine.myapplication.R;

class MazeView extends View {
    private Bitmap ball, hWall, vWall, goal;
    private MazeGrid mazeGrid;

    public MazeView(Context context) {
        super(context);
    }

    public void setMaze(MazeGrid mazeGrid) {
        this.mazeGrid = mazeGrid;
        loadAndScaleBitmaps();
    }

    private void loadAndScaleBitmaps() {
        int ballSize = (int)mazeGrid.getBallSize();
        int cellSize = (int)mazeGrid.getCellSize();
        int wallThickness = (int)mazeGrid.getWallThickness();

        ball = loadAndScaleBitmap(ballSize, ballSize, R.drawable.maze_ball);
        hWall = loadAndScaleBitmap(cellSize + wallThickness, wallThickness, R.drawable.maze_wall_h);
        vWall = loadAndScaleBitmap(wallThickness, cellSize + wallThickness, R.drawable.maze_wall_v);
        goal = loadAndScaleBitmap(cellSize, cellSize, R.drawable.maze_goal);
    }

    private Bitmap loadAndScaleBitmap(int w, int h, int resId) {
        Bitmap bmpSrc = BitmapFactory.decodeResource(getResources(), resId);
        return Bitmap.createScaledBitmap(bmpSrc, w, h, true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Ball b = mazeGrid.getBall();
        drawMaze(canvas);
        canvas.drawBitmap(ball, b.getX(), b.getY(), null);
        invalidate();
    }

    private void drawMaze(Canvas canvas) {
        for (MazeCell cell: mazeGrid.getCells()) {
            float cellX = cell.getX();
            float cellY = cell.getY();
            if (cell.isGoalCell()) {
                canvas.drawBitmap(goal, cellX, cellY, null);
            }
            if (cell.hasNorthWall()) {
                canvas.drawBitmap(hWall, cellX, cellY, null);
            }
            if (cell.hasWestWall()) {
                canvas.drawBitmap(vWall, cellX, cellY, null);
            }
        }
    }
}