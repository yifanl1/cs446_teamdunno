package com.example.cosine.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.app.AlarmManager;
import android.widget.*;
import android.view.*;
import android.content.*;
import android.app.*;

import java.util.*;

public class setAlarmActivity extends AppCompatActivity {

    AlarmManager alarmManager;
    Button setButton;
    Button changeButton;
    Button changeButton2;
    Button changeButton3;
    TimePicker timePicker;
    Intent intent;
    PendingIntent pi;
    Calendar c;
    TextView repeatInfo;
    TextView gameInfo;
    TextView ringtoneInfo;
    boolean repeatWKDs[];
    boolean daysChecked[];
    String weekDays[];
    String games[];
    String sampleSongList[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_alarm);

        intent=new Intent(this,AlarmRingingActivity.class);
        pi=PendingIntent.getActivity(this,0,intent,0);

        timePicker=(TimePicker)findViewById(R.id.timePicker);
        alarmManager=(AlarmManager)getSystemService(ALARM_SERVICE);

        repeatWKDs=new boolean[]{false, false, false, false, false, false ,false};
        daysChecked=new boolean[]{false, false, false, false, false, false ,false};
        weekDays=new String[]{"Monday","Tuesday","Wednesday","Thursday","Friday","Saturday","Sunday"};
        games=new String[]{"Math", "Jump", "Maze", "2048"};
        sampleSongList=new String[]{"Finesse","I Want You To Know","One Last Time","Perfect","Rockstar"};

        repeatInfo=(TextView)findViewById(R.id.repeat);
        gameInfo=(TextView)findViewById(R.id.challenge);
        ringtoneInfo=(TextView)findViewById(R.id.ringtone);

        setButton=(Button)findViewById(R.id.set);
        setButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                int hour=timePicker.getHour();
                int minute=timePicker.getMinute();

                c=Calendar.getInstance();
                c.setTimeInMillis(System.currentTimeMillis());
                long currentTime=c.getTimeInMillis();

                String shour=Integer.toString(hour);

                if (hour<10){
                    shour="0"+shour;
                }

                String sminute=Integer.toString(minute);

                if (minute<10) {
                    sminute = "0" + sminute;
                }
                c.set(Calendar.HOUR_OF_DAY, hour);
                c.set(Calendar.MINUTE, minute);
                c.set(Calendar.SECOND, 0);

                long ringTime=c.getTimeInMillis();
                long timeOfDay=1000*60*60*24;

                //timeOfDay=1000*60*2;

                if (ringTime<currentTime){
                    ringTime=ringTime+timeOfDay;
                }
                alarmManager.set(AlarmManager.RTC_WAKEUP,ringTime,pi);

                Toast.makeText(setAlarmActivity.this,"Alarm is set to "+shour+":"+sminute,Toast.LENGTH_SHORT).show();
                changetoMain();
            }
        });


        changeButton=(Button)findViewById(R.id.change);
        changeButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                daysChecked=repeatWKDs;
                AlertDialog ad=new AlertDialog.Builder(setAlarmActivity.this).setTitle("Repeat").setMultiChoiceItems(
                        weekDays,daysChecked,
                            new AlertDialog.OnMultiChoiceClickListener(){
                                @Override
                                public void onClick(DialogInterface di, int which, boolean isChecked){
                                    if (isChecked){
                                        daysChecked[which]=true;
                                    } else {
                                        daysChecked[which]=false;
                                    }
                                }
                            })
                        .setPositiveButton("OK", new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface dialog, int id){
                                repeatWKDs=daysChecked;
                                int i;
                                String s="";
                                int total=0;
                                for (i=0;i<7;i++){
                                    if (daysChecked[i]){
                                        total=total+1;
                                        if (s==""){
                                            s=weekDays[i];
                                        } else {
                                            s=s+", "+weekDays[i];
                                        }
                                    }
                                }
                                if (s==""){
                                    s="Repeat: Never";
                                    repeatInfo.setText(s);
                                } else {
                                    if (total==7){
                                        s="Repeat: Everyday";
                                        repeatInfo.setText(s);
                                    }
                                    if (total==2){
                                        if (daysChecked[5] && daysChecked[6]){
                                            s="Repeat: Weekends";
                                            repeatInfo.setText(s);
                                        } else {
                                            s="Repeat: "+s;
                                            repeatInfo.setText(s);
                                        }
                                    }
                                    if (total==5){
                                        if (daysChecked[5]==false && daysChecked[6]==false){
                                            s="Repeat: Weekdays";
                                            repeatInfo.setText(s);
                                        } else {
                                            s="Repeat: "+s;
                                            repeatInfo.setText(s);
                                        }
                                    }
                                    if (total==1 || total==3 || total==4 || total==6){
                                        s="Repeat: "+s;
                                        repeatInfo.setText(s);
                                    }
                                }

                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface dialog, int id){
                                daysChecked=repeatWKDs;
                            }
                        }).show();
            }
        });

        changeButton2=(Button)findViewById(R.id.change2);
        changeButton2.setOnClickListener(new View.OnClickListener(){
           @Override
           public void onClick(View v){
               AlertDialog ad=new AlertDialog.Builder(setAlarmActivity.this).setTitle("Choose Challenge")
                       .setSingleChoiceItems(games,-1,new AlertDialog.OnClickListener(){
                           @Override
                           public void onClick(DialogInterface dialog, int which){
                               gameInfo.setText("Challenge: "+games[which]);
                               dialog.dismiss();
                           }
                       }).show();
           }
        });

        changeButton3=(Button)findViewById(R.id.change3);
        changeButton3.setOnClickListener(new View.OnClickListener(){
           @Override
           public void onClick(View v){
               AlertDialog ad=new AlertDialog.Builder(setAlarmActivity.this).setTitle("Choose Ringtone")
                       .setSingleChoiceItems(sampleSongList,-1,new AlertDialog.OnClickListener(){
                           @Override
                           public void onClick(DialogInterface dialog, int which){
                               ringtoneInfo.setText("Ringtone: "+sampleSongList[which]);
                               dialog.dismiss();
                           }
                       }).show();
           }
        });

    }

    public void changetoMain() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}
