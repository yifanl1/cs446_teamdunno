package com.example.cosine.myapplication;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.cosine.myapplication.maze.MazeActivity;
import com.example.cosine.myapplication.g2048.G2048Activity;

public class ChallengeSwitchingActivity extends AppCompatActivity {
    MediaPlayer mediaPlayer;
    Intent intent;

    Intent challengeIntent;
    String gameName;
    String ifStop;
    Button stop;
    TextView tv;
    boolean flag, flag2, flag3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenge_switching);

        mediaPlayer=new MediaPlayer();
        mediaPlayer=MediaPlayer.create(this, R.raw.onelasttime);
        mediaPlayer.start();

        tv=(TextView)findViewById(R.id.textView);
        stop=(Button)findViewById(R.id.button);
        stop.setVisibility(View.INVISIBLE);

        intent = getIntent();
        gameName = intent.getStringExtra("name");
        tv.setText("gameName="+gameName+";");


        Class challengeClass = null;
        switch(gameName) {
            case "Jump":
                challengeClass = JumpActivity.class;
                break;
            case "Maze":
                challengeClass = MazeActivity.class;
                break;
            case "2048":
                challengeClass = G2048Activity.class;
                break;
            default:
                challengeIntent = null;
                break;
        }
        if (challengeClass != null) {
            challengeIntent = new Intent(ChallengeSwitchingActivity.this, challengeClass);
            challengeIntent.putExtra("mode", "alarm");
        }

        new AlertDialog.Builder(ChallengeSwitchingActivity.this).setTitle("Alarm").setMessage("Get up right now! Play "+gameName+" to stop the alarm!")
                .setPositiveButton("Sure!", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        goToGame();

                    }
                }).show();

    }

    public void goToGame(){
        if (challengeIntent == null) {
            return;
        }
        startActivity(challengeIntent);
        tv.setText("Congratulations!  You have passed the challenge!");
        stop.setVisibility(View.VISIBLE);
        stop.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                mediaPlayer.stop();
                ChallengeSwitchingActivity.this.finish();
            }
        });
        //mediaPlayer.stop();

    }
}
