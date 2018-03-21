package com.example.cosine.myapplication.g2048;

import android.os.Bundle;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.Button;
import android.widget.TextView;
import android.content.Context;
import android.content.Intent;

import com.example.cosine.myapplication.R;


public class G2048Activity extends AppCompatActivity{
    private GestureDetectorCompat mDetector;
    private Board board;
    private TextView swipe;

    String mode;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_g2048);
        mDetector = new GestureDetectorCompat(this,new MyGestureListener());

        board = new Board();
        Button blocks[][] = new Button[4][4];

        intent=getIntent();
        mode=intent.getStringExtra("mode");

        blocks[0][0] = (Button) findViewById(R.id.cell00);
        blocks[0][1] = (Button) findViewById(R.id.cell01);
        blocks[0][2] = (Button) findViewById(R.id.cell02);
        blocks[0][3] = (Button) findViewById(R.id.cell03);

        blocks[1][0] = (Button) findViewById(R.id.cell10);
        blocks[1][1] = (Button) findViewById(R.id.cell11);
        blocks[1][2] = (Button) findViewById(R.id.cell12);
        blocks[1][3] = (Button) findViewById(R.id.cell13);

        blocks[2][0] = (Button) findViewById(R.id.cell20);
        blocks[2][1] = (Button) findViewById(R.id.cell21);
        blocks[2][2] = (Button) findViewById(R.id.cell22);
        blocks[2][3] = (Button) findViewById(R.id.cell23);

        blocks[3][0] = (Button) findViewById(R.id.cell30);
        blocks[3][1] = (Button) findViewById(R.id.cell31);
        blocks[3][2] = (Button) findViewById(R.id.cell32);
        blocks[3][3] = (Button) findViewById(R.id.cell33);

        swipe = (TextView) findViewById(R.id.textView);


        Display display = new Display(this, board, blocks);
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
            int SWIPE_MIN_DISTANCE = 80;
            int SWIPE_THRESHOLD_VELOCITY = 100;

            try {

                // right to left swipe
                if (e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE
                        && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                    swipe.setText("Left");
                    System.out.println("LEFT");
                    board.merge_left();
                }
                // left to right swipe
                else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE
                        && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                    swipe.setText("Right");
                    System.out.println("RIGHT");
                    board.merge_right();
                }

                else if (e1.getY() - e2.getY() > SWIPE_MIN_DISTANCE
                        && Math.abs(velocityY) > SWIPE_THRESHOLD_VELOCITY) {
                    swipe.setText("Up");
                    System.out.println("UP");
                    board.merge_up();
                }

                else if (e2.getY() - e1.getY() > SWIPE_MIN_DISTANCE
                        && Math.abs(velocityY) > SWIPE_THRESHOLD_VELOCITY) {
                    swipe.setText("Down");
                    System.out.println("DOWN");
                    board.merge_down();
                }

                int max=board.getMax();
                if (mode!=null && mode.equals("alarm")){
                    if (max>17) {
                        G2048Activity.this.finish();
                    }
                }
            } catch (Exception e) {

            }
            return false;
        }
    }
}
