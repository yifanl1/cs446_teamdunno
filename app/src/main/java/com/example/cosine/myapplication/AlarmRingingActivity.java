package com.example.cosine.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.view.View;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Button;

import java.util.Random;

public class AlarmRingingActivity extends AppCompatActivity {

    MediaPlayer mediaPlayer;
    TextView question;
    EditText answer;
    Button confirm;

    int num1, num2, ans, correctCount, mode, userAns;
    String operators[]=new String[]{"+","-","×","÷"};
    String questionDisplay=new String();
    Random rand=new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_ringing);

        mediaPlayer=new MediaPlayer();
        mediaPlayer=MediaPlayer.create(this, R.raw.onelasttime);
        mediaPlayer.start();

        question=(TextView)findViewById(R.id.question);
        answer=(EditText)findViewById(R.id.answer);
        confirm=(Button)findViewById(R.id.confirm);

        Random rand=new Random();

        new AlertDialog.Builder(AlarmRingingActivity.this).setTitle("Alarm").setMessage("Get up right now! Do some math to stop the alarm!")
                .setPositiveButton("Sure!", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which){
                        // maybe some transition to other games
                    }
                }).show();

        correctCount=0;

        generateMathProb();

        confirm.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                userAns=Integer.parseInt(answer.getText().toString());
                if (userAns==ans){
                    correctCount++;
                    if (correctCount==5){
                        new AlertDialog.Builder(AlarmRingingActivity.this).setTitle("Congratulations").setMessage("You've passed the challenge!")
                                .setPositiveButton("Stop Alarm", new DialogInterface.OnClickListener(){
                                    @Override
                                    public void onClick(DialogInterface dialog, int which){
                                        mediaPlayer.stop();
                                        AlarmRingingActivity.this.finish();
                                    }
                                }
                        ).show();
                    } else {
                        new AlertDialog.Builder(AlarmRingingActivity.this).setTitle("Correct").setMessage("You did this question right!")
                                .setPositiveButton("Next Question", new DialogInterface.OnClickListener(){
                                            @Override
                                            public void onClick(DialogInterface dialog, int which){
                                                generateMathProb();
                                            }
                                        }
                                ).show();
                    }
                } else {
                    new AlertDialog.Builder(AlarmRingingActivity.this).setTitle("Wrong").setMessage("Your answer is wrong!")
                            .setPositiveButton("Try Again", new DialogInterface.OnClickListener(){
                                        @Override
                                        public void onClick(DialogInterface dialog, int which){
                                            answer.setText("");
                                        }
                                    }
                            ).show();
                }
            }
        });

    }

    public void generateMathProb(){
        mode=rand.nextInt(4);
        if (mode==0){ // add
            num1=rand.nextInt(100);
            num2=rand.nextInt(100);
            ans=num1+num2;
        }
        if (mode==1){ // subtract
            num2=rand.nextInt(100);
            ans=rand.nextInt(100);
            num1=num2+ans;
        }
        if (mode==2) {
            num1=rand.nextInt(100);
            num2=rand.nextInt(8)+2;
            ans=num1*num2;
        }
        if (mode==3){
            num2=rand.nextInt(8)+2;
            ans=rand.nextInt(100);
            num1=ans*num2;
        }
        questionDisplay=Integer.toString(num1)+operators[mode]+Integer.toString(num2);
        question.setText(questionDisplay);
        answer.setText("");
    }
}
