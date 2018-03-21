package com.example.cosine.myapplication.g2048;

import android.content.Context;
import android.graphics.Color;
import android.widget.Button;
import android.widget.TableLayout;

import com.example.cosine.myapplication.R;


public class Display extends TableLayout {
    private static Board board;
    private Button[][] blocks;
    private int dimension = 4;
    private static Context context;

    Display(Context context, Board board, Button[][] blocks){
        super(context);
        this.context = context;
        this.board = board;
        this.blocks = blocks;

        this.board.addDisplay(this);
    }

    public void updateDisplay(){
        int num;
        for (int x = 0; x < dimension; ++x) {
            for (int y = 0; y < dimension; ++y) {
                num = board.getValue(x, y);
                if (num == 0){
                    blocks[x][y].setText("");
                }
                else{
                    blocks[x][y].setText(Integer.toString(num));
                }
                blocks[x][y].setBackground(getResources().getDrawable(getDrawableId(num)));
                if (num == 2 || num == 4) blocks[x][y].setTextColor(Color.parseColor("#6a625a"));
                else blocks[x][y].setTextColor(Color.parseColor("#ffffff"));
            }
        }
    }

    private int getDrawableId(int num){
        switch (num){
            case 2:
                return R.drawable.cell_2;

            case 4:
                return R.drawable.cell_4;

            case 8:
                return R.drawable.cell_8;

            case 16:
                return R.drawable.cell_16;

            case 32:
                return R.drawable.cell_32;

            case 64:
                return R.drawable.cell_64;

            case 128:
                return R.drawable.cell_128;

            case 256:
                return R.drawable.cell_256;

            case 512:
                return R.drawable.cell_512;

            case 1024:
                return R.drawable.cell_1024;

            case 2048:
                return R.drawable.cell_2048;
        }
        return R.drawable.cell_0;
    }
}
