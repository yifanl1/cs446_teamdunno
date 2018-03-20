package com.example.cosine.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.view.View;

public class GameModeActivity extends AppCompatActivity {

    Button jumpButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_mode);

        jumpButton=(Button)findViewById(R.id.jump);

        jumpButton.setOnClickListener(new View.OnClickListener(){
           @Override
           public void onClick(View v){
               Intent intent = new Intent(GameModeActivity.this, JumpActivity.class);
               startActivity(intent);
           }
        });
    }
}
