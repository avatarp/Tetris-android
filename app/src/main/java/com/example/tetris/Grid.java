package com.example.tetris;

import com.example.tetris.blocks.Block;

public class Grid {
    private int GridHeight;
    private int GridWidth;
    private Byte[][] GameGrid;

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

    void addBlock(Block newBlock)
    {
        int y=newBlock.coordY;
        int x=newBlock.coordX;

        for(int i=0;i<4;i++){
            for(int j =0;j<4;j++) {
               if(newBlock.getPosition()[i][j]){

                   GameGrid[y + i][x + j] =
                           newBlock.getColor();
               }
            }

        }
    }

    public Integer updateGrid()
    {
        int score = 0;
        for (int i = 0; i < GridHeight; i++) {

            boolean lvlClear = true;

            for (int j = 0; j < GridWidth; j++) {
                if (GameGrid[i][j] == 0) {
                    lvlClear = false;
                }
            }

            if (lvlClear) {

                for (int j = i; j > 1; j--) {
                    for (int k = 0; k < GridWidth; k++) {
                        GameGrid[j][k] = GameGrid[j - 1][k];

                    }
                }
                i--;
                score += 10;

            }
            }


        return score;
    }

}
