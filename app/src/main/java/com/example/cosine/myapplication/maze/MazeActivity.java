package com.example.cosine.myapplication.maze;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Point;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;

import com.example.cosine.myapplication.GameState;
import com.example.cosine.myapplication.MainActivity;

public class MazeActivity extends AppCompatActivity implements SensorEventListener {
    private String mode = null;

    private SensorManager sensorManager;
    private Sensor accelerometer;
    private MazeGrid mazeGrid;
    private CountDownTimer mazeTimer;


    public MazeActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        // handle getting operation mode
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            mode = extras.getString("mode");
        }

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
                finishActivity();
            }
        };

        MazeView mazeView = new MazeView(this);
        mazeView.setMaze(mazeGrid);

        this.setContentView(mazeView);
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
            if (mazeGrid.getGameState() == GameState.WON) {
                finishActivity();
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
    }

    private void finishActivity() {
        if (mode != null && mode.equals("alarm")){
            MazeActivity.this.finish();
            return;
        }
        Intent quitIntent = new Intent(this, MainActivity.class);
        startActivity(quitIntent);
    }
}
