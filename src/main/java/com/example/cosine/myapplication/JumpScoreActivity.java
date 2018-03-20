package com.example.cosine.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class JumpScoreActivity extends AppCompatActivity {

    Button mainButton;
    Button restartButton;
    TextView scoreView;
    MyCanvas mycanvas;
    String score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        score = intent.getStringExtra(JumpActivity.EXTRA_MESSAGE);
        setContentView(R.layout.activity_jump_score);

        mainButton = (Button)  findViewById(R.id.main);
        restartButton = (Button)  findViewById(R.id.restart);
        scoreView = (TextView) findViewById(R.id.Score);
        //ycanvas = (MyCanvas) findViewById(R.id.paint_board);
        scoreView.setText("Score: " + score);
        mainButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                changetoMain();

            }
        });
        restartButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                changetoGame();

            }
        });


    }

    public void changetoGame( ) {
        Intent intent = new Intent(this, JumpActivity.class);
        startActivity(intent);
    }

    public void changetoMain( ) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}
