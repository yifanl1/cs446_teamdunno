package com.example.cosine.myapplication.maze;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.View;

import com.example.cosine.myapplication.R;
import com.example.cosine.myapplication.MainActivity;

public class MazeActivity extends AppCompatActivity implements SensorEventListener {
    private SensorManager sensorManager;
    private Sensor accelerometer;
    private CountDownTimer mazeTimer;

    private MazeGrid mazeGrid;

    public MazeActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        Point size = new Point();
        Display display = getWindowManager().getDefaultDisplay();
        display.getSize(size);

        mazeGrid = new MazeGrid(size.x, size.y);
        mazeTimer = new CountDownTimer(30000, 1000) {
            @Override
            public void onTick(long l) {
            }

            @Override
            public void onFinish() {
                gameOver();
            }
        };

        MazeView ballView = new MazeView(this);
        setContentView(ballView);
    }

    @Override
    protected void onStart() {
        super.onStart();
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_GAME);
        mazeTimer.start();
    }

    @Override
    protected void onStop() {
        sensorManager.unregisterListener(this);
        super.onStop();
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        int sensorType = sensorEvent.sensor.getType();
        if (sensorType == Sensor.TYPE_ACCELEROMETER) {
            float ddx = sensorEvent.values[0];
            float ddy = -sensorEvent.values[1];
            mazeGrid.update(ddx, ddy);
            if (mazeGrid.checkWin()) {
                gameOver();
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
    }

    private void gameOver() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private class MazeView extends View {
        private Bitmap ball, hWall, vWall, goal;

        public MazeView(Context context) {
            super(context);
            Bitmap ballSrc = BitmapFactory.decodeResource(getResources(), R.drawable.ball);
            Bitmap hWallSrc = BitmapFactory.decodeResource(getResources(), R.drawable.wall_h);
            Bitmap vWallSrc = BitmapFactory.decodeResource(getResources(), R.drawable.wall_v);
            Bitmap goalSrc = BitmapFactory.decodeResource(getResources(), R.drawable.goal);

            int ballSize = (int)mazeGrid.getBallSize();
            int cellSize = (int)mazeGrid.getCellSize();
            int wallThickness = (int)mazeGrid.getWallThickness();

            ball = Bitmap.createScaledBitmap(ballSrc, ballSize, ballSize, true);
            hWall = Bitmap.createScaledBitmap(hWallSrc, cellSize + wallThickness, wallThickness, true);
            vWall = Bitmap.createScaledBitmap(vWallSrc, wallThickness, cellSize + wallThickness, true);
            goal = Bitmap.createScaledBitmap(goalSrc, cellSize, cellSize, true);
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
}
