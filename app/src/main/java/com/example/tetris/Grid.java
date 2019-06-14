package com.example.tetris;

import android.util.Log;

import com.example.tetris.blocks.Block;

public class Grid {
    private int GridHeight;
    private int GridWidth;
    private Byte GameGrid[][];
    public Byte[][] getGameGrid() { return GameGrid; }

    Grid(){
        GridHeight=20;
        GridWidth=10;
        GameGrid=new Byte[GridHeight][GridWidth];
            for (int i = 0; i <GridHeight; i++) {
                 for (int j = 0; j < GridWidth; j++) {
                     GameGrid[i][j] =0;
                }
            }
    }

    //todo fix blocks not alvays being added to grid
    void addBlock(Block newBlock)
    {
        int y=newBlock.coordY;
        int x=newBlock.coordX;
        Integer XX = x;
        Integer YY = y;

        Log.d("debug", "dropdown is true");
        Log.d("debug", "add block currposY:" + YY.toString());
        Log.d("debug", "add block currposX:" + XX.toString());
        for(int i=0;i<4;i++){
            for(int j =0;j<4;j++) {
               if(newBlock.getPosition()[i][j]){

                   GameGrid[y + i][x + j] =
                           newBlock.getColor();
               }
            }

        }
    }

    public Byte updateGrid()
    {
        for (int i = 0; i < GridHeight; i++) {

            boolean lvlClear = true;

            for (int j = 0; j < GridWidth; j++) {
                if (GameGrid[i][j] == 0) {
                    lvlClear = false;
                }
            }

            if (lvlClear) {
                Log.d("debug", "lvlClear is true");

                for (int j = GridHeight - 1; j > 1; j--) {
                    for (int k = 0; k < GridWidth; k++) {
                        GameGrid[j][k] = GameGrid[j - 1][k];
                    }
                }
                return 10;

            }
            }


        return 0;
    }

}
