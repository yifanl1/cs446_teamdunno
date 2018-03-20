package com.example.cosine.myapplication.maze;

class Wall {
    private float x;
    private float y;
    private float width;
    private float height;
    private MazeCell cell;

    Wall(float x, float y, float width, float height, MazeCell cell) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.cell = cell;
    }

    boolean collidesWithBall(Ball ball) {
        float ballRad = ball.getSize() / 2;
        float ballXCentrePos = ball.getX() + ballRad;
        float ballYCentrePos = ball.getY() + ballRad;
        float halfWallWidth = width / 2;
        float halfWallHeight = height / 2;
        return MazeUtil.circleRectCollision(ballXCentrePos, ballYCentrePos, ballRad,
                x + halfWallWidth, y + halfWallHeight, halfWallWidth, halfWallHeight);
    }
}
