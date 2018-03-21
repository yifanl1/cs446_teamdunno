package com.example.cosine.myapplication;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import java.util.Random;
import android.animation.ObjectAnimator;
import  android.animation.AnimatorSet;
import android.widget.TextView;
import android.view.View.OnTouchListener;
import 	android.view.MotionEvent;
import android.app.AlertDialog;
import android.content.DialogInterface;

public class JumpActivity extends AppCompatActivity {

    MyCanvas mycanvas;
    public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";
    boolean lost = false;
    int score;
    int temp;
    String mode;
    Intent intent;

    protected void onCreate(Bundle savedInstanceState) {
        intent=getIntent();
        mode=intent.getStringExtra("mode");

        score = 0;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jump);
        mycanvas = (MyCanvas) findViewById(R.id.paint_board);
        mycanvas.initialGame();
        /*mycanvas.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
               jump();
            }
        });*/
        mycanvas.setOnTouchListener(new MyOnTouchListener());

    }

    public void jump( float period) {

        float period1 = period;


        float xend = (float) period1  * 100;



        //Random rand = new Random();
        //float xend = rand.nextInt(500) + 75;

        ObjectAnimator animateLeft = ObjectAnimator.ofFloat(mycanvas, "left", mycanvas.cleft, mycanvas.cleft+ xend);
        ObjectAnimator animateRight = ObjectAnimator.ofFloat(mycanvas, "right", mycanvas.cright, mycanvas.cright+ xend);
        ObjectAnimator animateTop = ObjectAnimator.ofFloat(mycanvas, "top", mycanvas.ctop, mycanvas.ctop-350, mycanvas.ctop);
        ObjectAnimator animateBottom = ObjectAnimator.ofFloat(mycanvas, "bottom", mycanvas.cbottom, mycanvas.cbottom -350, mycanvas.cbottom);
        AnimatorSet rectAnimation = new AnimatorSet();
        rectAnimation.playTogether(animateLeft, animateRight,animateTop ,animateBottom);
        rectAnimation.setDuration(1000).start();
        mycanvas.setC(mycanvas.cleft+ xend,mycanvas.ctop ,mycanvas.cright+ xend, mycanvas.cbottom );
        mycanvas.xend = xend;
        rectAnimation.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                if ( (mycanvas.cright <= mycanvas.left &&  mycanvas.xend > 100)  || mycanvas.cleft  >= mycanvas.right ) {

                    ObjectAnimator animateTop1 = ObjectAnimator.ofFloat(mycanvas, "top", mycanvas.ctop, mycanvas.ctop + 350);
                    //animateTop1.setStartDelay(1000);
                    ObjectAnimator animateBottom1 = ObjectAnimator.ofFloat(mycanvas, "bottom", mycanvas.cbottom, mycanvas.cbottom + 350);
                    //animateBottom1.setStartDelay(1000);
                    ObjectAnimator animateLeft1 = ObjectAnimator.ofFloat(mycanvas, "left", mycanvas.cleft, mycanvas.cleft);
                    //animateLeft1.setStartDelay(1000);
                    ObjectAnimator animateRight1 = ObjectAnimator.ofFloat(mycanvas, "right", mycanvas.cright, mycanvas.cright);
                    //animateRight1.setStartDelay(1000);
                    AnimatorSet rectAnimation1 = new AnimatorSet();
                    rectAnimation1.playTogether(animateLeft1, animateRight1, animateTop1, animateBottom1);
                    rectAnimation1.getStartDelay();
                    rectAnimation1.setDuration(500).start();
                    rectAnimation1.addListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);

                            loseGame();
                        }
                    });
                } else if (mycanvas.cright <= mycanvas.left &&  mycanvas.xend <= 100) {

                } else {
                    mycanvas.score += 1;

                    if (mode!=null && mode.equals("alarm")) {
                        if (mycanvas.score>=1){
                            JumpActivity.this.finish();
                        }
                    }

                    mycanvas.newRect();
                }
            }
        });

    }

    public void loseGame( ) {
        score = mycanvas.score;

        Intent intent = new Intent(this, JumpScoreActivity.class);
        intent.putExtra(EXTRA_MESSAGE, Integer.toString(score));
        intent.putExtra("mode",mode);
        startActivity(intent);
        this.finish();
    }

    class MyOnTouchListener implements OnTouchListener {

        //float time = 0;
        long time;

        @Override
        public boolean onTouch(View v, MotionEvent event) {

            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:

                    time = System.currentTimeMillis()/1000;;

                    System.out.println("press");

                    break;
                case MotionEvent.ACTION_MOVE:

                    System.out.println("move");
                    break;
                case MotionEvent.ACTION_UP:

                    long curtime = System.currentTimeMillis()/1000;
                    long period = curtime - time;
                    //time = 0;
                    period=period*4;
                    jump(period);

                    System.out.println("release");
                    break;
                default:
                    break;
            }
            return true;
        }

    }

}

