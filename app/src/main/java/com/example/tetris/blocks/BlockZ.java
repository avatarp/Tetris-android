package com.example.tetris.blocks;

public class BlockZ extends Block {
    @Override
    public void arrange()
    {
        position[0][0]=true;
        position[0][1]=true;
        position[1][1]=true;
        position[1][2]=true;
    }

    public BlockZ(Byte newColor){
        arrange();
        color=newColor;
    }
}
