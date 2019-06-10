package com.example.tetris.blocks;

public class BlockJ extends Block {
    @Override
    public void arrange()
    {
        position[1][0]=true;
        position[1][1]=true;
        position[1][2]=true;
        position[0][2]=true;
    }

    public BlockJ(Byte newColor){
        arrange();
        color=newColor;
    }
}
