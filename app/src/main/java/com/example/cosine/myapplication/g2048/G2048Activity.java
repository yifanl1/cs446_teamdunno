package com.example.cosine.myapplication.g2048;

import android.os.Bundle;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.content.Intent;

import com.example.cosine.myapplication.GameState;
import com.example.cosine.myapplication.R;


public class G2048Activity extends AppCompatActivity{
    private GestureDetectorCompat mDetector;
    private Board board;
    private TextView swipe;
    private Display display;

    String mode;
    Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_g2048);
        mDetector = new GestureDetectorCompat(this,new MyGestureListener());

        // handle getting operation mode
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            mode = extras.getString("mode");
        }

        board = new Board();
        Button buttons[][] = new Button[4][4];

        buttons[0][0] = (Button) findViewById(R.id.cell00);
        buttons[0][1] = (Button) findViewById(R.id.cell01);
        buttons[0][2] = (Button) findViewById(R.id.cell02);
        buttons[0][3] = (Button) findViewById(R.id.cell03);

        buttons[1][0] = (Button) findViewById(R.id.cell10);
        buttons[1][1] = (Button) findViewById(R.id.cell11);
        buttons[1][2] = (Button) findViewById(R.id.cell12);
        buttons[1][3] = (Button) findViewById(R.id.cell13);

        buttons[2][0] = (Button) findViewById(R.id.cell20);
        buttons[2][1] = (Button) findViewById(R.id.cell21);
        buttons[2][2] = (Button) findViewById(R.id.cell22);
        buttons[2][3] = (Button) findViewById(R.id.cell23);

        buttons[3][0] = (Button) findViewById(R.id.cell30);
        buttons[3][1] = (Button) findViewById(R.id.cell31);
        buttons[3][2] = (Button) findViewById(R.id.cell32);
        buttons[3][3] = (Button) findViewById(R.id.cell33);

        setButtonPassThrough(buttons);

        swipe = (TextView) findViewById(R.id.scoreView);

        display = new Display(this, buttons);
        display.setBoard(board);
        display.updateDisplay();

        if (mode != null && mode.equals("alarm")) {
            board.setScoredMode(100);
        }
    }

    private void setButtonPassThrough(Button[][] buttons) {
        for (Button[] row: buttons) {
            for (Button button: row) {
                button.setClickable(false);
                button.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View view, MotionEvent motionEvent) {
                        return false;
                    }
                });
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        this.mDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    class MyGestureListener extends GestureDetector.SimpleOnGestureListener {
        private static final String DEBUG_TAG = "Gestures";

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            final float SWIPE_MIN_DISTANCE = 80f;
            final float SWIPE_THRESHOLD_VELOCITY = 100f;

            try {
                float dx = e1.getX() - e2.getX();
                float dy = e1.getY() - e2.getY();
                Direction moveDir = null;

                if (Math.abs(dx) >= Math.abs(dy) && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                    if (dx > SWIPE_MIN_DISTANCE) {
                        moveDir = Direction.LEFT;
                    } else if (-1 * dx > SWIPE_MIN_DISTANCE) {
                        moveDir = Direction.RIGHT;
                    }
                } else if (Math.abs(dy) > Math.abs(dx) && Math.abs(velocityY) > SWIPE_THRESHOLD_VELOCITY) {
                    if (dy > SWIPE_MIN_DISTANCE) {
                        moveDir = Direction.UP;
                    } else if (-1 * dy > SWIPE_MIN_DISTANCE) {
                        moveDir = Direction.DOWN;
                    }
                }


                if (moveDir != null) {
                    board.makeMove(moveDir);
                    display.updateDisplay();
                    swipe.setText("Score: "+ Integer.toString(board.getScore()));

                    if (board.getGameState() == GameState.WON) {
                        System.out.println("finished");
                        G2048Activity.this.finish();
                    } else if (board.getGameState() == GameState.LOST) {
                        swipe.setText("You Lose!");
                    }
                    return true;
                }
            } catch (Exception e) {

            }
            return false;
        }
    }
}
