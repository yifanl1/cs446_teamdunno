package com.example.cosine.myapplication;

/**
 * Created by asus on 2018/2/20.
 */
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.View;
import android.util.*;
import java.util.ArrayList;
import java.util.Random;

public class MyCanvas extends View {


    Paint paint = new Paint();
    float xend = 0;

    RectF player;
    boolean lost = false;
    int score;

    //the newest rect position
    int left ;
    int right;
    int top;
    int bottom;

    // player's current position
    float cleft;
    float cright;
    float ctop;
    float cbottom;

    float dx = 0;
    float dy = 0;

    public MyCanvas(Context context) {
        super(context);
    }

    public MyCanvas(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    ArrayList<int[]>  coords = new ArrayList<int[]>();
    ArrayList<Integer> colors = new ArrayList<Integer>();


    void setStPos(int l, int t, int r, int b) {
        left  = l;
        right = r;
        top = t;
        bottom = b;
        drawPlayer((l+r)/2 -25,t -50,(l+r)/2 + 25, t );
    }

    void addRect(int l, int t, int r, int b) {

        if (coords.size() > 3) {
            removeRect();
        }
        int[]  coord  = new int[4];
        coord[0] = l;
        coord[1] = t;
        coord[2] = r;
        coord[3] = b;
        coords.add(coord);
        left = l;
        right = r;
        top = t;
        bottom = b;
        Random rnd = new Random();
        int col = Color.argb(255, rnd.nextInt(256),rnd.nextInt(256),rnd.nextInt(256));
        colors.add(col);
    }

    void removeRect() {
        coords.remove(0);
        colors.remove(0);
    }

    void drawPlayer(float l, float t, float r, float b) {
        cleft = l;
        cright = r;
        ctop = t;
        cbottom = b;
        player = new RectF(l,t,r,b);
    }

    void setC(float l, float t, float r, float b) {
        cleft = l;
        cright = r;
        ctop = t;
        cbottom = b;;
    }

    //void setPlayer(float l, float t, float r, float b) {
    //   player.set(l,t,r,b);
    //}

    @Override
    protected void onDraw(Canvas canvas)  {
        super.onDraw(canvas);
        //paint.setColor(Color.BLUE);
        paint.setStrokeWidth(0);

        // draw rect

        canvas.save();
        canvas.translate(-dx,0);
        for (int size = 0; size < coords.size(); size += 1)
        {
            float left = coords.get(size)[0];
            float top = coords.get(size)[1];
            float right = coords.get(size)[2];
            float bottom = coords.get(size)[3];
            paint.setColor(colors.get(size));
            canvas.drawRect(left,top,right,bottom,paint);
        }

        //draw player
        paint.setColor(Color.GREEN);
        canvas.drawRect(player,paint);
        canvas.restore();

    }

    public void setRight(float right){
        dx += right - player.right;
        this.player.right = right;
        invalidate();
    }
    public void setLeft(float left){
        this.player.left = left;
        invalidate();
    }
    public void setTop(float top){
        this.player.top = top;
        invalidate();
    }
    public void setBottom(float bottom){
        this.player.bottom = bottom;
        invalidate();
    }


    void initialGame() {
        score = 0;
        //starting position
        int sl = 100;
        int st= 500;
        int sr = 250;
        int sb = 650;
        setStPos(sl,st,sr,sb);
        addRect(sl,st,sr,sb); //start position
        invalidate();
        Random rand = new Random();
        int distance = rand.nextInt(301) + 100;
        addRect(sr + distance,st,sr + distance + 150 ,sb);
        invalidate();
    }


    void restart() {
        score = 0;
        coords.clear();
        lost = false;
        int score  = 0;
        int sl = 100;
        int st= 500;
        int sr = 250;
        int sb = 650;
        setStPos(sl,st,sr,sb);
        addRect(sl,st,sr,sb); //start position
        invalidate();
        Random rand = new Random();
        int distance = rand.nextInt(301) + 100;
        addRect(sr + distance,st,sr + distance + 150 ,sb);
        invalidate();
    }

    public void newRect() {

        int l = left;
        int t=  top;
        int r = right;
        int b = bottom;
        Random rand = new Random();
        int distance = rand.nextInt(301) + 100;
        addRect(r + distance,t,r + distance + 150 ,b);
        invalidate();

    }



}

