package com.example.cosine.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.*;
import android.content.*;
import android.view.View;
//import android.app.AlarmManager;


public class MainActivity extends AppCompatActivity {

    Button sa;
    Button gm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sa=(Button)findViewById(R.id.setAlarm);
        sa.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AlarmListActivity.class);
                startActivity(intent);

            }
        });
        gm=(Button)findViewById(R.id.gameMode);
        gm.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(MainActivity.this, GameModeActivity.class);
                startActivity(intent);
            }
        });

    }
}
