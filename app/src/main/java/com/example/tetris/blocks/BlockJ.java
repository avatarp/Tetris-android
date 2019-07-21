package com.example.tetris.blocks;

public class BlockJ extends Block {
    @Override
    public void arrange()
    {

        for (int i = 0; i <4; i++) {
            for (int j = 0; j < 4; j++) {
                position[i][j] =false;
            }
        }
        position[2][1] = true;
        position[2][2] = true;
        position[2][3] = true;
        position[1][3] = true;
    }

    public BlockJ(Byte newColor){

        arrange();
        color=newColor;
        coordY=0;
        coordX=4;
    }
}
