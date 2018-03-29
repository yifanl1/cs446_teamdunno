package com.example.cosine.myapplication;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.widget.*;

import java.io.*;

public class AlarmListActivity extends AppCompatActivity {

    Button addButton;
    FileInputStream istream;
    ListView listView;
    int numAlarms;
    String fileName="alarmList";
    String[] timeStr;
    String[] rptStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_list);

        checkFile();
        try {
            istream=openFileInput(fileName);
        } catch (Exception e){

        }


        listView=(ListView)findViewById(R.id.listView);
        setList();

        addButton=(Button)findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(AlarmListActivity.this, setAlarmActivity.class);
                startActivityForResult(intent,1);


            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        setList();
    }

    void checkFile(){
        File f=new File(fileName);
        FileOutputStream ostr;
        String msg="0";
        if (!f.exists()){
            try{
                ostr=openFileOutput(fileName, Context.MODE_PRIVATE);
                ostr.write(msg.getBytes());
                ostr.flush();
                ostr.close();
            } catch (Exception e){
                System.out.println("Create failed");
            }

        }
    }

    void setList(){
        BufferedReader reader;

        int i;
        String ts;
        try{
            istream=openFileInput(fileName);
            reader=new BufferedReader(new InputStreamReader(istream));
            ts=reader.readLine();
            numAlarms=Integer.parseInt(ts);
            timeStr=new String[numAlarms];
            rptStr=new String[numAlarms];
            for (i=0;i<numAlarms;i++){
                ts=reader.readLine();
                timeStr[i]=ts;
                ts=reader.readLine();
                rptStr[i]=ts;
                timeStr[i]=timeStr[i]+"\t\t"+ts;
                ts=reader.readLine();
                timeStr[i]=timeStr[i]+"\n"+ts;
                ts=reader.readLine();
                timeStr[i]=timeStr[i]+"\n"+ts;
            }
            reader.close();
            istream.close();
        } catch (Exception e){
            Toast.makeText(AlarmListActivity.this,"Failed!",Toast.LENGTH_SHORT).show();
        }

        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, timeStr);

        listView=(ListView)findViewById(R.id.listView);
        listView.setAdapter(adapter);
        listView.setVisibility(View.VISIBLE);

    }
}
