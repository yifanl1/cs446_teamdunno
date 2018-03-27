package com.example.cosine.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.view.View;

import com.example.cosine.myapplication.g2048.G2048Activity;
import com.example.cosine.myapplication.maze.MazeActivity;

public class GameModeActivity extends AppCompatActivity {

    Button jumpButton;
    Button mazeButton;
    Button g2048Button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_mode);
        this.setTitle("Games");
        jumpButton=(Button)findViewById(R.id.jump);
        jumpButton.setOnClickListener(new View.OnClickListener(){
           @Override
           public void onClick(View v){
               Intent intent = new Intent(GameModeActivity.this, JumpActivity.class);
               intent.putExtra("mode","game");
               startActivity(intent);
           }
        });


        mazeButton = (Button)findViewById(R.id.maze);
        mazeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent intent = new Intent(GameModeActivity.this, MazeActivity.class);
                intent.putExtra("mode","game");
                startActivity(intent);
            }
        });

        g2048Button=(Button)findViewById(R.id.two);
        g2048Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent intent = new Intent(GameModeActivity.this, G2048Activity.class);
                intent.putExtra("mode","game");
                startActivity(intent);
            }
        });
    }
}
