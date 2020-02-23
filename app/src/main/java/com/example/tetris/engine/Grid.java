package com.example.tetris.engine;

import com.example.tetris.blocks.Block;

 class Grid {
    private int GridHeight;
    private int GridWidth;
    private Byte[][] GameGrid;

     Byte[][] getGameGrid() { return GameGrid; }

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

    void addBlock(Block newBlock) {
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

     Integer updateGrid() {
        int score = 0;
        for (int i = 0; i < GridHeight; i++) {

            boolean lineClear = true;
            //Check for Cleared lines
            for (int j = 0; j < GridWidth; j++) {
                if (GameGrid[i][j] == 0) {
                    lineClear = false;
                }
            }

            if (lineClear) {
                //Rewrite blocks down
                for (int j = i; j > 0; j--) {
                    if (GridWidth >= 0)
                        System.arraycopy(GameGrid[j - 1], 0, GameGrid[j], 0, GridWidth);
                }
                //Clear top line
                for (int j = 0; j < GridWidth; j++) { GameGrid[0][j] = 0; }
                i--; //Check the same line again after rewriting blocks
                score += 10;
            }
        }
        return score;
    }

}