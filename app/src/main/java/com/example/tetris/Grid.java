package com.example.tetris;

import com.example.tetris.blocks.Block;

public class Grid {
    private int GridHeight=20;
    private int GridWidth=10;
    private Byte GameGrid[][]=new Byte[GridHeight][GridWidth];
    public Byte[][] getGameGrid() { return GameGrid; }

    public void addBlock(Block newBlock)
    {
        int y=newBlock.coordY;
        int x=newBlock.coordX;
        for(int i=0;i<4;i++){
            for(int j =0;j<4;j++) {
               if(newBlock.getPosition()[i][j]){

                   GameGrid[y + i][x + j] = newBlock.getColor();
               }
            }

        }
    }

    public Byte updateGrid()
    {
        Byte score=0;
        for(int i=0;i<GridHeight;i++) {

            boolean lvlClear = true;

            for (int j = 0; j < GridWidth; j++) {
                if (GameGrid[i][j] < 1) {
                    lvlClear = false;
                }
            }

            if(lvlClear) {
                for (int j=i;j<GridHeight-1;j++) {
                    for (int k = 0; k < GridWidth; k++) {
                        GameGrid[j][k]=GameGrid[j+1][k];
                    }
                }
                score++;
                i--;
            }
        }
        return score;
    }

}
