package com.example.cosine.myapplication.g2048;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v4.content.res.ResourcesCompat;
import android.widget.Button;
import android.widget.TableLayout;
import com.example.cosine.myapplication.R;

import java.util.Locale;


public class Display extends TableLayout {
    private Button[][] buttons;
    private Context context;
    private Board board;

    Display(Context context, Button[][] buttons){
        super(context);
        this.context = context;
        this.buttons = buttons;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public void updateDisplay(){
        int num;

        for (int x = 0; x < Board.BOARD_SIZE; ++x) {
            for (int y = 0; y < Board.BOARD_SIZE; ++y) {
                if (buttons[x][y] == null) {
                    continue;
                }

                num = board.getValueAt(x, y);
                if (num == 0){
                    buttons[x][y].setText("");
                } else{
                    buttons[x][y].setTextSize(chooseTextSize(num));
                    buttons[x][y].setText(String.format(Locale.getDefault(), "%d", num));
                }

                buttons[x][y].setBackground(chooseBackground(num));
                buttons[x][y].setTextColor(chooseTextColor(num));
            }
        }
    }

    private Drawable chooseBackground(int num){
        int resId;
        switch (num) {
            case 2:
                resId = R.drawable.g2048_cell_2;
                break;
            case 4:
                resId = R.drawable.g2048_cell_4;
                break;
            case 8:
                resId = R.drawable.g2048_cell_8;
                break;
            case 16:
                resId = R.drawable.g2048_cell_16;
                break;
            case 32:
                resId = R.drawable.g2048_cell_32;
                break;
            case 64:
                resId = R.drawable.g2048_cell_64;
                break;
            case 128:
                resId = R.drawable.g2048_cell_128;
                break;
            case 256:
                resId = R.drawable.g2048_cell_256;
                break;
            case 512:
                resId = R.drawable.g2048_cell_512;
                break;
            case 1024:
                resId = R.drawable.g2048_cell_1024;
                break;
            case 2048:
                resId = R.drawable.g2048_cell_2048;
                break;
            default:
                resId = R.drawable.g2048_cell_0;
                break;
        }
        return ResourcesCompat.getDrawable(getResources(), resId, null);
    }

    private float chooseTextSize(int num) {
        switch(num) {
            case 2:
            case 4:
            case 8:
                return 40f;
            case 16:
            case 32:
            case 64:
                return 32f;
            case 128:
            case 256:
            case 512:
                return 24f;
            case 1024:
            case 2048:
                return 20f;
        }
        return 1f;
    }

    private int chooseTextColor(int num) {
        String color;
        switch(num) {
            case 2:
            case 4:
                color = "#776e65";
                break;
            default:
                color = "#f9f6f2";
                break;
        }
        return Color.parseColor(color);
    }
}
