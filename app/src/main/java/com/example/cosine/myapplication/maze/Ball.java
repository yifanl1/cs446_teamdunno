package com.example.cosine.myapplication.maze;

import java.util.List;

class Ball {
    private final float BOUNCE_CONSTANT = -0.55f; // must be between -1 and 0
    private final float FRAME_TIME = 0.8f;
    private final float ACCEL_CONSTANT = 0.5f;
    private final float VEL_CONSTANT = 0.6f;

    private float x, y;
    private float dx, dy;
    private float lastX, lastY;
    private float size;

    Ball(float x, float y, float size) {
        this.x = x;
        this.y = y;
        this.size = size;

        lastX = x;
        lastY = y;
        dx = 0f;
        dy = 0f;
    }

    void update(float ddx, float ddy) {
        dx += ddx * ACCEL_CONSTANT * FRAME_TIME;
        dy += ddy * ACCEL_CONSTANT * FRAME_TIME;

        lastX = x;
        lastY = y;
        x -= (dx * VEL_CONSTANT * FRAME_TIME / 2);
        y -= (dy * VEL_CONSTANT * FRAME_TIME / 2);
    }

    void checkCollisions(List<MazeCell> cells, float xBound, float yBound) {
        for (MazeCell cell: cells) {
            if (cell.collidesWithNorthWall(this) || cell.collidesWithWestWall(this)) {
                if (cell.collidesWithNorthWall(this)) {
                    y = lastY;
                    verticalBounce();
                }
                if (cell.collidesWithWestWall(this)) {
                    x = lastX;
                    horizontalBounce();
                }
            }
        }

        // ensure ball is still in bounds
        if (x < 0 || x > xBound) {
            x = lastX;
            horizontalBounce();
        }

        if (y < 0 || y > yBound) {
            y = lastY;
            verticalBounce();
        }
    }

    private void horizontalBounce() {
        dx *= BOUNCE_CONSTANT;
    }

    private void verticalBounce() {
        dy *= BOUNCE_CONSTANT;
    }

    float getX() {
        return x;
    }

    float getY() {
        return y;
    }

    float getSize() {
        return size;
    }

}
