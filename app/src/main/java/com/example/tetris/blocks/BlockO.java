package com.example.tetris.blocks;

public class BlockO extends Block {
    @Override
    public void arrange()
    {
        for (int i = 0; i <4; i++) {
            for (int j = 0; j < 4; j++) {
                position[i][j] =false;
            }
        }
        position[0][0]=true;
        position[0][1]=true;
        position[1][0]=true;
        position[1][1]=true;
    }

    public BlockO(Byte newColor){
        arrange();
        color=newColor;
        coordY=0;
        coordX=4;
    }
}
